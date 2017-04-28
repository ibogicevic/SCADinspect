package scadinspect.data.scaddoc.export.format;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scadinspect.data.scaddoc.Module;
import scadinspect.data.scaddoc.ScadDocuFile;
import scadinspect.data.scaddoc.properties.MultiProperty;
import scadinspect.data.scaddoc.properties.PairProperty;
import scadinspect.data.scaddoc.properties.SingleProperty;

/**
 * @author Desyon on 21.04.2017.
 */
class HtmlExporterTest {

  private HtmlExporter exporter;
  private List<Module> modules;

  private String lineSeparator;

  @BeforeEach
  void instantiate() {
    exporter = new HtmlExporter();
    modules = new ArrayList<>();
    lineSeparator = System.lineSeparator();
  }

  @Test
  void testThrow() {
    modules = null;
    ScadDocuFile file = new ScadDocuFile(null, modules);
    assertThrows(Exception.class, ()
        -> exporter.getOutput(file));
  }

  /**
   * Tests the export of an empty list.
   *
   * @result Ensure the export is failsafe.
   */
  @Test
  void emptyList() throws Exception {
    ScadDocuFile file = new ScadDocuFile(Paths.get("testPath"), modules);

    assertEquals("<html xmlns=\"http://www.w3.org/1999/xhtml\">" + lineSeparator
            + "  <head>" + lineSeparator
            + "    <style>" + lineSeparator
            + "      table {\n"
            + "        font-family: \"Trebuchet MS\", Arial, Helvetica, sans-serif;" + lineSeparator
            + "        border-collapse: collapse;" + lineSeparator
            + "        margin-bottom: 15px;" + lineSeparator
            + "      }" + lineSeparator
            + lineSeparator
            + "      td, th {" + lineSeparator
            + "        border: 1px solid #ddd;" + lineSeparator
            + "        padding: 8px;" + lineSeparator
            + "      }" + lineSeparator
            + lineSeparator
            + "      tr:nth-child(even){background-color: #f2f2f2;}" + lineSeparator
            + "      tr:hover {background-color: #ddd;}" + lineSeparator
            + lineSeparator
            + "      th {" + lineSeparator
            + "        padding-top: 12px;" + lineSeparator
            + "        padding-bottom: 12px;" + lineSeparator
            + "        text-align: center;" + lineSeparator
            + "        background-color: #4CAF50;" + lineSeparator
            + "        color: white;" + lineSeparator
            + "      }"
            + lineSeparator
            + "    </style>" + lineSeparator
            + "    <title>Parts Documentation</title>" + lineSeparator
            + "  </head>" + lineSeparator
            + "  <body>" + lineSeparator
            + "    <table>" + lineSeparator
            + "      <thead>" + lineSeparator
            + "        <tr/>" + lineSeparator
            + "      </thead>" + lineSeparator
            + "      <tbody/>" + lineSeparator
            + "    </table>" + lineSeparator
            + "  </body>" + lineSeparator
            + "</html>" + lineSeparator,
        new String(exporter.getOutput(file), "UTF-8"));
  }

