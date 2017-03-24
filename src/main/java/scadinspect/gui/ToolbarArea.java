package scadinspect.gui;

import java.io.InputStream;

import javafx.scene.control.Hyperlink;
import javafx.scene.layout.VBox;
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
    VBox vbox = new VBox();
    private Button openProjectButton = new Button("Open");
    //private Button closeProjectButton = new Button("Close");
   // private Button refreshButton = new Button("Refresh");
    //private Button exportButton = new Button("Export");
    private Button settingsButton = new Button("Settings");
    //private Button helpButton = new Button("Help");
    private Hyperlink helpLink = new Hyperlink("Help");
    //private Button aboutButton = new Button("About");
    private Hyperlink aboutLink = new Hyperlink("About");
    //private Button exitButton = new Button("Exit");

    /**
     * Disable buttons when no project is open
     *
     * @param value true if buttons shall be disabled (no open project)
     */
    public void disableButtons(boolean value) {
        //closeProjectButton.setDisable(value);
        //refreshButton.setDisable(value);
        //exportButton.setDisable(value);
        settingsButton.setDisable(value);
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
        // set button icons
        openProjectButton.setGraphic(loadIcon("open-folder-outline"));
        //closeProjectButton.setGraphic(loadIcon("cross-mark-on-a-black-circle-background"));
        //refreshButton.setGraphic(loadIcon("refresh-page-option"));
        //exportButton.setGraphic(loadIcon("text-file"));
        settingsButton.setGraphic(loadIcon("cog-wheel-silhouette"));
        helpLink.setGraphic(loadIcon("question-sign"));
        aboutLink.setGraphic(loadIcon("information-symbol"));
        //exitButton.setGraphic(loadIcon("sign-out-option"));
        // status of buttons
        disableButtons(true);
        // actionlisteners
        openProjectButton.setOnAction(e -> ProjectHandling.openProject());
        //closeProjectButton.setOnAction(e -> ProjectHandling.closeProject());
        //exitButton.setOnAction(e -> Platform.exit());
        //refreshButton.setOnAction(e -> Main.getInstance().statusArea.simulateProgress());
        /*exitButton.setOnAction(e -> {
            Platform.exit();
        });*/
        helpLink.setOnAction(e -> ProjectHandling.openProject());
        aboutLink.setOnAction(e -> AboutDialog.openDialog());
        settingsButton.setOnAction(e -> SettingsDialog.openDialog());
        // add all buttons
        this.getItems().add(openProjectButton);
        //this.getItems().add(closeProjectButton);
        //this.getItems().add(refreshButton);
        this.getItems().add(new Separator());
        //this.getItems().add(exportButton);
        this.getItems().add(settingsButton);
        this.getItems().add(new Separator());
        //this.getItems().add(helpButton);
        //vbox.getItems().add(helpLink);
        //vbox.getItems().add(aboutLink);
        vbox.getChildren().add(helpLink);
        vbox.getChildren().add(aboutLink);
        this.getItems().add(vbox);
        //this.getItems().add(aboutButton);
        //this.getItems().add(exitButton);
    }
}
