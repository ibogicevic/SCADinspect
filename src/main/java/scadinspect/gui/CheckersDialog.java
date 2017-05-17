/**
 * Opens the Checkers Dialog
 *
 * @author: jonas_ber
 */
package scadinspect.gui;

import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class CheckersDialog {


  final static String Checker1Head = "1. Special Variable Checker";
  final static String Checker1Desc = "Detects variables which begin with a $ sign and are not reserved variables.";
  final static String Checker2Head = "Module Definition Checker";
  final static String Checker2Desc = "Detects if module definitions with a single statement are inside parantheses.";
  final static String Checker3Head = "Module Override Checker";
  final static String Checker3Desc = "Detects if built-in modules are overwritten.";
  final static String Checker4Head = "Function Override Checker";
  final static String Checker4Desc = "Detects if built-in functions are overwritten.";
  final static String Checker5Head = "Module Statement Count Checker";
  final static String Checker5Desc = "Detects if number of statements in a module exceeds the maximum number of 50 statements.";
  final static String Checker6Head = "File Statement Count Checker";
  final static String Checker6Desc = "Detects if number of statements in a file exceeds the maximum number of 200 statements.";

  public static void openDialog() {
    Main.getInstance().greyPane.modalToFront(true);
    Main.getInstance().greyStack.toFront();
    Main.getInstance().greyStack.setVisible(true);

    Dialog<Boolean> dialog = new Dialog<>();
    dialog.setTitle("Checkers");
    dialog.setHeaderText("Checker documentation");

    // Set the button types.
    ButtonType okButton = new ButtonType("OK", ButtonData.CANCEL_CLOSE);

    // Display the buttons
    dialog.getDialogPane().getButtonTypes().addAll(okButton);

    // Arrange Content
    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);

    Label welcome = new Label("Currently implemented checkers");
    welcome.setWrapText(true);
    welcome.setFont(Font.font(null, FontWeight.BLACK, 20));
    grid.add(welcome, 1, 1);

    Label head1 = new Label(Checker1Head);
    head1.setWrapText(true);
    head1.setFont(Font.font(null, FontWeight.BOLD, 16));
    grid.add(head1, 1, 2);

    Label desc1 = new Label(Checker1Desc);
    desc1.setWrapText(true);
    grid.add(desc1, 1, 3);

    Label section2 = new Label("Checkers which may be implemented in the future");
    section2.setWrapText(true);
    section2.setFont(Font.font(null, FontWeight.BLACK, 20));
    grid.add(section2, 1, 6);

    Label head2 = new Label(Checker2Head);
    head2.setWrapText(true);
    head2.setFont(Font.font(null, FontWeight.BOLD, 16));
    grid.add(head2, 1, 7);

    Label desc2 = new Label(Checker2Desc);
    desc2.setWrapText(true);
    grid.add(desc2, 1, 8);

    Label head3 = new Label(Checker3Head);
    head3.setWrapText(true);
    head3.setFont(Font.font(null, FontWeight.BOLD, 16));
    grid.add(head3, 1, 10);

    Label desc3 = new Label(Checker3Desc);
    desc3.setWrapText(true);
    grid.add(desc3, 1, 11);

    Label head4 = new Label(Checker4Head);
    head4.setWrapText(true);
    head4.setFont(Font.font(null, FontWeight.BOLD, 16));
    grid.add(head4, 1, 13);

    Label desc4 = new Label(Checker4Desc);
    desc4.setWrapText(true);
    grid.add(desc4, 1, 14);

    Label head5 = new Label(Checker5Head);
    head5.setWrapText(true);
    head5.setFont(Font.font(null, FontWeight.BOLD, 16));
    grid.add(head5, 1, 16);

    Label desc5 = new Label(Checker5Desc);
    desc5.setWrapText(true);
    grid.add(desc5, 1, 17);

    Label head6 = new Label(Checker6Head);
    head6.setWrapText(true);
    head6.setFont(Font.font(null, FontWeight.BOLD, 16));
    grid.add(head6, 1, 19);

    Label desc6 = new Label(Checker6Desc);
    desc6.setWrapText(true);
    grid.add(desc6, 1, 20);

    dialog.getDialogPane().setContent(grid);
    dialog.showAndWait();
    Main.getInstance().greyStack.toBack();
    Main.getInstance().greyStack.setVisible(false);


  }

}
