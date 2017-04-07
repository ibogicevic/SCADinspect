package scadinspect.gui;

import java.util.Optional;
import java.util.logging.Level;
import java.util.prefs.Preferences;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import scadinspect.control.ProjectHandling;

public class SettingsDialog {
    
    public static void openDialog(){

        Main.getInstance().greyStack.toFront();
        Main.getInstance().greyPane.modalToFront(true);
        Main.getInstance().greyStack.setVisible(true);

        Preferences userPrefs = Preferences.userRoot().node("DHBW.SCADInspect.Settings");

        Dialog<Boolean> dialog = new Dialog<>();
        dialog.setTitle("Settings");
        dialog.setHeaderText(null);
           
        // Set the button types.
        ButtonType okButtonType = new ButtonType("OK", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);
            
        // Create the checkbox fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
            
        CheckBox autorefresh = new CheckBox("Autorefresh On/Off");
        grid.add(autorefresh, 0, 0);

        //Create ComboBox for Logging Level
        Text logtext = new Text("Logging Level:");

        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "None",
                        "Severe",
                        "Warning",
                        "Info"
                );
        final ComboBox loggingCombo = new ComboBox(options);

        grid.add(logtext, 0, 1);
        grid.add(loggingCombo, 1, 1);


        // Get previously saved settings, default to false
        // Autorefresh
        if (userPrefs.getBoolean("SET_AUTOREFRESH", false)) {
            autorefresh.setSelected(true);
        } else {
            autorefresh.setSelected(false);
        }
        // Logging Level
        loggingCombo.getSelectionModel().select(userPrefs.getInt("LOG_LEVEL", 0));


        // Load contents in dialog
        dialog.getDialogPane().setContent(grid);
        dialog.initModality(Modality.APPLICATION_MODAL);
            
        Optional<Boolean> result = dialog.showAndWait();

        if (result.isPresent()){
            // ... user clicks "ok", save settings

            // Autorefresh
            if (autorefresh.isSelected()) {
                userPrefs.putBoolean("SET_AUTOREFRESH", true);
            } else {
                userPrefs.putBoolean("SET_AUTOREFRESH", false);
            }

            // Logging
            userPrefs.putInt("LOG_LEVEL", loggingCombo.getSelectionModel().getSelectedIndex());

        }
        Main.getInstance().greyStack.toBack();
        Main.getInstance().greyStack.setVisible(false);
    }
}
