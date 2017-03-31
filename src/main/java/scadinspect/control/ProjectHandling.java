package scadinspect.control;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import scadinspect.gui.Main;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import scadinspect.control.io.FileSearchRunnable;

/**
 * 
 * @author bilir
 *
 */
public class ProjectHandling {

  /**
   * TODO: Closing of the Project when clicked on "Open folder" and a new directory is choosen else
   * the current path remains and list is still loaded
   */

  // Definiton of the chooser
  private final DirectoryChooser directoryChooser = new DirectoryChooser();
  private final FileChooser fileChooser = new FileChooser();
  private final long FILE_READ_TIMEOUT = 5000;
  
  // Filter for the chooser
  private final FileChooser.ExtensionFilter extensionFilter =
      new FileChooser.ExtensionFilter("SCAD files", "*.scad");

  /**
   * Default constructor where the extension filter for the fileChooser is set. This assures that
   * only .scad files can be selected
   */
  public ProjectHandling() {
    fileChooser.getExtensionFilters().add(extensionFilter);
  }

  /**
   * Opens the dialog to choose a file
   */
  public void openProjectFile() {
	File projectFile;
    projectFile = fileChooser.showOpenDialog(Main.getInstance().getPrimaryStage());

    /**
     * Checks if a file is selected or the cancel button is clicked If cancel is clicked a null is
     * set into projectDirectory, else the Path
     */

    if (projectFile != null) {
      setProjectPath(projectFile);
      Main.getInstance().getFileList().add(projectFile);
    }
  }

  /**
   * Opens the dialog to choose a directory
   */
  public void openProjectFolder() {
	File projectDirectory;
    projectDirectory = directoryChooser.showDialog(Main.getInstance().getPrimaryStage());

    /**
     * Checks if a directory is selected or the cancel button is clicked If cancel is clicked a null
     * is set into projectDirectory, else it sets the Path and add the files recursively with the
     * contents of the subfolder to the fileList
     */
    if (projectDirectory != null) {
      setProjectPath(projectDirectory);
      Supplier<Boolean> confirmLongRead = () -> {
          System.out.println("Confirming");
          try {
              Task<Boolean> task = new Task<Boolean>() {
                  @Override
                  protected Boolean call() throws Exception {
                      System.out.println("Creating alert");
                      Alert alert = new Alert(AlertType.CONFIRMATION);
                      alert.setTitle("Open files");
                      alert.setHeaderText("File loading taking longer then expected");
                      alert.setContentText("Do you want to continue?");
                      Optional<ButtonType> b = alert.showAndWait();
                      return b.isPresent() && b.get() == ButtonType.OK;
                  }
              };
              Thread t = new Thread(task);
              t.start();
              t.join();
              return task.get();
          } catch (InterruptedException | ExecutionException ex) {
              return false;
          }
      };
      addFiles(projectDirectory, confirmLongRead);
    }
  }

  /**
   * Checks if a path for the project is set, if so it closes the last open project and then sets
   * the pathname in the title and enables the buttons
   * 
   * @param projectPath
   */
  private void setProjectPath(File projectPath) {
    closeProject();
    if (projectPath != null) {
      setCurrentProject(projectPath.getAbsolutePath());
      Main.getInstance().toolbarArea.setButtonsDisabled(false);
    }
  }

  /**
   * Sets the current project path in the Title and the App name
   * 
   * @param rootPath
   */
  private void setCurrentProject(String rootPath) {
    // update window title
    Main.getInstance().getPrimaryStage().setTitle(Main.APPNAME + " – " + rootPath);
    // remember open project
    Main.getInstance().currentProject = rootPath;
  }
  
  /**
   * Gets the files in the current directory and it subfolders. Also it adds only .scad files to the
   * list
   * 
   * @param dir
   * @param onTimeout
   */
    private void addFiles(File dir, Supplier<Boolean> onTimeout) {
      try {
          Collection<File> files = getFiles(dir, f -> f.getName().toLowerCase().endsWith(".scad"), true, FILE_READ_TIMEOUT, onTimeout);
          Main.getInstance().getFileList().addAll(files);
      } catch (IOException ex) {
          Logger.getLogger(ProjectHandling.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  
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
    Main.getInstance().toolbarArea.setButtonsDisabled(true);
    if (Main.getInstance().isProjectOpen() == true) {
      Main.getInstance().setCurrentProject("");
      Main.getInstance().getPrimaryStage().setTitle(Main.APPNAME);
      Main.getInstance().getFileList().clear();
    }
  }
}
