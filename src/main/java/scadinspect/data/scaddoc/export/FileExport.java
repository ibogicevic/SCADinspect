package scadinspect.data.scaddoc.export;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import scadinspect.data.scaddoc.Module;
import scadinspect.data.scaddoc.error.FileExportException;
import scadinspect.data.scaddoc.export.format.ExportFormat;
import scadinspect.data.scaddoc.export.format.Exporter;
import scadinspect.data.scaddoc.export.format.JsonExporter;
import scadinspect.data.scaddoc.export.format.XmlExporter;

/**
 * Provides all means to convert a List of Modules into multiple formats of character files.
 * This includes JSON and XML files.
 *
 * @author eric, desyon on 3/23/17.
 */

public class FileExport {

  /**
   * @param format of type ExportFormat for desired output
   * @param modules List of modules that are saved to a file
   * @param path Path to the location where the XML file should be saved
   * @throws FileExportException if some failure during the export. Further information can be in
   * the init cause.
   */
  public File save(ExportFormat format, List<Module> modules, String path)
      throws FileExportException {
    try {
      File file = new File(path);
      FileWriter fw = new FileWriter(file);

      Exporter exporter = null;

      switch (format) {
        case XML:
          exporter = new XmlExporter();
          break;
        case JSON:
          exporter = new JsonExporter();
          break;
        case MD:
          break;
        case CSV:
          break;
        case HTML:
          break;
      }

      fw.write(exporter.getOutput(modules));
      fw.close();

      return file;
    } catch (IOException | NullPointerException e) {
      throw new FileExportException(e);
    }
  }
}
