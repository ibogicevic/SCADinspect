package scadinspect.gui;

import java.io.InputStream;
import java.nio.file.attribute.PosixFilePermission;

import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
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
    Pane seperatorPane = new Pane();
    private Button openProjectFileButton = new Button("Open file");
    private Button openProjectFolderButton = new Button("Open folder");    private Button settingsButton = new Button("Settings");
    private Hyperlink helpLink = new Hyperlink("Help");
    private Hyperlink aboutLink = new Hyperlink("About");
    private Separator separator = new Separator();


    /**
     * Disable buttons when no project is open
     *
     * @param value true if buttons shall be disabled (no open project)
     */
    public void disableButtons(boolean value) {

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
    private ImageView loadResizedIcon(String fileName) {
        InputStream inputStream = Main.class.getResourceAsStream(Main.RESOURCES_DIR + fileName + ".png");
        Image image = new Image(inputStream);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(12);
        imageView.setFitWidth(12);
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
        settingsButton.setGraphic(loadIcon("cog-wheel-silhouette"));
        helpLink.setGraphic(loadResizedIcon("help-icon"));
        aboutLink.setGraphic(loadResizedIcon("about-icon"));

        // status of buttons
        disableButtons(true);
        // actionlisteners
        openProjectFileButton.setOnAction(e -> projectHandler.openProjectFile());
        openProjectFolderButton.setOnAction(e -> projectHandler.openProjectFolder());

        helpLink.setOnAction(e -> {
            Main.getInstance().greyStack.toFront();
            Main.getInstance().greyStack.setVisible(true);
            Main.getInstance().helpPane.modalToFront(true);
            Main.getInstance().helpPane.switchTour(0);
        });
        aboutLink.setOnAction(e -> AboutDialog.openDialog());
        settingsButton.setOnAction(e -> SettingsDialog.openDialog());
        separator = new Separator();
        // add all buttons
        this.getItems().add(openProjectFileButton);
        this.getItems().add(openProjectFolderButton);
        this.getItems().add(separator);
        this.getItems().add(settingsButton);
        //this.getItems().add(separator);

        HBox.setHgrow(seperatorPane, Priority.ALWAYS);
        vbox.getChildren().add(helpLink);
        vbox.getChildren().add(aboutLink);
        this.getItems().add(seperatorPane);
        this.getItems().add(vbox);
    }



     // this function is necessary to highlight the specific buttons for each tutorial step

    public void switchButtons(Integer button){


        switch (button) {
            case 0: {
                // hide all buttons except from openFile
                openProjectFolderButton.setVisible(false);
                settingsButton.setVisible(false);
                separator.setVisible(false);
                helpLink.setVisible(false);
                aboutLink.setVisible(false);
                openProjectFileButton.setVisible(true);

                //disable all buttons
                openProjectFileButton.setDisable(true);
                openProjectFolderButton.setDisable(true);
                settingsButton.setDisable(true);
                break;
            }
            case 1: {
                openProjectFileButton.setVisible(false);
                openProjectFolderButton.setVisible(true);
                settingsButton.setVisible(false);
                break;
            }
            case 2: {
                openProjectFolderButton.setVisible(false);
                settingsButton.setVisible(true);
                break;
            }
            case 3: {
                settingsButton.setVisible(false);
                break;
            }

        }
    }

}
