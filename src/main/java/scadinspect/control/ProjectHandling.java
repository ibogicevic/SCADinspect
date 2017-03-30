package scadinspect.control;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import scadinspect.data.scaddoc.JsonExport;
import scadinspect.data.scaddoc.Module;
import scadinspect.data.scaddoc.parser.PropertyParser;
import scadinspect.gui.Main;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

/**
 * @author bilir
 */
public class ProjectHandling {

  /**
   * TODO: Closing of the Project when clicked on "Open folder" and a new directory is choosen else
   * the current path remains and list is still loaded
   */

  // Definiton of the chooser
  private final DirectoryChooser directoryChooser = new DirectoryChooser();
  private final FileChooser fileChooser = new FileChooser();

  // Filter for the chooser
  private final FileChooser.ExtensionFilter extensionFilter =
      new FileChooser.ExtensionFilter("SCAD files", "*.scad");

  // Definition of the variables
  private File projectFile;
  private File projectDirectory;
  private List<File> fileList = new ArrayList();

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
    projectFile = fileChooser.showOpenDialog(Main.getInstance().getPrimaryStage());

    /**
     * Checks if a file is selected or the cancel button is clicked If cancel is clicked a null is
     * set into projectDirectory, else the Path
     */

    if (projectFile != null) {
      fileList.add(projectFile);
      setProjectPath(projectFile);
    }
  }

  /**
   * Opens the dialog to choose a directory
   */
  public void openProjectFolder() {
    projectDirectory = directoryChooser.showDialog(Main.getInstance().getPrimaryStage());

    /**
     * Checks if a directory is selected or the cancel button is clicked If cancel is clicked a null
     * is set into projectDirectory, else it sets the Path and add the files recursively with the
     * contents of the subfolder to the fileList
     */
    if (projectDirectory != null) {
      addFilesToList(projectDirectory.getAbsolutePath());
      setProjectPath(projectDirectory);
    }
  }

  /**
   * Checks if a path for the project is set, if so it closes the last open project and then sets
   * the pathname in the title and enables the buttons
   */
  private void setProjectPath(File projectPath) {
    closeProject();
    if (projectPath != null) {
      setCurrentProject(projectPath.getAbsolutePath().toString());
      Main.getInstance().toolbarArea.disableButtons(false);
    }
  }

  /**
   * Sets the current project path in the Title and the App name
   * Is the last called function after all files have been added to fileList
   */
  private void setCurrentProject(String rootPath) {
    // update window title
    Main.getInstance().getPrimaryStage().setTitle(Main.APPNAME + " – " + rootPath);
    // remember open project
    Main.getInstance().currentProject = rootPath;
    parseFiles();
  }

  /**
   * Gets the files in the current directory and it subfolders. Also it adds only .scad files to the
   * list
   */
  private void addFilesToList(String projectDirectory) {
    // Set the current folder
    File folder = new File(projectDirectory);

    // Loop to add files from folder and subfolders in list
    for (File file : folder.listFiles()) {

      // If the current file is a scad file add it to the list
      if (file.isFile() && file.toString().endsWith(".scad")) {
        fileList.add(file);
      } else {
        /**
         * If the current selected file is a folder, go recursively call the function with the
         * current subfolder
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
    Main.getInstance().toolbarArea.disableButtons(true);
    if (Main.getInstance().isProjectOpen() == true) {
      Main.getInstance().setCurrentProject("");
      Main.getInstance().getPrimaryStage().setTitle(Main.APPNAME);
      fileList.clear();
    }
  }

  /**
   * called after all filepath have been collected loads fields convert them to string parses them
   * with propertyParser and saves them in Main class
   */
  private void parseFiles()  {
    PropertyParser parser=new PropertyParser();
    JsonExport export=new JsonExport();
    for (File file : fileList) {
      try {
        String module=new String(Files.readAllBytes(file.toPath()));
        parser.setScadFile(module);
        Collection<Module> modules=parser.parseModules();
        System.out.println(export.getJson(modules));
      } catch (IOException e) {
        e.printStackTrace();
      }


    }
    Main.getInstance().setModules(null);
  }
}