  /**
   * Test against a single property containing an integer value.
   */
  @Test
  void singleProperty() throws Exception {
    Module singleProperty = new Module();
    singleProperty.addProperty(new SingleProperty<>("key", 1));
    modules.add(singleProperty);
    ScadDocuFile file = new ScadDocuFile(Paths.get("testPath"), modules);

    assertEquals("<html xmlns=\"http://www.w3.org/1999/xhtml\">" + lineSeparator
            + "  <head>" + lineSeparator
            + "    <style>" + lineSeparator
            + "      table {\n"
            + "        font-family: \"Trebuchet MS\", Arial, Helvetica, sans-serif;" + lineSeparator
            + "        border-collapse: collapse;" + lineSeparator
            + "        margin-bottom: 15px;" + lineSeparator
            + "      }" + lineSeparator
            + lineSeparator
            + "      td, th {" + lineSeparator
            + "        border: 1px solid #ddd;" + lineSeparator
            + "        padding: 8px;" + lineSeparator
            + "      }" + lineSeparator
            + lineSeparator
            + "      tr:nth-child(even){background-color: #f2f2f2;}" + lineSeparator
            + "      tr:hover {background-color: #ddd;}" + lineSeparator
            + lineSeparator
            + "      th {" + lineSeparator
            + "        padding-top: 12px;" + lineSeparator
            + "        padding-bottom: 12px;" + lineSeparator
            + "        text-align: center;" + lineSeparator
            + "        background-color: #4CAF50;" + lineSeparator
            + "        color: white;" + lineSeparator
            + "      }"
            + lineSeparator
            + "    </style>" + lineSeparator
            + "    <title>Parts Documentation</title>" + lineSeparator
            + "  </head>" + lineSeparator
            + "  <body>" + lineSeparator
            + "    <table>" + lineSeparator
            + "      <thead>" + lineSeparator
            + "        <tr>" + lineSeparator
            + "          <th>key</th>" + lineSeparator
            + "        </tr>" + lineSeparator
            + "      </thead>" + lineSeparator
            + "      <tbody>" + lineSeparator
            + "        <tr>" + lineSeparator
            + "          <td>1</td>" + lineSeparator
            + "        </tr>" + lineSeparator
            + "      </tbody>" + lineSeparator
            + "    </table>" + lineSeparator
            + "  </body>" + lineSeparator
            + "</html>" + lineSeparator,
        new String(exporter.getOutput(file), "UTF-8"));
  }

  /**
   * Test against multiple properties containing integer values.
   */
  @Test
  void multiProperty() throws Exception {
    Module multiProperty = new Module();
    multiProperty.addProperty(new MultiProperty<>("key", 1, 2, 3));
    modules.add(multiProperty);
    ScadDocuFile file = new ScadDocuFile(Paths.get("testPath"), modules);

    assertEquals("<html xmlns=\"http://www.w3.org/1999/xhtml\">" + lineSeparator
            + "  <head>" + lineSeparator + "    <style>" + lineSeparator
            + "      table {\n"
            + "        font-family: \"Trebuchet MS\", Arial, Helvetica, sans-serif;" + lineSeparator
            + "        border-collapse: collapse;" + lineSeparator
            + "        margin-bottom: 15px;" + lineSeparator
            + "      }" + lineSeparator
            + lineSeparator
            + "      td, th {" + lineSeparator
            + "        border: 1px solid #ddd;" + lineSeparator
            + "        padding: 8px;" + lineSeparator
            + "      }" + lineSeparator
            + lineSeparator
            + "      tr:nth-child(even){background-color: #f2f2f2;}" + lineSeparator
            + "      tr:hover {background-color: #ddd;}" + lineSeparator
            + lineSeparator
            + "      th {" + lineSeparator
            + "        padding-top: 12px;" + lineSeparator
            + "        padding-bottom: 12px;" + lineSeparator
            + "        text-align: center;" + lineSeparator
            + "        background-color: #4CAF50;" + lineSeparator
            + "        color: white;" + lineSeparator
            + "      }"
            + lineSeparator
            + "    </style>" + lineSeparator
            + "    <title>Parts Documentation</title>" + lineSeparator
            + "  </head>" + lineSeparator
            + "  <body>" + lineSeparator
            + "    <table>" + lineSeparator
            + "      <thead>" + lineSeparator
            + "        <tr>" + lineSeparator
            + "          <th>key</th>" + lineSeparator
            + "        </tr>" + lineSeparator
            + "      </thead>" + lineSeparator
            + "      <tbody>" + lineSeparator
            + "        <tr>" + lineSeparator
            + "          <td>1, 2, 3</td>" + lineSeparator
            + "        </tr>" + lineSeparator
            + "      </tbody>" + lineSeparator
            + "    </table>" + lineSeparator
            + "  </body>" + lineSeparator
            + "</html>" + lineSeparator,
        new String(exporter.getOutput(file), "UTF-8"));
  }

