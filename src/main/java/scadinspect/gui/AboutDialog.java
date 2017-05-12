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

import java.util.ArrayList;
import java.util.List;

/*
 * Edited by Tim Walter on 05.04.2017 (Sprint1 Review Fix)
 */
public class AboutDialog {

    private final static Dialog<Boolean> dialog = new Dialog<>();

    // Text content
    private final static String aboutContent =
            "Static code analysis and javadoc-like parts documentation for your OpenSCAD-Files \n" +
                    "\n" +
                    "© 2017 Licensed under GNU General Public License v3.0\n" +
                    "For more information visit:";

    private final static String contributorsContent =
            "Contributors:\n" +
                    "-Ivan Bogicevic\n" +
                    "-Tim Walter\n" +
                    "-Jonas Bernsdorff\n" +
                    "-Malcolm Malam\n" +
                    "-Maik Baumgartner\n" +
                    "-Desyon\n" +
                    "-Romy Römke";

    private final static String thirdPartyContent =
            "Third Party Artefacts\n" +
                    "SCADinspect uses Icons from the \"Font Awesome\"-Package by Dave Gandy released under the CC BY 3.0 Licence\n";

    // Set logo
    private static final Image logo = new Image("http://www.ghanaedudirectory.com/Images/nologo.jpg");

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
        dialog.setTitle("About");
        dialog.setHeaderText("SCADinspect - Version x.xx");


        // Set the button types.
        ButtonType okButton = new ButtonType("OK", ButtonData.CANCEL_CLOSE);

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

        Hyperlink scadinspect = new Hyperlink("https://github.com/ibogicevic/SCADinspect");
        links.add(scadinspect);

        Hyperlink flaticon = new Hyperlink("http://www.flaticon.com/authors/dave-gandy");
        links.add(flaticon);

        Hyperlink creativeCommons = new Hyperlink("http://creativecommons.org/licenses/by/3.0/");
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
