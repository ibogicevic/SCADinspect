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
 * @author Desyon on 07.04.2017.
 */
class MdExporterTest {

  private MdExporter exporter;
  private List<Module> modules;

  private String lineSeparator;

  @BeforeEach
  void instantiate() {
    exporter = new MdExporter();
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
    assertEquals("# Parts Documentation" + lineSeparator
            + lineSeparator
            + "## testPath" + lineSeparator,
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
    assertEquals("# Parts Documentation" + lineSeparator
            + lineSeparator
            + "## testPath" + lineSeparator
            + "key" + lineSeparator
            + "---" + lineSeparator
            + "1" + lineSeparator,
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
    assertEquals("# Parts Documentation" + lineSeparator
            + "" + lineSeparator
            + "## testPath" + lineSeparator
            + "key" + lineSeparator
            + "---" + lineSeparator
            + "1, 2, 3" + lineSeparator,
        new String(exporter.getOutput(file), "UTF-8"));
  }

  /**
   * Test against a pair property.
   */
  @Test
  void pairPropertyInt() throws Exception {
    Module pairProperty = new Module();
    pairProperty.addProperty(new PairProperty<>("price", 12, "EUR"));
    modules.add(pairProperty);
    ScadDocuFile file = new ScadDocuFile(Paths.get("testPath"), modules);
    assertEquals("# Parts Documentation" + lineSeparator
            + "" + lineSeparator
            + "## testPath" + lineSeparator
            + "price" + lineSeparator
            + "-----" + lineSeparator
            + "12 EUR" + lineSeparator,
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
    assertEquals("# Parts Documentation" + lineSeparator
            + "" + lineSeparator
            + "## testPath" + lineSeparator
            + "key1|key2" + lineSeparator
            + "----|----" + lineSeparator
            + "value1|value2" + lineSeparator,
        new String(exporter.getOutput(file), "UTF-8"));
  }

  /**
   * Test against a file with multiple modules
   */
  @Test
  void multipleModules() throws Exception {
    Module mod1 = new Module();
    Module mod2 = new Module();

    mod1.addProperty(new SingleProperty<>("key", "value"));
    mod2.addProperty(new SingleProperty<>("key", "value"));

    modules.add(mod1);
    modules.add(mod2);

    ScadDocuFile file = new ScadDocuFile(Paths.get("testPath"), modules);
    assertEquals("# Parts Documentation" + lineSeparator
            + "" + lineSeparator
            + "## testPath" + lineSeparator
            + "key" + lineSeparator
            + "---" + lineSeparator
            + "value" + lineSeparator
            + "value" + lineSeparator,
        new String(exporter.getOutput(file), "UTF-8"));
  }

  /**
   * Test against a collection of multiple files
   */
  @Test
  void multipleFiles() throws Exception {
    Module mod1 = new Module();
    Module mod2 = new Module();

    mod1.addProperty(new SingleProperty<>("key1", "value1"));
    mod2.addProperty(new SingleProperty<>("key2", "value2"));

    modules.add(mod1);
    modules.add(mod2);

    ScadDocuFile file1 = new ScadDocuFile(Paths.get("testPath1"), modules);
    ScadDocuFile file2 = new ScadDocuFile(Paths.get("testPath2"), modules);

    List<ScadDocuFile> files = new ArrayList<>();
    files.add(file1);
    files.add(file2);

    assertEquals("# Parts Documentation" + lineSeparator
            + "" + lineSeparator
            + "## testPath1" + lineSeparator
            + "key1|key2" + lineSeparator
            + "----|----" + lineSeparator
            + "value1|" + lineSeparator
            + "|value2" + lineSeparator
            + "" + lineSeparator
            + "## testPath2" + lineSeparator
            + "key1|key2" + lineSeparator
            + "----|----" + lineSeparator
            + "value1|" + lineSeparator
            + "|value2" + lineSeparator,
        new String(exporter.getOutput(files), "UTF-8"));
  }
}