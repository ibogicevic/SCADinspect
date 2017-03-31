package scadinspect.gui;

import java.io.InputStream;

import scadinspect.control.ProjectHandling;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Toolbar at the top of the main window
 *
 * @author ivan
 */
public class ToolbarArea extends ToolBar {

    // initialize buttons
    private Button openProjectFileButton = new Button("Open file");
    private Button openProjectFolderButton = new Button("Open folder");
    private Button closeProjectButton = new Button("Close");
    private Button refreshButton = new Button("Refresh");
    private Button exportButton = new Button("Export");
    private Button settingsButton = new Button("Settings");
    private Button helpButton = new Button("Help");
    private Button aboutButton = new Button("About");
    private Button exitButton = new Button("Exit");

    /**
     * Disable buttons when no project is open
     *
     * @param value true if buttons shall be disabled (no open project)
     */
    public void setButtonsDisabled(boolean value) {
        closeProjectButton.setDisable(value);
        refreshButton.setDisable(value);
        exportButton.setDisable(value);
    }

    /**
     * Loads a specific icon from the res-folder
     *
     * @return the icon as ImageView
     */
    private ImageView loadIcon(String fileName) {
        InputStream inputStream = Main.class.getResourceAsStream(Main.RESOURCES_DIR + fileName + ".png");
        Image image = new Image(inputStream);
        ImageView imageView = new ImageView(image);
        return imageView;
    }

    /**
     * Constructor of ToolbarArea
     */
    public ToolbarArea() {
        // instanciate classes
        ProjectHandling projectHandler = new ProjectHandling();

        // set button icons
        openProjectFileButton.setGraphic(loadIcon("open-folder-outline"));
        openProjectFolderButton.setGraphic(loadIcon("open-folder-outline"));
        closeProjectButton.setGraphic(loadIcon("cross-mark-on-a-black-circle-background"));
        refreshButton.setGraphic(loadIcon("refresh-page-option"));
        exportButton.setGraphic(loadIcon("text-file"));
        settingsButton.setGraphic(loadIcon("cog-wheel-silhouette"));
        helpButton.setGraphic(loadIcon("question-sign"));
        aboutButton.setGraphic(loadIcon("information-symbol"));
        exitButton.setGraphic(loadIcon("sign-out-option"));
        // status of buttons
        setButtonsDisabled(true);
        // actionlisteners
        openProjectFileButton.setOnAction(e -> projectHandler.openProjectFile());
        openProjectFolderButton.setOnAction(e -> projectHandler.openProjectFolder());
        closeProjectButton.setOnAction(e -> projectHandler.closeProject());
        refreshButton.setOnAction(e -> Main.getInstance().statusArea.simulateProgress());
        aboutButton.setOnAction(e -> AboutDialog.openDialog());
        settingsButton.setOnAction(e -> SettingsDialog.openDialog());
        exportButton.setOnAction(e -> ExportDialog.openDialog());
        exitButton.setOnAction(e -> Platform.exit());
      
        // add all buttons
        this.getItems().add(openProjectFileButton);
        this.getItems().add(openProjectFolderButton);
        this.getItems().add(closeProjectButton);
        this.getItems().add(refreshButton);
        this.getItems().add(new Separator());
        this.getItems().add(exportButton);
        this.getItems().add(settingsButton);
        this.getItems().add(new Separator());
        this.getItems().add(helpButton);
        this.getItems().add(aboutButton);
        this.getItems().add(exitButton);
    }
}