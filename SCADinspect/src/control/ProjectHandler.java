/*******************************************************************************
 * SCADinspect – https://github.com/ibogicevic/SCADinspect
 * Copyright (C) 2017 Ivan Bogicevic and others
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package control;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import control.io.FileSearchRunnable;
import gui.MainFrame;

/**
 * Project Handler for loading and closing paths and files.
 * Stores the loaded files in a file list.
 */
public class ProjectHandler {

	/**
	 * TODO: Closing of the Project when clicked on "Open folder" and a new directory is chosen else
	 * the current path remains and list is still loaded
	 */

	// Definition of the chooser
	private final DirectoryChooser directoryChooser = new DirectoryChooser();
	private final FileChooser fileChooser = new FileChooser();
	private static final long FILE_READ_TIMEOUT = 5000;
	public static final String SETTINGS_DEFAULT_DIR = "DefaultDir";

	// Filter for the chooser
	private final FileChooser.ExtensionFilter extensionFilter =
			new FileChooser.ExtensionFilter("SCAD files", "*.scad");

	/**
	 * Default constructor where the extension filter for the fileChooser is set. This assures that
	 * only .scad files can be selected
	 */
	public ProjectHandler() {
		fileChooser.getExtensionFilters().add(extensionFilter);
	}

	/**
	 * Opens the dialog to choose a file
	 */
	public void openProjectFile() {
		Preferences userPrefs = Preferences.userRoot().node("DHBW.SCADInspect.Project");
		String defFile = userPrefs.get(SETTINGS_DEFAULT_DIR, null);
		if(defFile != null) {
			File file = new File(defFile);
			if(file.exists()) {
				fileChooser.setInitialDirectory(file);
			}
		}
		File projectFile = fileChooser.showOpenDialog(MainFrame.getInstance().getPrimaryStage());

		/**
		 * Checks if a file is selected or the cancel button is clicked If cancel is clicked a null is
		 * set into projectDirectory, else the Path
		 */

		if (projectFile != null) {
			userPrefs.put(SETTINGS_DEFAULT_DIR, projectFile.getParent());
			setProjectPath(projectFile);
			MainFrame.getInstance().getFileList().add(projectFile);
		}
	}

	/**
	 * Opens the dialog to choose a directory
	 * @param onDone
	 */
	public void openProjectFolder(Consumer<Collection<File>> onDone) {
		Preferences userPrefs = Preferences.userRoot().node("DHBW.SCADInspect.Project");
		String defFile = userPrefs.get(SETTINGS_DEFAULT_DIR, null);
		if(defFile != null) {
			File file = new File(defFile);
			if(file.exists()) {
				directoryChooser.setInitialDirectory(file);
			}
		}

		File projectDirectory = directoryChooser.showDialog(MainFrame.getInstance().getPrimaryStage());

		/**
		 * Checks if a directory is selected or the cancel button is clicked If cancel is clicked a null
		 * is set into projectDirectory, else it sets the Path and add the files recursively with the
		 * contents of the sub-folder to the fileList
		 */
		if (projectDirectory != null) {
			userPrefs.put(SETTINGS_DEFAULT_DIR, projectDirectory.getAbsolutePath());
			setProjectPath(projectDirectory);
			Supplier<Boolean> confirmLongRead = () -> {
				try {
					final FutureTask<Boolean> query = new FutureTask<>(() -> {
						Alert alert = new Alert(AlertType.CONFIRMATION);
						alert.setTitle("Open files");
						alert.setHeaderText("File loading taking longer then expected");
						alert.setContentText("Do you want to continue?");
						Optional<ButtonType> b = alert.showAndWait();
						return b.isPresent() && b.get() == ButtonType.OK;
					});
					Platform.runLater(() -> query.run());
					return query.get();
				} catch (InterruptedException | ExecutionException ex) {
					return false;
				}

			};
			addFiles(projectDirectory, confirmLongRead, onDone);
		}
	}

	/**
	 * Closes the last open project and then sets
	 * the new pathname, the new project and enables the buttons
	 * 
	 * @param projectPath The project path for the current project
	 */
	private void setProjectPath(File projectPath) {
		closeProject();
		setCurrentProject(projectPath.getAbsolutePath());
		MainFrame.getInstance().toolbarArea.disableButtons(false);
	}

	/**
	 * Sets the current project path in the Title and the app name
	 * 
	 * @param rootPath The path for the current project
	 */
	private void setCurrentProject(String rootPath) {
		// update window title
		MainFrame.getInstance().getPrimaryStage().setTitle(MainFrame.APPNAME + " – " + rootPath);
		// remember open project
		MainFrame.getInstance().currentProject = rootPath;
	}

	/**
	 * Gets the files in the current directory and its sub-folders. It also adds only .scad files to the
	 * list
	 * 
	 * @param dir
	 * @param onTimeout
	 */
	private void addFiles(File dir, Supplier<Boolean> onTimeout, Consumer<Collection<File>> onDone) {
		new Thread(() -> {
			try {
				Collection<File> files = getFiles(dir, f -> f.getName().toLowerCase().endsWith(".scad"), true, FILE_READ_TIMEOUT, onTimeout);
				onDone.accept(files);
			} catch (IOException ex) {
				Logger.getLogger(ProjectHandler.class.getName()).log(Level.SEVERE, null, ex);
				onDone.accept(null);
			}
		}).start();
	}

	/**
	 * Blocking call
	 * @param dir
	 * @param filter
	 * @param recursive
	 * @param timeout
	 * @param onTimeout
	 * @return
	 * @throws IOException 
	 */
	private Collection<File> getFiles(File dir, FileFilter filter, boolean recursive, long timeout, Supplier<Boolean> onTimeout) throws IOException{
		try {
			FileSearchRunnable fsr = new FileSearchRunnable(dir, filter, recursive);
			Thread t = new Thread(fsr);
			t.start();
			Thread watchdog = null;
			if(timeout > 0) {
				watchdog = new Thread() {
					@Override
					public void run() {
						try {
							Thread.sleep(timeout);
							if(fsr.isRunning()) {
								fsr.pause();
								boolean resume = onTimeout.get();
								if(resume) {
									fsr.resume();
								}
								else {
									fsr.terminate();
								}
							}
						} catch (InterruptedException ex) {}
					}
				};
				watchdog.start();
			}
			t.join();
			if(watchdog != null) {
				watchdog.interrupt();
			}
			if(fsr.hasEndedNaturally()) {
				return fsr.get();
			}
		} catch (InterruptedException ex) {}
		throw new IOException("Timeout exceeded during file read");
	}

	/**
	 * Closes the current project and removes the path from the title. Also it deletes the content of
	 * the fileList.
	 */
	public void closeProject() {
		if (MainFrame.getInstance().isProjectOpen()) {
			MainFrame.getInstance().setCurrentProject("");
			MainFrame.getInstance().getPrimaryStage().setTitle(MainFrame.APPNAME);
			MainFrame.getInstance().getFileList().clear();
			MainFrame.getInstance().contentArea.refresh();  //requires fileList to be cleared
			MainFrame.getInstance().toolbarArea.disableButtons(true);
			MainFrame.getInstance().statusArea.setMessage("No file loaded.");
		}
	}
}
