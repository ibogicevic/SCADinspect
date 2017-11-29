package gui.areas;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import util.ResourceLoader;
import control.ProjectHandler;
import gui.Main;
import gui.dialogs.AboutDialog;
import gui.dialogs.ExportDialog;
import gui.dialogs.HelpDialog;
import javafx.scene.layout.BorderPane;

/** toolbar at the top of the main window */
public class ToolbarArea extends BorderPane {

	// constants
	private final int BUTTON_SPACING = 5;
	
	// initialize buttons
	private final MenuItem openFileButton = new MenuItem("Open file", ResourceLoader.loadIcon("open-folder-outline"));
	private final MenuItem openFolderButton = new MenuItem("Open folder", ResourceLoader.loadIcon("open-folder-outline"));
	private final SplitMenuButton openProjectButton = new SplitMenuButton(openFileButton, openFolderButton);
	private final Button closeProjectButton = new Button("Close");
	private final CheckMenuItem autoRefreshItem = new CheckMenuItem("Autorefresh");
	private final SplitMenuButton refreshButton = new SplitMenuButton(autoRefreshItem);
	private final Button exportButton = new Button("Export");
	private final Hyperlink helpLink = new Hyperlink("Help");
	private final Hyperlink aboutLink = new Hyperlink("About");
	private final Button exitButton = new Button("Exit");
	private final ProjectHandler projectHandler = new ProjectHandler();

	public ProjectHandler getProjectHandler() {
		return projectHandler;
	}

	/** constructor */
	public ToolbarArea() {
		
		// graphics
		openProjectButton.setGraphic(ResourceLoader.loadIcon("open-folder-outline"));
		closeProjectButton.setGraphic(ResourceLoader.loadIcon("cross-mark-on-a-black-circle-background"));
		refreshButton.setGraphic(ResourceLoader.loadIcon("refresh-page-option"));
		exportButton.setGraphic(ResourceLoader.loadIcon("text-file"));
		helpLink.setGraphic(ResourceLoader.loadIconSmall("help-icon"));
		aboutLink.setGraphic(ResourceLoader.loadIconSmall("about-icon"));
		exitButton.setGraphic(ResourceLoader.loadIcon("sign-out-option"));
		ImageView logo = ResourceLoader.loadIcon("logo3");
		logo.setPreserveRatio(true);
		logo.setFitHeight(25);
		
		// open project/file/folder action listeners
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

		// simple action listeners
		closeProjectButton.setOnAction(e -> projectHandler.closeProject());
		refreshButton.setText("Refresh");
		refreshButton.setOnAction(e -> {Main.getInstance().contentArea.refresh();});
		exportButton.setOnAction(e -> ExportDialog.openDialog());
		exitButton.setOnAction(e -> {Platform.exit();});
		helpLink.setOnAction(e -> HelpDialog.openDialog());
		aboutLink.setOnAction(e -> AboutDialog.openDialog());
		
		// add left-aligned elements
		HBox leftElements = new HBox();
		leftElements.getChildren().add(openProjectButton);
		leftElements.setSpacing(BUTTON_SPACING);
		leftElements.getChildren().add(closeProjectButton);
		leftElements.setSpacing(BUTTON_SPACING);
		leftElements.getChildren().add(refreshButton);
		leftElements.setSpacing(BUTTON_SPACING);
		leftElements.getChildren().add(exportButton);
		this.setLeft(leftElements);

		// add right-aligned elements
		HBox rightElements = new HBox();
		rightElements.getChildren().add(helpLink);
		rightElements.getChildren().add(aboutLink);
		rightElements.setSpacing(5);
		rightElements.getChildren().add(logo);
		rightElements.getChildren().add(exitButton);
		this.setRight(rightElements);

		this.setPadding(new Insets(BUTTON_SPACING, BUTTON_SPACING, BUTTON_SPACING, BUTTON_SPACING));
		ProjectHandler projectHandler = new ProjectHandler();

		//status of buttons
		disableButtons(true);
	}

	/**
	 * Disable buttons when no project is open
	 * @param value true if buttons shall be disabled (no open project)
	 */
	public void disableButtons(boolean value) {
		closeProjectButton.setDisable(value);
		refreshButton.setDisable(value);
		exportButton.setDisable(value);
	}

}