/**
 * Opens the about dialog if the About-Button is clicked
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
        dialog.setTitle(Messages.getString("AboutDialog.Title"));
        dialog.setHeaderText(Messages.getString("AboutDialog.HeaderText"));


        // Set the button types.
        ButtonType okButton = new ButtonType(Messages.getString("AboutDialog.okButton"), ButtonData.CANCEL_CLOSE);

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
        Label description = new Label(descriptionText);
        description.setWrapText(true);
        grid.add(description, 1, 0);

        Label thirdParty = new Label(thirdPartyText);
        thirdParty.setWrapText(true);
        grid.add(thirdParty, 1, 1);

        dialog.getDialogPane().setContent(grid);
        dialog.showAndWait();
        Main.getInstance().greyStack.toBack();
        Main.getInstance().greyStack.setVisible(false);


    }

    // Text content
    final static String descriptionText =
            Messages.getString("AboutDialog.Description");

    final static String thirdPartyText =
            Messages.getString("AboutDialog.ThirdParty");

    // Set logo
    private static final Image logo = new Image("AboutDialog.Logo");
}
