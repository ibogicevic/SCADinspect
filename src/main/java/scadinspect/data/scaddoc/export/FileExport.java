package scadinspect.data.scaddoc.export;

import java.io.File;
import java.io.FileWriter;
import java.util.Collection;
import scadinspect.data.scaddoc.ScadDocuFile;
import scadinspect.data.scaddoc.error.FileExportException;
import scadinspect.data.scaddoc.export.format.CsvExporter;
import scadinspect.data.scaddoc.export.format.ExportFormat;
import scadinspect.data.scaddoc.export.format.Exporter;
import scadinspect.data.scaddoc.export.format.JsonExporter;
import scadinspect.data.scaddoc.export.format.MdExporter;
import scadinspect.data.scaddoc.export.format.XmlExporter;

/**
 * Provides all means to convert a List of Modules into multiple formats of character files.
 * This includes JSON and XML files.
 *
 * @author richteto, desyon, eric on 3/23/17.
 */

public class FileExport {

  /**
   * @param format of type ExportFormat for desired output
   * @param file List of modules that are saved to a file
   * @param path Path to the location where the XML file should be saved
   * @throws FileExportException if some failure during the export. Further information can be in
   * the init cause.
   */
  public File save(ExportFormat format, ScadDocuFile file, String path)
      throws FileExportException {
    try {
      File outputFile = new File(path);
      FileWriter fw = new FileWriter(outputFile);

      Exporter exporter = getExporter(format);

      fw.write(exporter.getOutput(file));
      fw.close();

      return outputFile;
    } catch (Exception e) {
      throw new FileExportException(e);
    }
  }

  public File save(ExportFormat format, Collection<ScadDocuFile> files, String path)
      throws FileExportException {
    try {
      File outputFile = new File(path);
      FileWriter fw = new FileWriter(outputFile);

      Exporter exporter = getExporter(format);

      for(ScadDocuFile file : files) {
        fw.write(exporter.getOutput(file));
        fw.close();
      }

      return outputFile;

    } catch (Exception e) {
      throw new FileExportException(e);
    }
  }

  private Exporter getExporter(ExportFormat format) {
    Exporter exporter;

    switch (format) {
      case XML:
        exporter = new XmlExporter();
        break;
      case JSON:
        exporter = new JsonExporter();
        break;
      case MD:
        exporter = new MdExporter();
        break;
      case CSV:
        exporter = new CsvExporter();
        break;
      case HTML:
        exporter = null;
        break;
      default:
        exporter = null;
    }

    return exporter;
  }
}
