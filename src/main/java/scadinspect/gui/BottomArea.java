package scadinspect.gui;

import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import scadinspect.control.ProjectHandling;

import java.io.InputStream;

/**
 * Bottombar below the main window
 *
 * @author maikbaumgartner
 * @author jonasber
 * @author bigpen1s
 */
public class BottomArea extends ToolBar{

    //initialize buttons
    private Button closeProjectButton = new Button("Close");
    private Button exportButton = new Button("Export");
    private Button refreshButton = new Button("Refresh");

    //initialize SeparatorPane to align Buttons
    private Pane separatorPane = new Pane();

    private ImageView loadIcon(String fileName) {
        InputStream inputStream = Main.class.getResourceAsStream(Main.RESOURCES_DIR + fileName + ".png");
        Image image = new Image(inputStream);
        ImageView imageView = new ImageView(image);
        return imageView;
    }
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
        //set button icons
        closeProjectButton.setGraphic(loadIcon("cross-mark-on-a-black-circle-background"));
        exportButton.setGraphic(loadIcon("text-file"));
        refreshButton.setGraphic(loadIcon("refresh-page-option"));
        //status of buttons
        disableButtons(true);
        // action listeners
        closeProjectButton.setOnAction(e -> ProjectHandling.closeProject());
        refreshButton.setOnAction(e -> Main.getInstance().statusArea.simulateProgress());
        // TODO: export Button action

        //Expands the separator pane
        HBox.setHgrow(separatorPane, Priority.ALWAYS);
        //adding all buttons
        this.getItems().add(refreshButton);
        this.getItems().add(new Separator());
        this.getItems().add(separatorPane);
        this.getItems().add(exportButton);
        this.getItems().add(closeProjectButton);

    }
}