  /**
   * Test against a pair property.
   */
  @Test
  void pairProperty() throws Exception {
    Module pairProperty = new Module();
    pairProperty.addProperty(new PairProperty<>("price", 12, "EUR"));
    modules.add(pairProperty);
    ScadDocuFile file = new ScadDocuFile(Paths.get("testPath"), modules);

    assertEquals("<html xmlns=\"http://www.w3.org/1999/xhtml\">" + lineSeparator
            + "  <head>" + lineSeparator + "    <style>" + lineSeparator
            + "      table {\n"
            + "        font-family: \"Trebuchet MS\", Arial, Helvetica, sans-serif;" + lineSeparator
            + "        border-collapse: collapse;" + lineSeparator
            + "        margin-bottom: 15px;" + lineSeparator
            + "      }" + lineSeparator
            + lineSeparator
            + "      td, th {" + lineSeparator
            + "        border: 1px solid #ddd;" + lineSeparator
            + "        padding: 8px;" + lineSeparator
            + "      }" + lineSeparator
            + lineSeparator
            + "      tr:nth-child(even){background-color: #f2f2f2;}" + lineSeparator
            + "      tr:hover {background-color: #ddd;}" + lineSeparator
            + lineSeparator
            + "      th {" + lineSeparator
            + "        padding-top: 12px;" + lineSeparator
            + "        padding-bottom: 12px;" + lineSeparator
            + "        text-align: center;" + lineSeparator
            + "        background-color: #4CAF50;" + lineSeparator
            + "        color: white;" + lineSeparator
            + "      }"
            + lineSeparator
            + "    </style>" + lineSeparator
            + "    <title>Parts Documentation</title>" + lineSeparator
            + "  </head>" + lineSeparator
            + "  <body>" + lineSeparator
            + "    <table>" + lineSeparator
            + "      <thead>" + lineSeparator
            + "        <tr>" + lineSeparator
            + "          <th>price</th>" + lineSeparator
            + "        </tr>" + lineSeparator
            + "      </thead>" + lineSeparator
            + "      <tbody>" + lineSeparator
            + "        <tr>" + lineSeparator
            + "          <td>12 EUR</td>" + lineSeparator
            + "        </tr>" + lineSeparator
            + "      </tbody>" + lineSeparator
            + "    </table>" + lineSeparator
            + "  </body>" + lineSeparator
            + "</html>" + lineSeparator,
        new String(exporter.getOutput(file), "UTF-8"));
  }

