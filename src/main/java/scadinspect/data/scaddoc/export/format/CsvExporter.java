package scadinspect.data.scaddoc.export.format;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import scadinspect.data.scaddoc.Module;
import scadinspect.data.scaddoc.ScadDocuFile;
import scadinspect.data.scaddoc.properties.Property;

/**
 * @author richteto on 31.03.2017.
 */
public class CsvExporter implements Exporter {

  String lineSeparator = System.lineSeparator();

  /**
   * @param file a ScadDocuFile containing a list of modules that are supposed to be exported
   * @return returns the CSV document as String that can be written to a file or used further
   * internally
   */
  @Override
  public byte[] getOutput(ScadDocuFile file) {
    List<String> keys = new ArrayList<>(file.getAllKeys());
    Collection<Module> modules = file.getModules();

    StringBuilder csvExport = new StringBuilder();
    csvExport.append(file.getPath().toString());
    csvExport.append(lineSeparator);
    if (keys.size() == 0) {
      return csvExport.toString().getBytes();
    }
    // Headers
    for (String key : keys) {
      csvExport.append(key);
      csvExport.append(",");
    }
    csvExport.setLength(csvExport.length() - 1);
    csvExport.append(lineSeparator);

    for (Module module : modules) {
      Collection<Property> properties = module.getProperties();
      for (String key : keys) {
        for (Property property : properties) {
          if (property.getKey().equals(key)) {
            csvExport.append(
                property.getValue().toString().replaceAll("\\[|\\]|\\n|\\r", "")
                    .replaceAll(",", ":"));
          }
        }
        csvExport.append(",");
      }
      csvExport.setLength(csvExport.length() - 1);
      csvExport.append(lineSeparator);
    }
    return csvExport.toString().getBytes();
  }

  /**
   * @param files multiple ScadDocuFile containing a list of modules that are supposed to be
   * exported
   * @return returns the CSV document as String that can be written to a file or used further
   * internally
   */
  @Override
  public byte[] getOutput(Collection<ScadDocuFile> files) throws Exception {
    ByteArrayOutputStream csvExport = new ByteArrayOutputStream();
    for (ScadDocuFile docuFile : files) {
      csvExport.write(getOutput(docuFile));
      csvExport.write(lineSeparator.getBytes());
    }
    return csvExport.toByteArray();
  }
}
