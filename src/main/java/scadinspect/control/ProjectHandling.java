package scadinspect.control;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.UIManager;

import scadinspect.gui.Main;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

public class ProjectHandling {
	
	// Definiton of the Chooser
	private final DirectoryChooser directoryChooser = new DirectoryChooser();
	private final FileChooser fileChooser = new FileChooser();

	// Definition of the variables
	private File projectFile;
	private File projectDirectory;
	
	/**
	 * Sets the current project path in the Title and the main class
	 * @param rootPath
	 */
	private void setCurrentProject(String rootPath) {
		// update window title
		Main.getInstance().getPrimaryStage().setTitle(Main.APPNAME + " – " + rootPath);
		// remember open project
		Main.getInstance().currentProject = rootPath;
	}

	/**
	 * Opens the dialog to choose a file
	 */
	public void openProjectFile() {
		projectFile = fileChooser.showOpenDialog(Main.getInstance().getPrimaryStage());
		setProjectPath(projectFile);
	}
	
	/**
	 * Opens the dialog to choose a directory
	 */
	public void openProjectFolder(){
		projectDirectory = directoryChooser.showDialog(Main.getInstance().getPrimaryStage());
		setProjectPath(projectDirectory);
	}
	
	/**
	 * Checks if a path for the project is set, if so it sets the pathname in the title and opens the project
	 * @param projectDirectory
	 */
	private void setProjectPath(File projectPath){
		if (projectPath != null) {
			projectPath.getAbsolutePath();
			setCurrentProject(projectPath.getAbsolutePath().toString());
			Main.getInstance().toolbarArea.disableButtons(false);
		}
	}

	/**
	 * Closes the current project and removes the path from the title
	 */
	public void closeProject() {
		Main.getInstance().toolbarArea.disableButtons (true);
		if(Main.getInstance().isProjectOpen()==true){
			Main.getInstance().setCurrentProject("");
			Main.getInstance().getPrimaryStage().setTitle(Main.APPNAME);
		}
	}

}
