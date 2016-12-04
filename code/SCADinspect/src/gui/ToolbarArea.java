package gui;

import control.ProjectHandling;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;

/**
 * Toolbar at the top of the main window
 * @author ivan
 */
public class ToolbarArea extends ToolBar {
	
	public ToolbarArea() {
		// initialize buttons
		Button openProjectButton = new Button("Open Project");
		Button newFileButton = new Button("New File");
		Button saveFileButton = new Button("Save File");
		Button saveFileAsButton = new Button("Save File as");
		Button settingsButton = new Button("Settings");
		Button aboutButton = new Button("About");
		Button helpButton = new Button("Help");
		Button exitButton = new Button("Exit");
		// actionlisteners
		openProjectButton.setOnAction(e -> ProjectHandling.openProject());
		exitButton.setOnAction(e -> {Platform.exit();});
		// add all buttons
		this.getItems().add(openProjectButton);
		this.getItems().add(newFileButton);
		this.getItems().add(saveFileButton);
		this.getItems().add(saveFileAsButton);
		this.getItems().add(new Separator());
		this.getItems().add(new Separator());
		this.getItems().add(settingsButton);
		this.getItems().add(aboutButton);
		this.getItems().add(helpButton);
		this.getItems().add(exitButton);
	}
}