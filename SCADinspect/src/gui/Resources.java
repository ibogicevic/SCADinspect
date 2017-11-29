package gui;

import java.io.InputStream;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Resources {
	
    private static final String RESOURCES_DIR = "/res/";	
	/**
     * Loads a specific icon from the res-folder
     *
     * @return the icon as ImageView
     */
    public static ImageView loadIcon(String fileName) {
        InputStream inputStream = Main.class.getResourceAsStream(RESOURCES_DIR + fileName + ".png");
        Image image = new Image(inputStream);
        ImageView imageView = new ImageView(image);
        return imageView;
    }
    public static ImageView loadIconSmall(String fileName) {
        ImageView imageView = loadIcon(fileName);
        imageView.setFitHeight(12);
        imageView.setFitWidth(12);
        return imageView;
    }
}
