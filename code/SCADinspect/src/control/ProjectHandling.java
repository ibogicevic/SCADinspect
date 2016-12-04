package control;

import java.io.File;

import gui.Main;
import javafx.stage.DirectoryChooser;

public class ProjectHandling {

	static public void openProject(String rootPath) {
		// update window title
		Main.getInstance().getPrimaryStage().setTitle(Main.APPNAME + " – " + rootPath);
		// load tree in explorer
		//Main.getInstance().explorerArea.loadNewTree(rootPath);
		// remember open project
		Main.getInstance().rootPath = rootPath;

	}

	static public void openProject() {
		final DirectoryChooser directoryChooser = new DirectoryChooser();
		File projectDirectory;
		projectDirectory = directoryChooser.showDialog(Main.getInstance().getPrimaryStage());
		if (projectDirectory != null) {
			projectDirectory.getAbsolutePath();
		}
		// TODO: check if dialog has been cancelled
		openProject(projectDirectory.getAbsolutePath());
	}

}
