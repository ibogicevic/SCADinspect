/**
 * Opens the about dialog if the About-Button is clicked
 *
 * @author: schmjuli
 */
package scadinspect.gui;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/*
 * Edited by Tim Walter on 05.04.2017 (Sprint1 Review Fix)
 */
public class AboutDialog {

    private final static Dialog<Boolean> dialog = new Dialog<>();

    // Text content
    private final static String aboutContent = "AboutDialog.Description";

    private final static String contributorsContent = "AboutDialog.Contributor";

    private final static String thirdPartyContent = "AboutDialog.ThirdParty";

    // Set logo
    private static final Image logo = new Image("AboutDialog.Logo");

    /**
     * Application startup function
     */
    public static void openDialog() {
        /* final Properties properties = new Properties();
        try {
            properties.load(dialog.getClass().getResourceAsStream("project.properties"));
        } catch (IOException e) {
            //TODO: Log
        } */


        //ProjectHandling.showModal();
        Main.getInstance().greyPane.modalToFront(true);
        Main.getInstance().greyStack.toFront();
        Main.getInstance().greyStack.setVisible(true);

        //Main.getInstance().greyStack
        dialog.setTitle("AboutDialog.Title");
        dialog.setHeaderText("AboutDialog.HeaderText");


        // Set the button types.
        ButtonType okButton = new ButtonType("AboutDialog.okButton", ButtonData.CANCEL_CLOSE);

        // Display the buttons
        dialog.getDialogPane().getButtonTypes().addAll(okButton);

        // Arrange Content
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // Display Logo
        ImageView imageView = new ImageView(logo);
        imageView.setFitWidth(128);
        imageView.setFitHeight(128);
        StackPane stackPane = new StackPane(imageView);
        stackPane.setAlignment(Pos.CENTER);
        grid.add(stackPane, 0, 0);

        // Display Text
        Label about = new Label(aboutContent);
        about.setWrapText(true);

        Label contributors = new Label(contributorsContent);
        contributors.setWrapText(true);
        grid.add(contributors, 1, 1);

        Label thirdParty = new Label(thirdPartyContent);
        thirdParty.setWrapText(true);

        //Links for text content
        List<Hyperlink> links = new ArrayList<>();

        Hyperlink scadinspect = new Hyperlink("AboutDialog.Git");
        links.add(scadinspect);

        Hyperlink flaticon = new Hyperlink("AboutDialog.Flaticon");
        links.add(flaticon);

        Hyperlink creativeCommons = new Hyperlink("AboutDialog.creativeCommons");
        links.add(creativeCommons);

        for(final Hyperlink hyperlink : links) {
            hyperlink.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    Main.getInstance().getHostServices().showDocument(hyperlink.getText());
                }
            });
        }

        VBox Box1 = new VBox();
        Box1.getChildren().add(about);
        Box1.getChildren().add(scadinspect);
        grid.add(Box1, 1,0);

        VBox Box2 = new VBox();
        Box2.getChildren().add(thirdParty);
        Box2.getChildren().add(flaticon);
        Box2.getChildren().add(creativeCommons);
        grid.add(Box2, 1,2);

        dialog.getDialogPane().setContent(grid);
        dialog.showAndWait();
        Main.getInstance().greyStack.toBack();
        Main.getInstance().greyStack.setVisible(false);
    }
}