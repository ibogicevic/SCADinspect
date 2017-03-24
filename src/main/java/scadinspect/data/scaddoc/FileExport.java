package scadinspect.data.scaddoc;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import org.json.JSONObject;
import org.json.XML;

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

  private JsonExport jsonex;
  private FileWriter fileWriter;

  public FileExport() {
    jsonex = new JsonExport();
  }

  /**
   * @param modules List of modules that are saved to a file
   * @param path Path to the location where the XML file should be saved
   */
  public File saveAsJson(List<Module> modules, String path) throws IOException {
    File file = new File(path);
    fileWriter = new FileWriter(file);
    fileWriter.write(jsonex.getJson(modules));
    fileWriter.close();

    return file;
  }

  /**
   * @param modules List of modules that are saved to a file
   * @param path Path to the location where the XML file should be saved
   */
  public File saveAsXml(List<Module> modules, String path) throws IOException {
    Collection<JSONObject> json = jsonex.getJsonList(modules);

    StringBuilder sb = new StringBuilder();

    sb.append("<modules>");
    for (JSONObject jso : json) {
      sb.append(XML.toString(jso, "module"));
    }
    sb.append("</modules>");

    String xmlString = sb.toString();

    File file = new File(path);
    fileWriter = new FileWriter(file);
    fileWriter.write(xmlString);
    fileWriter.close();

    return file;
  }
}
