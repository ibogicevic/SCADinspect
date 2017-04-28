package scadinspect.gui;

import javafx.scene.control.ToggleGroup;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import java.util.Optional;

public class ExportDialog {

    public static void openDialog(){
        Dialog<Boolean> dialog = new Dialog<>();
            dialog.setTitle("Export");
            dialog.setHeaderText(null);

            // Set the button types.
            ButtonType o
                    kButtonType = new ButtonType("OK", ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

            // Create the radio buttons
            VBox vBox = new VBox();
            vBox.setSpacing(10);

            ToggleGroup group = new ToggleGroup();
            RadioButton excel = new RadioButton("Excel");
            excel.setToggleGroup(group);
            RadioButton csv = new RadioButton("CSV");
            csv.setToggleGroup(group);
            RadioButton json = new RadioButton("JSON");
            json.setToggleGroup(group);
            RadioButton xml = new RadioButton("XML");
            xml.setToggleGroup(group);

            vBox.getChildren().add(excel);
            vBox.getChildren().add(csv);
            vBox.getChildren().add(json);
            vBox.getChildren().add(xml);

            dialog.getDialogPane().setContent(vBox);

            dialog.initModality(Modality.APPLICATION_MODAL);

        Optional<Boolean> result = dialog.showAndWait();
    }

}
