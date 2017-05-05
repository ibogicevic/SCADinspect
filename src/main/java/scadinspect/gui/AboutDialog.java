/*
 * 
 * @author: schmjuli
 */
package scadinspect.gui;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class AboutDialog {

  /*
   *
   * Opens a new dialog and displays 'about information'
   */
  public static void openDialog() {
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
    grid.add(text1, 1, 0);

    Label text2 = new Label(Text2);
    text2.setWrapText(true);
    grid.add(text2, 1, 1);

    dialog.getDialogPane().setContent(grid);
    dialog.showAndWait();


  }

  // Content
  final static String Text1 =
      "Static code analysis and javadoc-like parts documentation for your OpenSCAD-Files \n" +
          "Visit https://github.com/ibogicevic/SCADinspect for more information\n" +
          "\n" +
          "Contributors:\n" +
          "-Ivan Bogicevic\n"
          + "-Tim Walter\n"
          + "-Tom Richter\n"
          + "-Maik Baumgartner\n"
          + "-Lisa Milius\n"
          + "-Simon Steinrücken";

  final static String Text2 =
      "Buttons used from http://www.flaticon.com/authors/dave-gandy\n" +
          "\n" +
          "© 2017 Licensed under GNU General Public License v2.0";

  private static final Image logo = new Image("http://www.ghanaedudirectory.com/Images/nologo.jpg");
}
