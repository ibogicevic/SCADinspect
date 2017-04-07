
/**
 * Opens the Checkers Dialog
 *
 * @author: jonas_ber
 */
package scadinspect.gui;

        import javafx.scene.control.ButtonType;
        import javafx.scene.control.Dialog;
        import javafx.scene.control.Label;
        import javafx.scene.control.ButtonBar.ButtonData;
        import javafx.scene.layout.GridPane;


public class CheckersDialog {


    public static void openDialog() {
        Main.getInstance().greyPane.modalToFront(true);
        Main.getInstance().greyStack.toFront();
        Main.getInstance().greyStack.setVisible(true);

        Dialog<Boolean> dialog = new Dialog<>();
        dialog.setTitle("Checkers");
        dialog.setHeaderText("View checkers");


        // Set the button types.
        ButtonType okButton = new ButtonType("OK", ButtonData.CANCEL_CLOSE);

        // Display the buttons
        dialog.getDialogPane().getButtonTypes().addAll(okButton);

        // Arrange Content
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);



    }
}