  /**
   * Test against a module with multiple properties.
   */
  @Test
  void multipleProperties() throws Exception {
    Module mod = new Module();

    mod.addProperty(new SingleProperty<>("key1", "value1"));
    mod.addProperty(new SingleProperty<>("key2", "value2"));

    modules.add(mod);

    ScadDocuFile file = new ScadDocuFile(Paths.get("testPath"), modules);

    assertEquals("<html xmlns=\"http://www.w3.org/1999/xhtml\">" + lineSeparator
            + "  <head>" + lineSeparator + "    <style>" + lineSeparator
            + "      table {\n"
            + "        font-family: \"Trebuchet MS\", Arial, Helvetica, sans-serif;" + lineSeparator
            + "        border-collapse: collapse;" + lineSeparator
            + "        margin-bottom: 15px;" + lineSeparator
            + "      }" + lineSeparator
            + lineSeparator
            + "      td, th {" + lineSeparator
            + "        border: 1px solid #ddd;" + lineSeparator
            + "        padding: 8px;" + lineSeparator
            + "      }" + lineSeparator
            + lineSeparator
            + "      tr:nth-child(even){background-color: #f2f2f2;}" + lineSeparator
            + "      tr:hover {background-color: #ddd;}" + lineSeparator
            + lineSeparator
            + "      th {" + lineSeparator
            + "        padding-top: 12px;" + lineSeparator
            + "        padding-bottom: 12px;" + lineSeparator
            + "        text-align: center;" + lineSeparator
            + "        background-color: #4CAF50;" + lineSeparator
            + "        color: white;" + lineSeparator
            + "      }"
            + lineSeparator
            + "    </style>" + lineSeparator
            + "    <title>Parts Documentation</title>" + lineSeparator
            + "  </head>" + lineSeparator
            + "  <body>" + lineSeparator
            + "    <table>" + lineSeparator
            + "      <thead>" + lineSeparator
            + "        <tr>" + lineSeparator
            + "          <th>key1</th>" + lineSeparator
            + "          <th>key2</th>" + lineSeparator
            + "        </tr>" + lineSeparator
            + "      </thead>" + lineSeparator
            + "      <tbody>" + lineSeparator
            + "        <tr>" + lineSeparator
            + "          <td>value1</td>" + lineSeparator
            + "          <td>value2</td>" + lineSeparator
            + "        </tr>" + lineSeparator
            + "      </tbody>" + lineSeparator
            + "    </table>" + lineSeparator
            + "  </body>" + lineSeparator
            + "</html>" + lineSeparator,
        new String(exporter.getOutput(file), "UTF-8"));
  }

  /**
   * Test against a file with multiple modules
   */
  @Test
  void multipleModules() throws Exception {
    Module mod1 = new Module();
    Module mod2 = new Module();

    mod1.addProperty(new SingleProperty<>("key1", "value1"));
    mod2.addProperty(new SingleProperty<>("key2", "value2"));

    modules.add(mod1);
    modules.add(mod2);

    ScadDocuFile file = new ScadDocuFile(Paths.get("testPath"), modules);

    assertEquals("<html xmlns=\"http://www.w3.org/1999/xhtml\">" + lineSeparator
            + "  <head>" + lineSeparator + "    <style>" + lineSeparator
            + "      table {\n"
            + "        font-family: \"Trebuchet MS\", Arial, Helvetica, sans-serif;" + lineSeparator
            + "        border-collapse: collapse;" + lineSeparator
            + "        margin-bottom: 15px;" + lineSeparator
            + "      }" + lineSeparator
            + lineSeparator
            + "      td, th {" + lineSeparator
            + "        border: 1px solid #ddd;" + lineSeparator
            + "        padding: 8px;" + lineSeparator
            + "      }" + lineSeparator
            + lineSeparator
            + "      tr:nth-child(even){background-color: #f2f2f2;}" + lineSeparator
            + "      tr:hover {background-color: #ddd;}" + lineSeparator
            + lineSeparator
            + "      th {" + lineSeparator
            + "        padding-top: 12px;" + lineSeparator
            + "        padding-bottom: 12px;" + lineSeparator
            + "        text-align: center;" + lineSeparator
            + "        background-color: #4CAF50;" + lineSeparator
            + "        color: white;" + lineSeparator
            + "      }"
            + lineSeparator
            + "    </style>" + lineSeparator
            + "    <title>Parts Documentation</title>" + lineSeparator
            + "  </head>" + lineSeparator
            + "  <body>" + lineSeparator
            + "    <table>" + lineSeparator
            + "      <thead>" + lineSeparator
            + "        <tr>" + lineSeparator
            + "          <th>key1</th>" + lineSeparator
            + "          <th>key2</th>" + lineSeparator
            + "        </tr>" + lineSeparator
            + "      </thead>" + lineSeparator
            + "      <tbody>" + lineSeparator
            + "        <tr>" + lineSeparator
            + "          <td>value1</td>" + lineSeparator
            + "          <td/>" + lineSeparator
            + "        </tr>" + lineSeparator
            + "        <tr>" + lineSeparator
            + "          <td/>" + lineSeparator
            + "          <td>value2</td>" + lineSeparator
            + "        </tr>" + lineSeparator
            + "      </tbody>" + lineSeparator
            + "    </table>" + lineSeparator
            + "  </body>" + lineSeparator
            + "</html>" + lineSeparator,
        new String(exporter.getOutput(file), "UTF-8"));
  }

