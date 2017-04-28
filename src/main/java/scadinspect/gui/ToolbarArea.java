package scadinspect.gui;

import java.io.InputStream;
import java.util.prefs.Preferences;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import scadinspect.control.CodeAnalyzer;
import scadinspect.control.ProjectHandling;

/**
 * Toolbar at the top of the main window
 *
 * @author ivan
 */
public class ToolbarArea extends ToolBar {

    // initialize buttons
    VBox vbox = new VBox();
    Pane seperatorPane = new Pane();

    private MenuItem openFileButton = new MenuItem(Messages.getString("ToolbarArea.openFileButtonText"), loadIcon(Messages.getString("ToolbarArea.openFileIcon")));  //$NON-NLS-2$
    private MenuItem openFolderButton = new MenuItem(Messages.getString("ToolbarArea.openFolderButtonText"), loadIcon(Messages.getString("ToolbarArea.openFolderIcon")));  //$NON-NLS-2$
    private SplitMenuButton openProjectButton = new SplitMenuButton(openFileButton, openFolderButton);
    private Preferences userPrefs = Preferences.userRoot().node(Messages.getString("ToolbarArea.userPrefs")); 
    private Button settingsButton = new Button(Messages.getString("ToolbarArea.settingsButton")); 
    private Hyperlink helpLink = new Hyperlink(Messages.getString("ToolbarArea.helpLink")); 
    private Hyperlink aboutLink = new Hyperlink(Messages.getString("ToolbarArea.aboutLink")); 
    private Separator separator = new Separator();


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
        openProjectButton.setGraphic(loadIcon(Messages.getString("ToolbarArea.openProjectButtonIcon"))); 
        // Read settings
        if (userPrefs.getInt("SET_OPENBUTTON", 0) == 0) { 
            openProjectButton.setText(Messages.getString("ToolbarArea.openProjectButtonText")); 
            openProjectButton.setOnAction(event -> {
              projectHandler.openProjectFile();
              CodeAnalyzer.refresh();
            });
        } else {
            openProjectButton.setText("ToolbarArea.openFolderButtonText"); 
            openProjectButton.setOnAction(event -> {
              //Loading of multiple files is non blocking
              projectHandler.openProjectFolder((files) -> {
                if(files != null) {
                  Main.getInstance().getFileList().addAll(files);
                  CodeAnalyzer.refresh();
                }
              });
            });
        }
        openFolderButton.setOnAction(e -> {
            userPrefs.putInt("SET_OPENBUTTON", 1); 
            openProjectButton.setText("ToolbarArea.openFolderButtonText"); 
            openProjectButton.setOnAction(event -> {
              //Loading of multiple files is non blocking
              projectHandler.openProjectFolder((files) -> {
                if(files != null) {
                  Main.getInstance().getFileList().addAll(files);
                  CodeAnalyzer.refresh();
                }
              });
            });
        });
        openFileButton.setOnAction(e -> {
            userPrefs.putInt("SET_OPENBUTTON", 0); 
            openProjectButton.setText(Messages.getString("ToolbarArea.openProjectButton")); 
            openProjectButton.setOnAction(event -> {
              projectHandler.openProjectFile();
              CodeAnalyzer.refresh();
            });
        });

        // set button icons
        settingsButton.setGraphic(loadIcon(Messages.getString("ToolbarArea.settingsButtonIcon"))); 
        helpLink.setGraphic(loadResizedIcon(Messages.getString("ToolbarArea.helpLinkIcon"))); 
        aboutLink.setGraphic(loadResizedIcon(Messages.getString("ToolbarArea.aboutLinkIcon"))); 

        // status of buttons
        disableButtons(true);


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
        this.getItems().add(openProjectButton);
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
                settingsButton.setVisible(false);
                separator.setVisible(false);
                helpLink.setVisible(false);
                aboutLink.setVisible(false);
                openProjectButton.setVisible(true);

                //disable all buttons
                openProjectButton.setMouseTransparent(true);
                settingsButton.setMouseTransparent(true);
                break;
            }
            case 1: {
                openProjectButton.setVisible(false);
                settingsButton.setVisible(true);
                break;
            }
            case 2: {
                settingsButton.setVisible(false);
                break;
            }
            case 3: {
                settingsButton.setVisible(false);
                break;
            }

        }
    }

}
