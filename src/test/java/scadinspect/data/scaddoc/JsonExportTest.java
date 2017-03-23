package scadinspect.data.scaddoc;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scadinspect.data.scaddoc.properties.MultiProperty;
import scadinspect.data.scaddoc.properties.PairProperty;
import scadinspect.data.scaddoc.properties.SingleProperty;

/**
 * @author simon, desyon on 3/17/17.
 */
class JsonExportTest {

  private JsonExport exporter;
  private List<Module> modules;

  @BeforeEach
  void instantiate() {
    exporter = new JsonExport();
    modules = new LinkedList<>();
  }

  /**
   * Tests the export of an empty list.
   *
   * @result Ensure the export is failsafe.
   */
  @Test
  void emptyList() {
    assertEquals("[]", exporter.getJson(modules));
  }

  /**
   * Test against a single property containing an integer value.
   */
  @Test
  void singlePropertyInt() {
    Module singleProperty = new Module();
    singleProperty.addProperty(new SingleProperty<>("key", 1));
    modules.add(singleProperty);
    assertEquals("[{\"key\": 1}]", exporter.getJson(modules));
  }

  /**
   * Test against a single property containing a float value.
   */
  @Test
  void singlePropertyFloat() {
    Module singleProperty = new Module();
    singleProperty.addProperty(new SingleProperty<>("key", 0.1));
    modules.add(singleProperty);
    assertEquals("[{\"key\": 0.1}]", exporter.getJson(modules));
  }

  /**
   * Test against a single property containing a string value.
   */
  @Test
  void singlePropertyString() {
    Module singleProperty = new Module();
    singleProperty.addProperty(new SingleProperty<>("key", "value"));
    modules.add(singleProperty);
    assertEquals("[{\"key\": \"value\"}]", exporter.getJson(modules));
  }

  /**
   * Test against multiple properties containing integer values.
   */
  @Test
  void multiPropertyInt() {
    Module multiProperty = new Module();
    multiProperty.addProperty(new MultiProperty<>("key", 1, 2, 3));
    modules.add(multiProperty);
    assertEquals("[{\"key\": [\n"
        + "  1,\n"
        + "  2,\n"
        + "  3\n"
        + "]}]", exporter.getJson(modules));
  }

  /**
   * Test against multiple properties containing float values.
   */
  @Test
  void multiPropertyFloat() {
    Module multiProperty = new Module();
    multiProperty.addProperty(new MultiProperty<>("key", 0.1, 1.1, 2, 4));
    modules.add(multiProperty);
    assertEquals("[{\"key\": [\n"
        + "  0.1,\n"
        + "  1.1,\n"
        + "  2,\n"
        + "  4\n"
        + "]}]", exporter.getJson(modules));
  }

  /**
   * Test against multiple properties containing string values.
   */
  @Test
  void multiPropertyString() {
    Module multiProperty = new Module();
    multiProperty.addProperty(new MultiProperty<>("key", "a", "b", "c"));
    modules.add(multiProperty);
    assertEquals("[{\"key\": [\n"
        + "  \"a\",\n"
        + "  \"b\",\n"
        + "  \"c\"\n"
        + "]}]", exporter.getJson(modules));
  }

  /**
   * Test against a pair property containing an integer value.
   */
  @Test
  void pairPropertyInt() {
    Module pairProperty = new Module();
    pairProperty.addProperty(new PairProperty<>("price", 12, "EUR"));
    modules.add(pairProperty);
    assertEquals("[{\"price\": {\n"
        + "  \"metric\": \"EUR\",\n"
        + "  \"value\": 12\n"
        + "}}]", exporter.getJson(modules));
  }

  /**
   * Test against a pair property containing a float value.
   */
  @Test
  void pairPropertyFloat() {
    Module pairProperty = new Module();
    pairProperty.addProperty(new PairProperty<>("price", 12.3, "EUR"));
    modules.add(pairProperty);
    assertEquals("[{\"price\": {\n"
        + "  \"metric\": \"EUR\",\n"
        + "  \"value\": 12.3\n"
        + "}}]", exporter.getJson(modules));
  }

  /**
   * Test against a pair property containing a String value.
   */
  @Test
  void pairPropertyString() {
    Module pairProperty = new Module();
    pairProperty.addProperty(new PairProperty<>("weight", "1200", "kg"));
    modules.add(pairProperty);
    assertEquals("[{\"weight\": {\n"
            + "  \"metric\": \"kg\",\n"
            + "  \"value\": \"1200\"\n"
            + "}}]",
        exporter.getJson(modules));
  }
}