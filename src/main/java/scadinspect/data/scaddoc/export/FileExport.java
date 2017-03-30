package scadinspect.data.scaddoc.export;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import scadinspect.control.MyLogger;
import scadinspect.data.scaddoc.Module;
import scadinspect.data.scaddoc.error.FileExportException;
import scadinspect.data.scaddoc.export.format.JsonExport;
import scadinspect.data.scaddoc.export.format.XmlExport;

/**
 * Provides all means to convert a List of Modules into multiple formats of character files.
 * This includes JSON and XML files.
 *
 * @author eric, desyon on 3/23/17.
 */

public class FileExport {

  private static MyLogger logger;
  /**
   * @exception FileExportException if some failure during the export. Further information can be in
   * the init cause.
   */
  private FileWriter fileWriter;

  /**
   * @param modules List of modules that are saved to a file
   * @param path Path to the location where the XML file should be saved
   */
  public File saveAsJson(List<Module> modules, String path) throws FileExportException {
    JsonExport jsonEx = new JsonExport();

    try {
      File file = new File(path);
      fileWriter = new FileWriter(file);

      fileWriter.write(jsonEx.getJson(modules));
      fileWriter.close();

      return file;
    } catch (Exception e) {
      //TODO: Log Exception
      FileExportException exportException = new FileExportException(e);
      throw exportException;
    }
  }

  /**
   * @param modules List of modules that are saved to a file
   * @param path Path to the location where the XML file should be saved
   */
  public File saveAsXml(List<Module> modules, String path) throws FileExportException {
    XmlExport xmlEx = new XmlExport();

    try {
      File file = new File(path);
      fileWriter = new FileWriter(file);

      fileWriter.write(xmlEx.getXml(modules));
      fileWriter.close();

      return file;
    } catch (Exception e) {
      //TODO: Log Exception
      FileExportException exportException = new FileExportException(e);
      throw exportException;
    }
  }
}
