package scadinspect.gui;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;

/**
 * Bottombar below the main window
 *
 * @author maikbaumgartner
 * @author jonasber
 * @author bigpen1s
 */
public class BottomArea {

    //initialize buttons
    private Button closeProjectButton = new Button("Close");
    private Button exportProjectButton = new Button("Export");
    private Button refreshButton = new Button("Refresh");

    private ImageView loadIcon(String fileName) {
        InputStream inputStream = Main.class.getResourceAsStream(Main.RESOURCES_DIR + fileName + ".png");
        Image image = new Image(inputStream);
        ImageView imageView = new ImageView(image);
        return imageView;
    }

    public BottomArea() {
        //set button icons
        closeProjectButton.setGraphic(loadIcon("cross-mark-on-a-black-circle"));
        exportProjectButton.setGraphic(loadIcon("text-file"));
        refreshButton.setGraphic(loadIcon("refresh-page-option"));
    }
}
