package scadinspect.gui.areas;

import java.util.prefs.Preferences;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import scadinspect.control.CodeAnalyzer;
import scadinspect.control.ProjectHandling;
import scadinspect.gui.Main;
import scadinspect.gui.Resources;
import scadinspect.gui.dialogs.AboutDialog;
import scadinspect.gui.dialogs.SettingsDialog;
import javafx.scene.layout.BorderPane;

/**
 * Toolbar at the top of the main window
 */
public class ToolbarArea extends BorderPane {

	// settings content
    private final Preferences userPrefs = Preferences.userRoot().node("DHBW.SCADInspect.Settings");
	
    // initialize buttons
    private final MenuItem openFileButton = new MenuItem("Open file", Resources.loadIcon("open-folder-outline"));
    private final MenuItem openFolderButton = new MenuItem("Open folder", Resources.loadIcon("open-folder-outline"));
    private final SplitMenuButton openProjectButton = new SplitMenuButton(openFileButton, openFolderButton);
    private final Pane separatorPane = new Pane();
    private final Pane separatorPane2 = new Pane();
    private final Button settingsButton = new Button("Settings");
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
        if (userPrefs.getInt("SET_OPENBUTTON", 0) == 0) {
            openProjectButton.setText("Open file");
            openProjectButton.setOnAction(event -> {
              projectHandler.openProjectFile();
              CodeAnalyzer.refresh();
                Main.getInstance().tabArea.getDocumentationList().refresh();
            });
        } else {
            openProjectButton.setText("Open folder");
            openProjectButton.setOnAction(event -> {
              // loading of multiple files is non blocking
              projectHandler.openProjectFolder((files) -> {
                if(files != null) {
                  Main.getInstance().getFileList().addAll(files);
                  CodeAnalyzer.refresh();
                    Platform.runLater(() -> {
                        Main.getInstance().tabArea.getDocumentationList().refresh();
                    });
                }
              });
            });
        }
        openFolderButton.setOnAction(e -> {
            userPrefs.putInt("SET_OPENBUTTON", 1);
            openProjectButton.setText("Open folder");
            openProjectButton.setOnAction(event -> {
              // loading of multiple files is non blocking
              projectHandler.openProjectFolder((files) -> {
                if(files != null) {
                  Main.getInstance().getFileList().addAll(files);
                  CodeAnalyzer.refresh();
                    Platform.runLater(() -> {
                        Main.getInstance().tabArea.getDocumentationList().refresh();
                    });
                }
              });
            });
        });
        openFileButton.setOnAction(e -> {
            userPrefs.putInt("SET_OPENBUTTON", 0);
            openProjectButton.setText("Open file");
            openProjectButton.setOnAction(event -> {
              projectHandler.openProjectFile();
              CodeAnalyzer.refresh();
                Main.getInstance().tabArea.getDocumentationList().refresh();
            });
        });
        exitButton.setOnAction(e -> {
        	Platform.exit();
        });

        // images
        settingsButton.setGraphic(Resources.loadIcon("cog-wheel-silhouette"));
        helpLink.setGraphic(Resources.loadIconSmall("help-icon"));
        aboutLink.setGraphic(Resources.loadIconSmall("about-icon"));
        exitButton.setGraphic(Resources.loadIcon("sign-out-option"));
        ImageView logo = Resources.loadIcon("logo2");
        logo.setPreserveRatio(true);
        logo.setFitHeight(25);
        
        // status of buttons
        disableButtons(true);
        helpLink.setOnAction(e -> {
            Main.getInstance().greyStack.toFront();
            Main.getInstance().greyStack.setVisible(true);
            Main.getInstance().helpPane.modalToFront(true);
            Main.getInstance().helpPane.switchTour(-1);
        });
        aboutLink.setOnAction(e -> AboutDialog.openDialog());
        settingsButton.setOnAction(e -> SettingsDialog.openDialog());
        
        // add left-aligned elements
        HBox leftButtons = new HBox();
        leftButtons.getChildren().add(openProjectButton);
        leftButtons.getChildren().add(settingsButton);
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



     // this function is necessary to highlight the specific buttons for each tutorial step

    public void switchButtons(Integer button){


//        switch (button) {
//            case -1: {
//                settingsButton.setVisible(false);
//                separator.setVisible(false);
//                helpLink.setVisible(false);
//                aboutLink.setVisible(false);
//                openProjectButton.setVisible(false);
//                break;
//            }
//            case 0: {
//                // hide all buttons except from openFile
//                settingsButton.setVisible(false);
//                separator.setVisible(false);
//                helpLink.setVisible(false);
//                aboutLink.setVisible(false);
//                openProjectButton.setVisible(true);
//
//                //disable all buttons
//                openProjectButton.setMouseTransparent(true);
//                settingsButton.setMouseTransparent(true);
//                break;
//            }
//            case 1: {
//                openProjectButton.setVisible(false);
//                settingsButton.setVisible(true);
//                break;
//            }
//            case 2: {
//                settingsButton.setVisible(false);
//                break;
//            }
//            case 3: {
//                settingsButton.setVisible(false);
//                break;
//            }
//
//        }
    }
}
