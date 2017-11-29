package gui.dialogs;

import java.util.Optional;
import java.util.logging.Level;
import java.util.prefs.Preferences;

import gui.Main;
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
    
    public static final String SETTING_STATIC_ANALYSIS = "SET_STATICANALYSIS";
    public static final String SETTING_LOG_LEVEL = "LOG_LEVEL";
    public static final String SETTING_DOCUMENTATION = "SET_DOCUMENTATION";
    public static final String SETTING_AUTOREFRESH = "SET_AUTOREFRESH";
    
    public static void open(){

        Preferences userPrefs = Preferences.userRoot().node("DHBW.SCADInspect.Settings");

        Dialog<ButtonType> dialog = new Dialog<>();
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
        grid.add(codeAnalysis, 0,1);

        CheckBox documentation = new CheckBox("Documentation");
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
        autorefresh.setSelected(userPrefs.getBoolean(SettingsDialog.SETTING_AUTOREFRESH, false));
        documentation.setSelected(userPrefs.getBoolean(SettingsDialog.SETTING_DOCUMENTATION, true));
        codeAnalysis.setSelected(userPrefs.getBoolean(SettingsDialog.SETTING_STATIC_ANALYSIS, true));
        loggingCombo.getSelectionModel().select(userPrefs.getInt(SettingsDialog.SETTING_LOG_LEVEL, 0));

        // Load contents in dialog
        dialog.getDialogPane().setContent(grid);
        dialog.initModality(Modality.APPLICATION_MODAL);
            
        Optional<ButtonType> result = dialog.showAndWait();
                
        // ... user clicks "ok", save settings
        
        if (result.orElse(ButtonType.CANCEL) == okButtonType) {
            boolean staticAnalysisOld = userPrefs.getBoolean(SettingsDialog.SETTING_STATIC_ANALYSIS, false);
            boolean documentationOld = userPrefs.getBoolean(SettingsDialog.SETTING_DOCUMENTATION, true);
            
            if(codeAnalysis.isSelected() != staticAnalysisOld) {
//                if(codeAnalysis.isSelected()) {
//                    Main.getInstance().tabArea.getTabs().add(0,Main.getInstance().tabArea.getIssues());
//                }
//                else {
//                    Main.getInstance().tabArea.getTabs().remove(Main.getInstance().tabArea.getIssues());
//                }
//                Main.getInstance().tabArea.getSelectionModel().select(0);
//                userPrefs.putBoolean(SettingsDialog.SETTING_STATIC_ANALYSIS, codeAnalysis.isSelected());
            }
            
            if(documentation.isSelected() != documentationOld) {
                if(documentation.isSelected()) {
                    Main.getInstance().tabArea.getTabs().add(Main.getInstance().tabArea.getDocumentation());
                }
                else {
                    Main.getInstance().tabArea.getTabs().remove(Main.getInstance().tabArea.getDocumentation());
                }
                userPrefs.putBoolean(SettingsDialog.SETTING_DOCUMENTATION, documentation.isSelected());
            }
            
            userPrefs.putBoolean(SettingsDialog.SETTING_STATIC_ANALYSIS, codeAnalysis.isSelected());

            // Logging
            int level =  loggingCombo.getSelectionModel().getSelectedIndex();
            userPrefs.putInt(SettingsDialog.SETTING_LOG_LEVEL, level);
            Level logLevel = Level.parse(Integer.toString(level));
            Main.logger.setLevel(logLevel);
        }
        Main.getInstance().greyStack.toBack();
        Main.getInstance().greyStack.setVisible(false);
    }
}
