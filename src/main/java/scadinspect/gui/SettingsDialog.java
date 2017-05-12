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
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType);
        //dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
        // Reenable Close Button, once the changes are ONLY applied when clicking OK
            
        // Create the checkbox fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
            
        CheckBox autorefresh = new CheckBox("Autorefresh On/Off");
        grid.add(autorefresh, 0, 0);

        CheckBox codeAnalysis = new CheckBox("Static Code Analysis");
        codeAnalysis.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                Main.getInstance().tabArea.getTabs().add(0,Main.getInstance().tabArea.getIssues());
                Main.getInstance().tabArea.getSelectionModel().select(0);
            } else {
                Main.getInstance().tabArea.getTabs().remove(Main.getInstance().tabArea.getIssues());
                Main.getInstance().tabArea.getSelectionModel().select(0);
            }
        });
        grid.add(codeAnalysis, 0,1);

        CheckBox documentation = new CheckBox("Documentation");
        documentation.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                if(codeAnalysis.isSelected())
                    Main.getInstance().tabArea.getTabs().add( 1, Main.getInstance().tabArea.getDocumentation());
                else
                    Main.getInstance().tabArea.getTabs().add( 0, Main.getInstance().tabArea.getDocumentation());
                Main.getInstance().tabArea.getSelectionModel().select(0);
            } else {
                Main.getInstance().tabArea.getTabs().remove(Main.getInstance().tabArea.getDocumentation());
                Main.getInstance().tabArea.getSelectionModel().select(0);
            }
        });
        grid.add(documentation,0,2);

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

        grid.add(logtext, 0, 3);
        grid.add(loggingCombo, 1, 3);


        // Get previously saved settings, default to false
        // Autorefresh
        if (userPrefs.getBoolean("SET_AUTOREFRESH", false)) {
            autorefresh.setSelected(true);
        } else {
            autorefresh.setSelected(false);
        }

        // Documentation
        if (userPrefs.getBoolean("SET_DOCUMENTATION", true)) {
            documentation.setSelected(true);
        } else {
            documentation.setSelected(false);
        }

        // Static Code Analysis
        if (userPrefs.getBoolean("SET_STATICANALYSIS", true)) {
            codeAnalysis.setSelected(true);
        } else {
            codeAnalysis.setSelected(false);
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

            // Documentation
            if (documentation.isSelected()) {
                userPrefs.putBoolean("SET_DOCUMENTATION", true);
            } else {
                userPrefs.putBoolean("SET_DOCUMENTATION", false);
            }

            // Static Code Analysis
            if (codeAnalysis.isSelected()) {
                userPrefs.putBoolean("SET_STATICANALYSIS", true);
            } else {
                userPrefs.putBoolean("SET_STATICANALYSIS", false);
            }

            // Logging
            int level =  loggingCombo.getSelectionModel().getSelectedIndex();
            userPrefs.putInt("LOG_LEVEL", level);
            Level logLevel = Level.parse(Integer.toString(level));
            Main.logger.setLevel(logLevel);
        }
        Main.getInstance().greyStack.toBack();
        Main.getInstance().greyStack.setVisible(false);
    }
}
