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
package export;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Collection;
import data.ScadDocuFile;
import export.format.CsvExporter;
import export.format.Exporter;
import export.format.Exporter.ExportFormat;
import export.format.HtmlExporter;
import export.format.MdExporter;

/**
 * Provides all means to convert a List of Modules into multiple formats of character files.
 * This includes JSON and XML files.
 */

public class FileExporter {

  /**
   * Returns a file with all data given in the file in the assigned format
   *
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
      FileOutputStream fos = new FileOutputStream(path);

      Exporter exporter = getExporter(format);

      fos.write(exporter.getOutput(file));
      fos.close();

      return outputFile;
    } catch (Exception e) {
      throw new FileExportException(e);
    }
  }

  /**
   * Returns a file with all data given in the files in the assigned format
   *
   * @param format of type ExportFormat for desired output
   * @param files List of modules that are saved to a file
   * @param path Path to the location where the XML file should be saved
   * @throws FileExportException if some failure during the export. Further information can be in
   * the init cause.
   */
  public File save(ExportFormat format, Collection<ScadDocuFile> files, String path)
      throws FileExportException {
    try {
      File outputFile = new File(path);
      FileOutputStream fos = new FileOutputStream(path);

      Exporter exporter = getExporter(format);

      for(ScadDocuFile file : files) {
        fos.write(exporter.getOutput(file));
      }

      fos.close();

      return outputFile;

    } catch (Exception e) {
      throw new FileExportException(e);
    }
  }

  /**
   * selects the right exporter for a given format
   *
   * @param format value of the enumerator ExportFormat
   * @return an exporter of the given format or null if the format is invalid
   */
  private Exporter getExporter(Exporter.ExportFormat format) {
    Exporter exporter;

    switch (format) {
      case MD:
        exporter = new MdExporter();
        break;
      case CSV:
        exporter = new CsvExporter();
        break;
      case HTML:
        exporter = new HtmlExporter();
        break;
      default:
        exporter = null;
    }

    return exporter;
  }
}
