package scadinspect.gui;

import java.util.Optional;
import java.util.prefs.Preferences;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.layout.GridPane;

public class SettingsDialog {
    
    public static void openDialog(){

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

            //Get previously saved settings, default to false
            if (userPrefs.getBoolean("SET_AUTOREFRESH", false)) {
                autorefresh.setSelected(true);
            } else {
                autorefresh.setSelected(false);
            }
            
            grid.add(autorefresh, 0, 0);
                     
            dialog.getDialogPane().setContent(grid);
                    
            Optional<Boolean> result = dialog.showAndWait();

        if (result.isPresent()){
            // ... user clicks "ok", save settings
            if (autorefresh.isSelected()) {
                userPrefs.putBoolean("SET_AUTOREFRESH", true);
            } else {
                userPrefs.putBoolean("SET_AUTOREFRESH", false);
            }
        }
    }
    
}