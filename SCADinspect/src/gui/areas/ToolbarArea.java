package gui.areas;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import control.ProjectHandling;
import gui.Main;
import gui.Resources;
import gui.dialogs.AboutDialog;
import gui.dialogs.HelpDialog;
import javafx.scene.layout.BorderPane;

/**
 * Toolbar at the top of the main window
 */
public class ToolbarArea extends BorderPane {

	// initialize buttons
	private final MenuItem openFileButton = new MenuItem("Open file", Resources.loadIcon("open-folder-outline"));
	private final MenuItem openFolderButton = new MenuItem("Open folder", Resources.loadIcon("open-folder-outline"));
	private final SplitMenuButton openProjectButton = new SplitMenuButton(openFileButton, openFolderButton);
	private final Hyperlink helpLink = new Hyperlink("Help");
	private final Hyperlink aboutLink = new Hyperlink("About");
	private final Button exitButton = new Button("Exit");
	private final ProjectHandling projectHandler = new ProjectHandling();

	public ProjectHandling getProjectHandler() {
		return projectHandler;
	}

	/**
	 * Disable buttons when no project is open
	 *
	 * @param value true if buttons shall be disabled (no open project)
	 */
	public void disableButtons(boolean value) {


	}

	/**
	 * Constructor of ToolbarArea
	 */
	public ToolbarArea() {
		// configure open button
		openProjectButton.setGraphic(Resources.loadIcon("open-folder-outline"));
		// read settings
		openProjectButton.setText("Open file");
		openProjectButton.setOnAction(event -> {
			projectHandler.openProjectFile();
			Main.getInstance().contentArea.refresh();
		});
		openFolderButton.setOnAction(e -> {
			openProjectButton.setText("Open folder");
			openProjectButton.setOnAction(event -> {
				// loading of multiple files is non blocking
				projectHandler.openProjectFolder((files) -> {
					if(files != null) {
						Main.getInstance().getFileList().addAll(files);
						Platform.runLater(() -> {
							Main.getInstance().contentArea.refresh();
						});
					}
				});
			});
		});
		openFileButton.setOnAction(e -> {
			openProjectButton.setText("Open file");
			openProjectButton.setOnAction(event -> {
				projectHandler.openProjectFile();
				Main.getInstance().contentArea.refresh();
			});
		});
		exitButton.setOnAction(e -> {
			Platform.exit();
		});

		// images
		helpLink.setGraphic(Resources.loadIconSmall("help-icon"));
		aboutLink.setGraphic(Resources.loadIconSmall("about-icon"));
		exitButton.setGraphic(Resources.loadIcon("sign-out-option"));
		ImageView logo = Resources.loadIcon("logo2");
		logo.setPreserveRatio(true);
		logo.setFitHeight(25);

		// status of buttons
		disableButtons(true);
		helpLink.setOnAction(e -> HelpDialog.openDialog());
		aboutLink.setOnAction(e -> AboutDialog.openDialog());

		// add left-aligned elements
		HBox leftButtons = new HBox();
		leftButtons.getChildren().add(openProjectButton);
		leftButtons.setSpacing(5);
		this.setLeft(leftButtons);

		// add centered element (logo)
		this.setCenter(logo);

		// add right-aligned elements
		HBox rightButtons = new HBox();
		rightButtons.getChildren().add(helpLink);
		rightButtons.getChildren().add(aboutLink);
		rightButtons.getChildren().add(exitButton);
		this.setRight(rightButtons);

		this.setPadding(new Insets(5f, 5f, 5f, 5f));
	}
}
