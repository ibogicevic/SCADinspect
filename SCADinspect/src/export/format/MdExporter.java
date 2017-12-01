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
package export.format;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;
import data.Module;
import data.ScadDocuFile;
import data.properties.Property;

public class MdExporter implements Exporter {

  /**
   * Creates and returns a XML String from the given ScadDocuFile analog to the specified
   * example
   *
   * @param file ScadDocuFile that the Export shall be exported for
   * @return String object, which represents the created XML
   */
  @Override
  public byte[] getOutput(ScadDocuFile file) {
    StringBuilder sb = new StringBuilder();

    sb.append("# Parts Documentation");
    sb.append(System.lineSeparator());

    sb.append(System.lineSeparator());
    sb.append(insertFile(file));

    return sb.toString().getBytes();
  }

  /**
   * Creates and returns a XML String from the given ScadDocuFiles according to the specified
   * example
   *
   * @param files Collection of ScadDocuFiles that the exporter shall convert
   * @return String object, which represents the created XML
   */
  @Override
  public byte[] getOutput(Collection<ScadDocuFile> files) {
    StringBuilder sb = new StringBuilder();

    sb.append("# Parts Documentation");
    sb.append(System.lineSeparator());

    for (ScadDocuFile file : files) {
      sb.append(System.lineSeparator());
      sb.append(insertFile(file));
    }

    return sb.toString().getBytes();
  }

  /**
   * Creates a Markdown table containing all modules and properties of the given file
   *
   * @param file file to create the table for
   * @return String object containing Markdown code for the table
   */
  private String insertFile(ScadDocuFile file) {
    String lineSeparator = System.lineSeparator();
    List<String> keys = new ArrayList<>(file.getAllKeys());
    Collection<Module> modules = file.getModules();

    StringBuilder sb = new StringBuilder();

    sb.append("## ");
    sb.append(file.getPath().toString());
    sb.append(lineSeparator);

    if (0 == keys.size()) {
      return sb.toString();
    }

    //table  header
    StringJoiner sj = new StringJoiner("|");
    for (String key : keys) {
      sj.add(key);
    }

    sb.append(sj.toString());
    sb.append(lineSeparator);

    //separate header
    sj = new StringJoiner("|");
    StringBuilder hSeparator;
    for (String key : keys) {
      hSeparator = new StringBuilder();
      for (int i = 0; i < key.length(); i++) {
        hSeparator.append("-");
      }
      sj.add(hSeparator.toString());
    }

    sb.append(sj.toString());
    sb.append(lineSeparator);

    //table content
    boolean foundKey;
    for (Module module : modules) {
      Collection<Property> properties = module.getProperties();
      sj = new StringJoiner("|");
      for (String key : keys) {
        foundKey = false;
        for (Property property : properties) {
          if (property.getKey().equals(key)) {
            sj.add(property.getValue().toString().replaceAll("\\[|]|\\n|\\r", "")
                .replaceAll("\\|", ";"));
            foundKey = true;
            break;
          }
        }
        if (!foundKey) {
          sj.add("");
        }
      }
      sb.append(sj.toString());
      sb.append(lineSeparator);
    }

    return sb.toString();
  }
}

