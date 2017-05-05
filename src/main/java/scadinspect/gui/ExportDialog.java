package scadinspect.gui;

import java.io.File;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import java.util.Optional;
import scadinspect.data.scaddoc.error.FileExportException;
import scadinspect.data.scaddoc.export.FileExport;
import scadinspect.data.scaddoc.export.format.ExportFormat;

public class ExportDialog {

  public static void openDialog() {
    Dialog<Boolean> dialog = new Dialog<>();
    dialog.setTitle("Export");
    dialog.setHeaderText(null);

    // Set the button types.
    ButtonType okButtonType = new ButtonType("OK", ButtonData.OK_DONE);
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
    RadioButton pdf = new RadioButton("PDF");
    pdf.setToggleGroup(group);
    RadioButton md = new RadioButton("Markdown");
    md.setToggleGroup(group);

    vBox.getChildren().add(excel);
    vBox.getChildren().add(csv);
    vBox.getChildren().add(json);
    vBox.getChildren().add(xml);
    vBox.getChildren().add(pdf);
    vBox.getChildren().add(md);

    dialog.getDialogPane().setContent(vBox);

    dialog.initModality(Modality.APPLICATION_MODAL);

    Optional<Boolean> result = dialog.showAndWait();

    FileChooser fileChooser = new FileChooser();
    File exportFile = fileChooser.showSaveDialog(Main.getInstance().getPrimaryStage());

    if (exportFile != null) {

      String pathWithoutExtension = exportFile.getAbsolutePath();
      FileExport exporter = new FileExport();
      try {
        if (excel.isSelected()) {
          exporter.save(ExportFormat.EXCEL, Main.getInstance().getDocuFiles(),
              pathWithoutExtension + ".xsxl");
        }
        if (csv.isSelected()) {
          exporter.save(ExportFormat.CSV, Main.getInstance().getDocuFiles(),
              pathWithoutExtension + ".csv");
        }
        if (json.isSelected()) {
          exporter.save(ExportFormat.JSON, Main.getInstance().getDocuFiles(),
              pathWithoutExtension + ".json");
        }
        if (xml.isSelected()) {
          System.out.println(Main.getInstance().getDocuFiles());
          exporter.save(ExportFormat.XML, Main.getInstance().getDocuFiles(),
              pathWithoutExtension + ".xml");
        }
        if (pdf.isSelected()) {
          System.out.println(Main.getInstance().getDocuFiles());
          exporter.save(ExportFormat.PDF, Main.getInstance().getDocuFiles(),
              pathWithoutExtension + ".pdf");
        }
        if (md.isSelected()) {
          System.out.println(Main.getInstance().getDocuFiles());
          exporter.save(ExportFormat.MD, Main.getInstance().getDocuFiles(),
              pathWithoutExtension + ".md");
        }
      } catch (FileExportException e) {
        e.printStackTrace();
        System.out.println(e.getCause());

      }
    }

  }

}
