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

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import data.Module;
import data.ScadDocuFile;
import data.properties.Property;

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
