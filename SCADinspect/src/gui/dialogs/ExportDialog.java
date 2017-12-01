/*******************************************************************************
 * SCADinspect – https://github.com/ibogicevic/SCADinspect
 * Copyright (C) 2017 Ivan Bogicevic and others
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package gui.dialogs;

import java.io.File;
import java.util.Optional;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import export.FileExportException;
import export.FileExporter;
import export.format.Exporter.ExportFormat;
import gui.Main;

public class ExportDialog {

  /**
   * Opens the export dialog, select extension, show save File dialog, save file
   */
  public static void openDialog() {
    Dialog<ButtonType> dialog = new Dialog<>();
    dialog.setTitle("Export");
    dialog.setHeaderText(null);

    // Set the button types.
    ButtonType okButtonType = new ButtonType("OK");
    dialog.getDialogPane().getButtonTypes().addAll(okButtonType);
    //dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
    // TODO: Reenable Cancel button, once cancelling without export is possible

    // Create the radio buttons
    VBox vBox = new VBox();
    vBox.setSpacing(10);

    ToggleGroup group = new ToggleGroup();

    RadioButton csvButton = new RadioButton("CSV");
    csvButton.setToggleGroup(group);
    RadioButton htmlButton = new RadioButton("HTML");
    htmlButton.setToggleGroup(group);
    RadioButton mdButton = new RadioButton("Markdown");
    mdButton.setToggleGroup(group);

    //listen for changes in selection of radioButtons
    group.selectedToggleProperty().addListener((ov, oldToggle, newToggle) -> {
      //enable Ok button if something was selected
      if (group.getSelectedToggle() != null) {
        dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
      }
      //shouldn't be necessary, for safety implemented if selection gets cleared
      else {
        dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
      }
    });

    vBox.getChildren().add(csvButton);
    vBox.getChildren().add(htmlButton);
    vBox.getChildren().add(mdButton);

    //display export Dialog
    dialog.getDialogPane().setContent(vBox);
    dialog.initModality(Modality.APPLICATION_MODAL);
    Optional<ButtonType> result = dialog.showAndWait();

    //if dialog is closed by Ok button (something has to be selected)
    if (result.get() == ButtonType.OK) {

      //display file chooser
      FileChooser fileChooser = new FileChooser();
      File exportFile = fileChooser.showSaveDialog(Main.getInstance().getPrimaryStage());

      //check if fileChooser got a result
      if (exportFile != null) {
        String pathWithoutExtension = exportFile.getAbsolutePath();
        FileExporter exporter = new FileExporter();
        try {
          File exported = null;  //save exported file for later reference
          //switch for radio buttons
          if (csvButton.isSelected()) {
            exported = exporter.save(ExportFormat.CSV, Main.getInstance().getDocuFiles(),
                pathWithoutExtension + ".csv");
          } else if (htmlButton.isSelected()) {
            exported = exporter.save(ExportFormat.HTML, Main.getInstance().getDocuFiles(),
                pathWithoutExtension + ".html");
          } else if (mdButton.isSelected()) {
            exported = exporter.save(ExportFormat.MD, Main.getInstance().getDocuFiles(),
                pathWithoutExtension + ".md");
          }
          String exportedFilePath = "";
          if (exported != null) {
        	  exportedFilePath = exported.getAbsolutePath();
          }
          Main.getInstance().statusArea.setMessage("Saved as " + exportedFilePath);
        } catch (FileExportException e) {
          Main.getInstance().statusArea.setMessage("Error in Export: "+e.getCause());
          e.printStackTrace();
          //TODO use Logger
        }
      }
    }
  }
}
