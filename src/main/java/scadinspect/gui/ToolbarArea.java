package scadinspect.gui;

import java.io.InputStream;
import java.nio.file.attribute.PosixFilePermission;
import java.util.prefs.Preferences;

import javafx.geometry.Pos;
import javafx.scene.control.*;
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

    private MenuItem openFileButton = new MenuItem("Open file", loadIcon("open-folder-outline"));
    private MenuItem openFolderButton = new MenuItem("Open folder", loadIcon("open-folder-outline"));
    private SplitMenuButton openProjectButton = new SplitMenuButton(openFileButton, openFolderButton);
    private Preferences userPrefs = Preferences.userRoot().node("DHBW.SCADInspect.Settings");
    private Button settingsButton = new Button("Settings");
    private Hyperlink helpLink = new Hyperlink("Help");
    private Hyperlink aboutLink = new Hyperlink("About");


    /**
     * Disable buttons when no project is open
     *
     * @param value true if buttons shall be disabled (no open project)
     */
    public void disableButtons(boolean value) {


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


        // configure open button
        openProjectButton.setGraphic(loadIcon("open-folder-outline"));
        // Read settings
        if (userPrefs.getInt("SET_OPENBUTTON", 0) == 0) {
            openProjectButton.setText("Open file");
            openProjectButton.setOnAction(event -> projectHandler.openProjectFile());
        } else {
            openProjectButton.setText("Open Folder");
            openProjectButton.setOnAction(event -> projectHandler.openProjectFolder());
        }
        openFolderButton.setOnAction(e -> {
            userPrefs.putInt("SET_OPENBUTTON", 1);
            openProjectButton.setText("Open Folder");
            openProjectButton.setOnAction(event -> projectHandler.openProjectFolder());
        });
        openFileButton.setOnAction(e -> {
            userPrefs.putInt("SET_OPENBUTTON", 0);
            openProjectButton.setText("Open File");
            openProjectButton.setOnAction(event -> projectHandler.openProjectFile());
        });

        // set button icons
        settingsButton.setGraphic(loadIcon("cog-wheel-silhouette"));
        helpLink.setGraphic(loadResizedIcon("help-icon"));
        aboutLink.setGraphic(loadResizedIcon("about-icon"));

        // status of buttons
        disableButtons(true);


        helpLink.setOnAction(e -> ProjectHandling.showModal());
        aboutLink.setOnAction(e -> AboutDialog.openDialog());
        settingsButton.setOnAction(e -> SettingsDialog.openDialog());
        exportButton.setOnAction(e -> ExportDialog.openDialog());
        exitButton.setOnAction(e -> Platform.exit());

        // add all buttons
        this.getItems().add(openProjectButton);
        this.getItems().add(new Separator());
        this.getItems().add(settingsButton);
        this.getItems().add(new Separator());

        HBox.setHgrow(seperatorPane, Priority.ALWAYS);
        vbox.getChildren().add(helpLink);
        vbox.getChildren().add(aboutLink);
        this.getItems().add(seperatorPane);
        this.getItems().add(vbox);
    }
}
