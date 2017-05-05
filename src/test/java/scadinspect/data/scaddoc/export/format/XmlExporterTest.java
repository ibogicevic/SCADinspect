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
import scadinspect.data.scaddoc.properties.helper.Pair;

/**
 * @author Desyon on 3/24/17.
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
    assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" + lineSeparator
            + "<testPath>" + lineSeparator
            + "  <module>" + lineSeparator
            + "    <key>1</key>" + lineSeparator
            + "  </module>" + lineSeparator
            + "</testPath>" + lineSeparator,
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
    assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" + lineSeparator
            + "<testPath>" + lineSeparator
            + "  <module>" + lineSeparator
            + "    <key>1, 2, 3</key>" + lineSeparator
            + "  </module>" + lineSeparator
            + "</testPath>" + lineSeparator,
        new String(exporter.getOutput(file), "UTF-8"));
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
    assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" + lineSeparator
            + "<testPath>" + lineSeparator
            + "  <module>" + lineSeparator
            + "    <key1>value1</key1>" + lineSeparator
            + "    <key2>value2</key2>" + lineSeparator
            + "  </module>" + lineSeparator
            + "</testPath>" + lineSeparator,
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
    assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" + lineSeparator
            + "<testPath>" + lineSeparator
            + "  <module>" + lineSeparator
            + "    <key>value</key>" + lineSeparator
            + "  </module>" + lineSeparator
            + "  <module>" + lineSeparator
            + "    <key>value</key>" + lineSeparator
            + "  </module>" + lineSeparator
            + "</testPath>" + lineSeparator,
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
        new String(exporter.getOutput(files), "UTF-8"));
  }
  @Test
  void tormentModule() throws Exception {
    Module torment = new Module();

    torment.addProperty(new SingleProperty<>("Key#__;", "v4|u3"));
    torment.addProperty(new SingleProperty<>("Key/%§§", "{[]}\\"));
    torment.addProperty(new PairProperty<>("Pair", new Pair<>("100", "CND/AUD")));
    torment.addProperty(new MultiProperty<>("Multi", "<>|!§$%&/\"()=?", "*'+#~;:,."));

    modules.add(torment);

    ScadDocuFile file = new ScadDocuFile(Paths.get("testPath"), modules);

    assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"+lineSeparator
            + "<testPath>"+lineSeparator
            + "  <module>"+lineSeparator
            + "    <Key__>v4u3</Key__>"+lineSeparator
            + "    <Key>{}</Key>"+lineSeparator
            + "    <Pair>"+lineSeparator
            + "      <metric>CNDAUD</metric>"+lineSeparator
            + "      <value>100</value>"+lineSeparator
            + "    </Pair>"+lineSeparator
            + "    <Multi>$=, *+~:,.</Multi>"+lineSeparator
            + "  </module>"+lineSeparator
            + "</testPath>"+lineSeparator,
        new String(exporter.getOutput(file), "UTF-8"));
  }
}