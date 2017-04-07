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
 * @author desyon on 3/24/17.
 */
class XmlExporterTest {

  private XmlExporter exporter;
  private List<Module> modules;

  private String lineSeparator;

  @BeforeEach
  void instantiate() {
    exporter = new XmlExporter();
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
    assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" + lineSeparator
            + "<testPath/>" + lineSeparator,
        exporter.getOutput(file));
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
    assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" + lineSeparator
            + "<testPath>" + lineSeparator
            + "  <module>" + lineSeparator
            + "    <key>1</key>" + lineSeparator
            + "  </module>" + lineSeparator
            + "</testPath>" + lineSeparator,
        exporter.getOutput(file));

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
    assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" + lineSeparator
            + "<testPath>" + lineSeparator
            + "  <module>" + lineSeparator
            + "    <key>1, 2, 3</key>" + lineSeparator
            + "  </module>" + lineSeparator
            + "</testPath>" + lineSeparator,
        exporter.getOutput(file));
  }

  /**
   * Test against a pair property containing an integer value.
   */
  @Test
  void pairProperty() throws Exception {
    Module pairProperty = new Module();
    pairProperty.addProperty(new PairProperty<>("price", 12, "EUR"));
    modules.add(pairProperty);
    ScadDocuFile file = new ScadDocuFile(Paths.get("testPath"), modules);
    assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" + lineSeparator
            + "<testPath>" + lineSeparator
            + "  <module>" + lineSeparator
            + "    <price>" + lineSeparator
            + "      <metric>EUR</metric>" + lineSeparator
            + "      <value>12</value>" + lineSeparator
            + "    </price>" + lineSeparator
            + "  </module>" + lineSeparator
            + "</testPath>" + lineSeparator,
        exporter.getOutput(file));
  }

  @Test
  void multipleModules() throws Exception {
    Module mod1 = new Module();
    Module mod2 = new Module();

    mod1.addProperty(new SingleProperty<>("key", "value"));
    mod2.addProperty(new SingleProperty<>("key", "value"));

    modules.add(mod1);
    modules.add(mod2);

    ScadDocuFile file = new ScadDocuFile(Paths.get("testPath"), modules);
    assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" + lineSeparator
            + "<testPath>" + lineSeparator
            + "  <module>" + lineSeparator
            + "    <key>value</key>" + lineSeparator
            + "  </module>" + lineSeparator
            + "  <module>" + lineSeparator
            + "    <key>value</key>" + lineSeparator
            + "  </module>" + lineSeparator
            + "</testPath>" + lineSeparator,
        exporter.getOutput(file));
  }

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

    assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" + lineSeparator
            + "<files>" + lineSeparator
            + "  <testPath1>" + lineSeparator
            + "    <module>" + lineSeparator
            + "      <key>value</key>" + lineSeparator
            + "    </module>" + lineSeparator
            + "    <module>" + lineSeparator
            + "      <key>value</key>" + lineSeparator
            + "    </module>" + lineSeparator
            + "  </testPath1>" + lineSeparator
            + "  <testPath2>" + lineSeparator
            + "    <module>" + lineSeparator
            + "      <key>value</key>" + lineSeparator
            + "    </module>" + lineSeparator
            + "    <module>" + lineSeparator
            + "      <key>value</key>" + lineSeparator
            + "    </module>" + lineSeparator
            + "  </testPath2>" + lineSeparator
            + "</files>" + lineSeparator,
        exporter.getOutput(files));
  }
}