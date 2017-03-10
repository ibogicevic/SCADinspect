package scadinspect.control;

import java.io.File;

import scadinspect.gui.Main;
import javafx.stage.DirectoryChooser;

public class ProjectHandling {

	static public void openProject(String rootPath) {
		// update window title
		Main.getInstance().getPrimaryStage().setTitle(Main.APPNAME + " – " + rootPath);
		// load tree in explorer
		//Main.getInstance().explorerArea.loadNewTree(rootPath);
		// remember open project
		Main.getInstance().currentProject = rootPath;

	}

	static public void openProject() {
		final DirectoryChooser directoryChooser = new DirectoryChooser();
		File projectDirectory;
		//projectDirectory = directoryChooser.showDialog(Main.getInstance().getPrimaryStage());
		//if (projectDirectory != null) {
			//projectDirectory.getAbsolutePath();
		//}
		// TODO: check if dialog has been cancelled
		//openProject(projectDirectory.getAbsolutePath());
		Main.getInstance().toolbarArea.disableButtons(false);
	}

	static public void closeProject() {
		// TODO: close windows of project
		Main.getInstance().currentProject = null;
		Main.getInstance().toolbarArea.disableButtons (true);
	}

}