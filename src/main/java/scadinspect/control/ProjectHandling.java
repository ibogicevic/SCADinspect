package scadinspect.control;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import scadinspect.gui.Main;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

/**
 * Project Handler for loading and closing paths and files.
 * Stores the loaded files in a file list.
 * @author bilir
 *
 */
public class ProjectHandling {

  /**
   * TODO: Closing of the Project when clicked on "Open folder" and a new directory is chosen else
   * the current path remains and list is still loaded
   */

  // Definition of the chooser
  private final DirectoryChooser directoryChooser = new DirectoryChooser();
  private final FileChooser fileChooser = new FileChooser();

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
     * contents of the sub-folder to the fileList
     */
    if (projectDirectory != null) {
      setProjectPath(projectDirectory);
      addFilesToList(projectDirectory.getAbsolutePath());
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
    setCurrentProject(projectPath.getAbsolutePath().toString());
    Main.getInstance().toolbarArea.setButtonsDisabled(false);
  }

  /**
   * Sets the current project path in the Title and the app name
   * 
   * @param rootPath The path for the current project
   */
  private void setCurrentProject(String rootPath) {
    // update window title
    Main.getInstance().getPrimaryStage().setTitle(Main.APPNAME + " – " + rootPath);
    // remember open project
    Main.getInstance().currentProject = rootPath;
  }

  /**
   * Gets the files in the current directory and it sub-folders. Also it adds only .scad files to the
   * list
   * 
   * @param projectDirectory The path for the currently selected folder and it sub-folders
   */
  private void addFilesToList(String projectDirectory) {
    // Set the current folder
    File folder = new File(projectDirectory);

    // Loop to add files from folder and sub-folders in list
    for (File file : folder.listFiles()) {

      // If the current file is a scad file add it to the list
      if (file.isFile() && file.toString().endsWith(".scad")) {
        Main.getInstance().getFileList().add(file);
      } else {
        /**
         * If the current selected file is a folder, go recursively call the function with the
         * current sub-folder
         */
        if (file.isDirectory()) {
          addFilesToList(file.getAbsolutePath());
        }
      }
    }
  }

  /**
   * Closes the current project and removes the path from the title. Also it deletes the content of
   * the fileList.
   */
  public void closeProject() {
    Main.getInstance().toolbarArea.setButtonsDisabled(true);
    if (Main.getInstance().isProjectOpen()) {
      Main.getInstance().setCurrentProject("");
      Main.getInstance().getPrimaryStage().setTitle(Main.APPNAME);
      Main.getInstance().getFileList().clear();
    }
  }
}
