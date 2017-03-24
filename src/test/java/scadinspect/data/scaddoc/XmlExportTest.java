package scadinspect.data.scaddoc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scadinspect.data.scaddoc.properties.MultiProperty;
import scadinspect.data.scaddoc.properties.PairProperty;
import scadinspect.data.scaddoc.properties.SingleProperty;

/**
 * Created by desyon on 3/24/17.
 */
class XmlExportTest {

  private XmlExport exporter;
  private List<Module> modules;

  @BeforeEach
  void instantiate() {
    exporter = new XmlExport();
    modules = new ArrayList<>();
  }

  @Test
  void testThrow(){
    modules = null;
    assertThrows(Exception.class, ()
        -> exporter.getXml(modules));
  }

  /**
   * Tests the export of an empty list.
   *
   * @result Ensure the export is failsafe.
   */
  @Test
  void emptyList() throws Exception {
    assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n"
            + "<modules/>\n",
        exporter.getXml(modules));
  }

  /**
   * Test against a single property containing an integer value.
   */
  @Test
  void singlePropertyInt() throws Exception {
    Module singleProperty = new Module();
    singleProperty.addProperty(new SingleProperty<>("key", 1));
    modules.add(singleProperty);
    assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n"
            + "<modules>\n"
            + "  <module>\n"
            + "    <key>1</key>\n"
            + "  </module>\n"
            + "</modules>\n",
        exporter.getXml(modules));

  }

  /**
   * Test against a single property containing a float value.
   */
  @Test
  void singlePropertyFloat() throws Exception {
    Module singleProperty = new Module();
    singleProperty.addProperty(new SingleProperty<>("key", 0.1));
    modules.add(singleProperty);
    assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n"
        + "<modules>\n"
        + "  <module>\n"
        + "    <key>0.1</key>\n"
        + "  </module>\n"
        + "</modules>\n", exporter.getXml(modules));
  }

  /**
   * Test against a single property containing a string value.
   */
  @Test
  void singlePropertyString() throws Exception {
    Module singleProperty = new Module();
    singleProperty.addProperty(new SingleProperty<>("key", "value"));
    modules.add(singleProperty);
    assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n"
        + "<modules>\n"
        + "  <module>\n"
        + "    <key>value</key>\n"
        + "  </module>\n"
        + "</modules>\n", exporter.getXml(modules));
  }

  /**
   * Test against multiple properties containing integer values.
   */
  @Test
  void multiPropertyInt() throws Exception {
    Module multiProperty = new Module();
    multiProperty.addProperty(new MultiProperty<>("key", 1, 2, 3));
    modules.add(multiProperty);
    assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n"
        + "<modules>\n"
        + "  <module>\n"
        + "    <key>1, 2, 3</key>\n"
        + "  </module>\n"
        + "</modules>\n",
        exporter.getXml(modules));
  }

  /**
   * Test against multiple properties containing float values.
   */
  @Test
  void multiPropertyFloat() throws Exception {
    Module multiProperty = new Module();
    multiProperty.addProperty(new MultiProperty<>("key", 0.1, 1.1, 2, 4));
    modules.add(multiProperty);
    assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n"
        + "<modules>\n"
        + "  <module>\n"
        + "    <key>0.1, 1.1, 2, 4</key>\n"
        + "  </module>\n"
        + "</modules>\n", exporter.getXml(modules));
  }

  /**
   * Test against multiple properties containing string values.
   */
  @Test
  void multiPropertyString() throws Exception {
    Module multiProperty = new Module();
    multiProperty.addProperty(new MultiProperty<>("key", "a", "b", "c"));
    modules.add(multiProperty);
    assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n"
        + "<modules>\n"
        + "  <module>\n"
        + "    <key>a, b, c</key>\n"
        + "  </module>\n"
        + "</modules>\n", exporter.getXml(modules));
  }

  /**
   * Test against a pair property containing an integer value.
   */
  @Test
  void pairPropertyInt() throws Exception {
    Module pairProperty = new Module();
    pairProperty.addProperty(new PairProperty<>("price", 12, "EUR"));
    modules.add(pairProperty);
    assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n"
            + "<modules>\n"
            + "  <module>\n"
            + "    <price>\n"
            + "      <metric>EUR</metric>\n"
            + "      <value>12</value>\n"
            + "    </price>\n"
            + "  </module>\n"
            + "</modules>\n",
        exporter.getXml(modules));
  }

  /**
   * Test against a pair property containing a float value.
   */
  @Test
  void pairPropertyFloat() throws Exception {
    Module pairProperty = new Module();
    pairProperty.addProperty(new PairProperty<>("price", 12.3, "EUR"));
    modules.add(pairProperty);
    assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n"
            + "<modules>\n"
            + "  <module>\n"
            + "    <price>\n"
            + "      <metric>EUR</metric>\n"
            + "      <value>12.3</value>\n"
            + "    </price>\n"
            + "  </module>\n"
            + "</modules>\n",
        exporter.getXml(modules));
  }

  /**
   * Test against a pair property containing a String value.
   */
  @Test
  void pairPropertyString() throws Exception {
    Module pairProperty = new Module();
    pairProperty.addProperty(new PairProperty<>("weight", "1200", "kg"));
    modules.add(pairProperty);
    assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n"
            + "<modules>\n"
            + "  <module>\n"
            + "    <weight>\n"
            + "      <metric>kg</metric>\n"
            + "      <value>1200</value>\n"
            + "    </weight>\n"
            + "  </module>\n"
            + "</modules>\n",
        exporter.getXml(modules));
  }

   @Test
  void multipleModules() throws Exception {
    Module mod1 = new Module();
    Module mod2 = new Module();

    mod1.addProperty(new SingleProperty<>("key", "value"));
    mod2.addProperty(new SingleProperty<>("key", "value"));

    modules.add(mod1);
    modules.add(mod2);

    assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n"
        + "<modules>\n"
        + "  <module>\n"
        + "    <key>value</key>\n"
        + "  </module>\n"
        + "  <module>\n"
        + "    <key>value</key>\n"
        + "  </module>\n"
        + "</modules>\n",
        exporter.getXml(modules));
   }
}