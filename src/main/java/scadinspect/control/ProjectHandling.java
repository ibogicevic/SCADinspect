package scadinspect.control;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

import scadinspect.gui.Main;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

public class ProjectHandling {
	
	/**
	 * TODO: 
	 */
	
	// Definiton of the chooser
	private final DirectoryChooser directoryChooser = new DirectoryChooser();
	private final FileChooser fileChooser = new FileChooser();
	
	// Filter for the chooser
	private final FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("SCAD files", "*.scad");
	private final FileFilter fileFilter= new FileFilter() {
		@Override
		/**
		 * Accepts only files which end with .scad
		 */
		public boolean accept(File file) {
			if(file.getName().toLowerCase().endsWith(".scad")){
				return true;
			}else{
				return false;
			}
		}
	};
	
	// Definition of the variables
	private File projectFile;
	private File projectDirectory;
	private ArrayList<File> fileList = new ArrayList();
	
	/**
	 * Constructor
	 */
	public ProjectHandling(){
		fileChooser.getExtensionFilters().add(extensionFilter);
	}
	
	/**
	 * Opens the dialog to choose a file
	 */
	public void openProjectFile() {
		projectFile = fileChooser.showOpenDialog(Main.getInstance().getPrimaryStage());
		setProjectPath(projectFile);
		if(projectFile!=null)
			fileList.add(projectFile);
	}
	
	/**
	 * Opens the dialog to choose a directory
	 */
	public void openProjectFolder(){
		projectDirectory = directoryChooser.showDialog(Main.getInstance().getPrimaryStage());
		setProjectPath(projectDirectory);
		if(projectDirectory!=null)
			addFilesToList(projectDirectory);
	}
	
	/**
	 * Checks if a path for the project is set, if so it sets the pathname in the title and opens the project
	 * @param projectPath
	 */
	private void setProjectPath(File projectPath){
		if (projectPath != null) {
			projectPath.getAbsolutePath();
			setCurrentProject(projectPath.getAbsolutePath().toString());
			Main.getInstance().toolbarArea.disableButtons(false);
		}
	}
	
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
	 * Gets the files in the current directory and adds only .scad files to the list
	 * @param projectDirectory
	 */
	private void addFilesToList(File projectDirectory) {
		for(File file:projectDirectory.listFiles(fileFilter)){
			fileList.add(file);
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
			fileList.clear();
		}
	}
	
	/**
	 * Returns the currently opened Files
	 * @return fileList
	 */
	public ArrayList<File> getFileList(){
		return fileList;
	}

}
