package scadinspect.gui.areas;

import java.io.InputStream;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import scadinspect.control.ProjectHandling;
import scadinspect.gui.Main;
import scadinspect.gui.Resources;
import scadinspect.gui.dialogs.ExportDialog;

/**
 * Bottombar below the main window
 *
 * @author maikbaumgartner
 * @author jonasber
 * @author bigpen1s
 */
public class BottomArea extends ToolBar{

    //initialize buttons
    private final Button closeProjectButton = new Button("Close");
    private final Button exportButton = new Button("Export");
    private final Button refreshButton = new Button("Refresh");

    //initialize SeparatorPane to align Buttons
    private Pane separatorPane = new Pane();

    /**
     * Disable buttons when no project is open
     *
     * @param value true if buttons shall be disabled (no open project)
     */
    public void disableButtons(boolean value) {
        closeProjectButton.setDisable(value);
        refreshButton.setDisable(value);
        exportButton.setDisable(value);

    }

    /**
     * Constructor of BottomArea
     */
    public BottomArea() {
        ProjectHandling projectHandler = new ProjectHandling();

        //set button icons
        closeProjectButton.setGraphic(Resources.loadIcon("cross-mark-on-a-black-circle-background"));
        exportButton.setGraphic(Resources.loadIcon("text-file"));
        refreshButton.setGraphic(Resources.loadIcon("refresh-page-option"));
        //status of buttons
        disableButtons(true);
        // action listeners
        closeProjectButton.setOnAction(e -> projectHandler.closeProject());
        refreshButton.setOnAction(e -> {
          //Main.getInstance().statusArea.simulateProgress();
          //CodeAnalyzer.refresh();
            //TODO make thread
            Main.getInstance().tabArea.getDocumentationList().refresh();
        });
        exportButton.setOnAction(e -> ExportDialog.openDialog());

        //Expands the separator pane
        HBox.setHgrow(separatorPane, Priority.ALWAYS);
        //adding all buttons
        this.getItems().add(refreshButton);
        this.getItems().add(separatorPane);
        this.getItems().add(exportButton);
        this.getItems().add(closeProjectButton);

    }

    //this function is necessary to highlight the specific buttons for the help tour
    public void switchButtons(Integer button){
        switch (button){
            case 0: {
                refreshButton.setVisible(false);
                exportButton.setVisible(false);
                closeProjectButton.setVisible(false);

                refreshButton.setDisable(false);
                exportButton.setDisable(false);
                closeProjectButton.setDisable(false);

                refreshButton.setMouseTransparent(true);
                exportButton.setMouseTransparent(true);
                closeProjectButton.setMouseTransparent(true);
                break;
            }
            case 1: {
                refreshButton.setVisible(true);
                exportButton.setVisible(false);
                break;
            }
            case 2: {
                refreshButton.setVisible(false);
                exportButton.setVisible(true);
                closeProjectButton.setVisible(false);
                break;
            }
            case 3: {
                exportButton.setVisible(false);
                closeProjectButton.setVisible(true);
                break;
            }
        }
    }
}
