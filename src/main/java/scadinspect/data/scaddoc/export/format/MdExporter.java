package scadinspect.data.scaddoc.export.format;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;
import scadinspect.data.scaddoc.Module;
import scadinspect.data.scaddoc.ScadDocuFile;
import scadinspect.data.scaddoc.properties.Property;

/**
 * @author desyon on 31.03.17.
 */
public class MdExporter implements Exporter {

  @Override
  public String getOutput(ScadDocuFile file) throws Exception {
    String lineSeparator = System.lineSeparator();
    List<String> keys = new ArrayList<>(file.getAllKeys());
    Collection<Module> modules = file.getModules();

    StringBuilder sb = new StringBuilder();

    insertHeader(sb);

    sb.append("## ");
    sb.append(file.getPath().toString());
    sb.append(lineSeparator);

    //table  header
    StringJoiner sj = new StringJoiner("|");
    for (String key : keys) {
      sj.add(key);
    }

    sb.append(sj.toString());
    sb.append(lineSeparator);

    //separate header
    sj = new StringJoiner("|");
    String hSeparator;
    for (String key : keys) {
      hSeparator = "";
      for (int i = 0; i < key.length(); i++) {
        hSeparator += "-";
      }
      sj.add(hSeparator);
    }

    sb.append(sj.toString());
    sb.append(lineSeparator);

    //table content
    for (Module module : modules) {
      Collection<Property> properties = module.getProperties();
      sj = new StringJoiner("|");
      for (String key : keys) {
        for (Property property : properties) {
          if (property.getKey().equals(key)) {
            sj.add(property.getValue().toString().replaceAll("\\[|]|\\n|\\r", "")
                .replaceAll("\\|", ";"));
            break;
          }
        }
      }
      sb.append(sj.toString());
      sb.append(lineSeparator);
    }

    return sb.toString();
  }

  @Override
  public String getOutput(Collection<ScadDocuFile> files) throws Exception {
    return null;
  }

  private void insertHeader(StringBuilder sb) {
    sb.append("# Parts Documentation");
    sb.append(System.lineSeparator());
  }
}
