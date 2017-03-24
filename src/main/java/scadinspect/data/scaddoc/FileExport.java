package scadinspect.data.scaddoc;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @author eric, desyon on 3/23/17.
 */

//TODO: Write proper XML export implementation
public class FileExport {

  /**
   * @exception IOException if stream to aFile cannot be written to or closed.
   * @exception IllegalArgumentException if aFile is a directory.
   * @exception IllegalArgumentException if aFile cannot be written.
   */
  private FileWriter fileWriter;

  /**
   * @param modules List of modules that are saved to a file
   * @param path Path to the location where the XML file should be saved
   */
  public File saveAsJson(List<Module> modules, String path) throws Exception {
    JsonExport jsonEx = new JsonExport();

    try {
      File file = new File(path);
      fileWriter = new FileWriter(file);

      fileWriter.write(jsonEx.getJson(modules));
      fileWriter.close();

      return file;
    } catch (Exception e){
      //TODO: log exception
      System.out.println(e);
      throw e;
    }
  }

  /**
   * @param modules List of modules that are saved to a file
   * @param path Path to the location where the XML file should be saved
   */
  public File saveAsXml(List<Module> modules, String path) throws Exception {
    XmlExport xmlEx = new XmlExport();

    try {
      File file = new File(path);
      fileWriter = new FileWriter(file);

      fileWriter.write(xmlEx.getXml(modules));
      fileWriter.close();

      return file;
    } catch (Exception e) {
      //TODO: log exception
      System.out.println(e);
      throw e;
    }
  }
}
