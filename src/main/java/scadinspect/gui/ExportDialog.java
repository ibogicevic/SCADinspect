package scadinspect.gui;

import java.io.File;
import java.util.Optional;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import scadinspect.data.scaddoc.error.FileExportException;
import scadinspect.data.scaddoc.export.FileExport;
import scadinspect.data.scaddoc.export.format.ExportFormat;

/**
 * author simon
 */
public class ExportDialog {

  /**
   * Opens the export dialog, select extension, show save File dialog, save file
   */
  public static void openDialog() {
    Dialog<ButtonType> dialog;
    dialog = new Dialog<>();
    dialog.setTitle("Export");
    dialog.setHeaderText(null);

    //add disabled OK and Cancel button
    dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
    dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);

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
    RadioButton pdf = new RadioButton("PDF");
    pdf.setToggleGroup(group);
    RadioButton md = new RadioButton("Markdown");
    md.setToggleGroup(group);

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

    vBox.getChildren().add(excel);
    vBox.getChildren().add(csv);
    vBox.getChildren().add(json);
    vBox.getChildren().add(xml);
    vBox.getChildren().add(pdf);
    vBox.getChildren().add(md);

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
        FileExport exporter = new FileExport();
        try {
          File exported;  //save exported file for later reference
          //switch for radio buttons
          if (excel.isSelected()) {
            exported = exporter.save(ExportFormat.EXCEL, Main.getInstance().getDocuFiles(),
                pathWithoutExtension + ".xsxl");
          } else if (csv.isSelected()) {
            exported = exporter.save(ExportFormat.CSV, Main.getInstance().getDocuFiles(),
                pathWithoutExtension + ".csv");
          } else if (json.isSelected()) {
            exported = exporter.save(ExportFormat.JSON, Main.getInstance().getDocuFiles(),
                pathWithoutExtension + ".json");
          } else if (xml.isSelected()) {
            exported = exporter.save(ExportFormat.XML, Main.getInstance().getDocuFiles(),
                pathWithoutExtension + ".xml");
          } else if (pdf.isSelected()) {
            exported = exporter.save(ExportFormat.PDF, Main.getInstance().getDocuFiles(),
                pathWithoutExtension + ".pdf");
          } else if (md.isSelected()) {
            exported = exporter.save(ExportFormat.MD, Main.getInstance().getDocuFiles(),
                pathWithoutExtension + ".md");
          } else {   //shouldn't happen, but default is pdf
            exported = exporter.save(ExportFormat.PDF, Main.getInstance().getDocuFiles(),
                pathWithoutExtension + ".pdf");
          }
          Main.getInstance().statusArea.setMessage("Saved as " + exported.getAbsolutePath());
        } catch (FileExportException e) {
          Main.getInstance().statusArea.setMessage("Error in Export: "+e.getCause());
          e.printStackTrace();
          //TODO use Logger
        }
      }
    }
  }
}
