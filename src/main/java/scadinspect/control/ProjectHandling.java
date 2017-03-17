package scadinspect.control;

import java.io.File;

import scadinspect.gui.Main;
import javafx.stage.DirectoryChooser;

public class ProjectHandling {

	private static final DirectoryChooser directoryChooser = new DirectoryChooser();
	
	static public void setCurrentProject(String rootPath) {
		// update window title
		Main.getInstance().getPrimaryStage().setTitle(Main.APPNAME + " – " + rootPath);
		// remember open project
		Main.getInstance().currentProject = rootPath;
	}

	static public void openProject() {
		File projectDirectory;
		projectDirectory = directoryChooser.showDialog(Main.getInstance().getPrimaryStage());
		if (projectDirectory != null) {
			projectDirectory.getAbsolutePath();
			setCurrentProject(projectDirectory.getAbsolutePath().toString());
			Main.getInstance().toolbarArea.disableButtons(false);
		}
		// TODO: check if dialog has been cancelled
	}

	static public void closeProject() {
		// TODO: close windows of project
		Main.getInstance().toolbarArea.disableButtons (true);
		if(Main.getInstance().isProjectOpen()==true){
			Main.getInstance().setCurrentProject("");
			Main.getInstance().getPrimaryStage().setTitle(Main.APPNAME);
		}
	}

}