  /**
   * Test against a collection of multiple files
   */
  @Test
  void multipleFiles() throws Exception {
    Module mod1 = new Module();
    Module mod2 = new Module();

    mod1.addProperty(new SingleProperty<>("key", "value"));
    mod2.addProperty(new SingleProperty<>("key", "value"));

    modules.add(mod1);
    modules.add(mod2);

    ScadDocuFile file1 = new ScadDocuFile(Paths.get("testPath1"), modules);
    ScadDocuFile file2 = new ScadDocuFile(Paths.get("testPath2"), modules);

    List<ScadDocuFile> files = new ArrayList<>();
    files.add(file1);
    files.add(file2);

    assertEquals("<html xmlns=\"http://www.w3.org/1999/xhtml\">" + lineSeparator
            + "  <head>" + lineSeparator + "    <style>" + lineSeparator
            + "      table {\n"
            + "        font-family: \"Trebuchet MS\", Arial, Helvetica, sans-serif;" + lineSeparator
            + "        border-collapse: collapse;" + lineSeparator
            + "        margin-bottom: 15px;" + lineSeparator
            + "      }" + lineSeparator
            + lineSeparator
            + "      td, th {" + lineSeparator
            + "        border: 1px solid #ddd;" + lineSeparator
            + "        padding: 8px;" + lineSeparator
            + "      }" + lineSeparator
            + lineSeparator
            + "      tr:nth-child(even){background-color: #f2f2f2;}" + lineSeparator
            + "      tr:hover {background-color: #ddd;}" + lineSeparator
            + lineSeparator
            + "      th {" + lineSeparator
            + "        padding-top: 12px;" + lineSeparator
            + "        padding-bottom: 12px;" + lineSeparator
            + "        text-align: center;" + lineSeparator
            + "        background-color: #4CAF50;" + lineSeparator
            + "        color: white;" + lineSeparator
            + "      }"
            + lineSeparator
            + "    </style>" + lineSeparator
            + "    <title>Parts Documentation</title>" + lineSeparator
            + "  </head>" + lineSeparator
            + "  <body>" + lineSeparator
            + "    <table>" + lineSeparator
            + "      <thead>" + lineSeparator
            + "        <tr>" + lineSeparator
            + "          <th>key</th>" + lineSeparator
            + "        </tr>" + lineSeparator
            + "      </thead>" + lineSeparator
            + "      <tbody>" + lineSeparator
            + "        <tr>" + lineSeparator
            + "          <td>value</td>" + lineSeparator
            + "        </tr>" + lineSeparator
            + "        <tr>" + lineSeparator
            + "          <td>value</td>" + lineSeparator
            + "        </tr>" + lineSeparator
            + "      </tbody>" + lineSeparator
            + "    </table>" + lineSeparator
            + "    <table>" + lineSeparator
            + "      <thead>" + lineSeparator
            + "        <tr>" + lineSeparator
            + "          <th>key</th>" + lineSeparator
            + "        </tr>" + lineSeparator
            + "      </thead>" + lineSeparator
            + "      <tbody>" + lineSeparator
            + "        <tr>" + lineSeparator
            + "          <td>value</td>" + lineSeparator
            + "        </tr>" + lineSeparator
            + "        <tr>" + lineSeparator
            + "          <td>value</td>" + lineSeparator
            + "        </tr>" + lineSeparator
            + "      </tbody>" + lineSeparator
            + "    </table>" + lineSeparator
            + "  </body>" + lineSeparator
            + "</html>" + lineSeparator,
        new String(exporter.getOutput(files), "UTF-8"));
  }
}