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

public class AboutDialog {

    /**
     * Application startup function
     */
    public static void openDialog() {
        //ProjectHandling.showModal();
        Main.getInstance().greyPane.modalToFront(true);
        Main.getInstance().greyStack.toFront();
        Main.getInstance().greyStack.setVisible(true);

        //Main.getInstance().greyStack
        Dialog<Boolean> dialog = new Dialog<>();
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
        Label text1 = new Label(Text1);
        text1.setWrapText(true);

        Label text2 = new Label(Text2);
        text2.setWrapText(true);
        grid.add(text2, 1, 1);

        Label text3 = new Label(Text3);
        text3.setWrapText(true);

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
        Box1.getChildren().add(text1);
        Box1.getChildren().add(scadinspect);
        grid.add(Box1, 1,0);

        VBox Box2 = new VBox();
        Box2.getChildren().add(text3);
        Box2.getChildren().add(flaticon);
        Box2.getChildren().add(creativeCommons);
        grid.add(Box2, 1,2);

        dialog.getDialogPane().setContent(grid);
        dialog.showAndWait();
        Main.getInstance().greyStack.toBack();
        Main.getInstance().greyStack.setVisible(false);


    }

    // Text content
    final static String Text1 =
            "Static code analysis and javadoc-like parts documentation for your OpenSCAD-Files \n" +
                    "\n" +
                    "© 2017 Licensed under GNU General Public License v3.0\n" +
                    "For more information visit:";

    final static String Text2 =
            "Contributors:\n" +
                    "-Ivan Bogicevic\n" +
                    "-Tim Walter\n"
                    + "-Tom Richter\n"
                    + "-Maik Baumgartner\n"
                    + "-Lisa Milius\n"
                    + "-Simon Steinrücken"
                    + "-Jonas Bernsdorff\n"
                    + "-Malcolm Malam\n"
                    + "-Desyon\n"
                    + "-Romy Römke"
                    + "-Jonas Balsfulland"
                    + "-Romy Römke"
                    + "-Felix Stegmaier"
                    + "-Julian Schmidt"
                    + "-Orhan Bilir"
                    + "-Jokke Jansen"
                    + "-Romy Römke"
                    + "-Christoph Auf der Landwehr";

    final static String Text3 =
            "Third Party Artefacts\n" +
                    "SCADinspect uses Icons from the \"Font Awesome\"-Package by Dave Gandy released under the CC BY 3.0 Licence\n";

    // Set logo
    private static final Image logo = new Image("http://www.ghanaedudirectory.com/Images/nologo.jpg");
}
