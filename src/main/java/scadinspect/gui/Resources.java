package scadinspect.gui;

import java.io.InputStream;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Resources {
	/**
     * Loads a specific icon from the res-folder
     *
     * @return the icon as ImageView
     */
    public static ImageView loadIcon(String fileName) {
        InputStream inputStream = Main.class.getResourceAsStream(Main.RESOURCES_DIR + fileName + ".png");
        Image image = new Image(inputStream);
        ImageView imageView = new ImageView(image);
        return imageView;
    }
    public static ImageView loadResizedIcon(String fileName) {
        InputStream inputStream = Main.class.getResourceAsStream(Main.RESOURCES_DIR + fileName + ".png");
        Image image = new Image(inputStream);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(12);
        imageView.setFitWidth(12);
        return imageView;
    }
}