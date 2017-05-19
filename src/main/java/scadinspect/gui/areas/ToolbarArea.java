package scadinspect.gui.areas;

import java.util.prefs.Preferences;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import scadinspect.control.CodeAnalyzer;
import scadinspect.control.ProjectHandling;
import scadinspect.gui.Main;
import scadinspect.gui.Resources;
import scadinspect.gui.dialogs.AboutDialog;
import scadinspect.gui.dialogs.SettingsDialog;

/**
 * Toolbar at the top of the main window
 */
public class ToolbarArea extends ToolBar {

    // initialize buttons
    private final Pane seperatorPane = new Pane();
    private final MenuItem openFileButton = new MenuItem("Open file", Resources.loadIcon("open-folder-outline"));
    private final MenuItem openFolderButton = new MenuItem("Open folder", Resources.loadIcon("open-folder-outline"));
    private final SplitMenuButton openProjectButton = new SplitMenuButton(openFileButton, openFolderButton);
    private final Preferences userPrefs = Preferences.userRoot().node("DHBW.SCADInspect.Settings");
    private final Button settingsButton = new Button("Settings");
    private final Hyperlink helpLink = new Hyperlink("Help");
    private final Hyperlink aboutLink = new Hyperlink("About");
    private final Button exitButton = new Button("Exit");
    private final ProjectHandling projectHandler = new ProjectHandling();
    private final Separator separator;

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

        // set button icons
        settingsButton.setGraphic(Resources.loadIcon("cog-wheel-silhouette"));
        helpLink.setGraphic(Resources.loadResizedIcon("help-icon"));
        aboutLink.setGraphic(Resources.loadResizedIcon("about-icon"));
        exitButton.setGraphic(Resources.loadIcon("sign-out-option"));
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
        separator = new Separator();
        // add all buttons
        this.getItems().add(openProjectButton);
        this.getItems().add(separator);
        this.getItems().add(settingsButton);
        // rightmost links
        HBox.setHgrow(seperatorPane, Priority.ALWAYS);
        final HBox box = new HBox();
        box.getChildren().add(helpLink);
        box.getChildren().add(aboutLink);
        box.getChildren().add(exitButton);
        this.getItems().add(seperatorPane);
        this.getItems().add(box);
    }



     // this function is necessary to highlight the specific buttons for each tutorial step

    public void switchButtons(Integer button){


        switch (button) {
            case -1: {
                settingsButton.setVisible(false);
                separator.setVisible(false);
                helpLink.setVisible(false);
                aboutLink.setVisible(false);
                openProjectButton.setVisible(false);
                break;
            }
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
