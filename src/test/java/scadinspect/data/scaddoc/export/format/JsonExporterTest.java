package scadinspect.data.scaddoc.export.format;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scadinspect.data.scaddoc.Module;
import scadinspect.data.scaddoc.ScadDocuFile;
import scadinspect.data.scaddoc.properties.MultiProperty;
import scadinspect.data.scaddoc.properties.PairProperty;
import scadinspect.data.scaddoc.properties.SingleProperty;

/**
 * @author simon, Desyon on 3/17/17.
 */
class JsonExporterTest {

  private JsonExporter exporter;
  private List<Module> modules;

  @BeforeEach
  void instantiate() {
    exporter = new JsonExporter();
    modules = new LinkedList<>();
  }

  /**
   * Tests the export of an empty list.
   *
   * @result Ensure the export is failsafe.
   */
  @Test
  void emptyList() throws Exception{
    ScadDocuFile file = new ScadDocuFile(null, modules);
    assertEquals("[]", new String(exporter.getOutput(file), "UTF-8"));
  }

  /**
   * Test against a single property containing an integer value.
   */
  @Test
  void singleProperty() throws Exception{
    Module singleProperty = new Module();
    singleProperty.addProperty(new SingleProperty<>("key", 1));
    modules.add(singleProperty);
    ScadDocuFile file = new ScadDocuFile(null, modules);
    assertEquals("[{\"key\": 1}]", new String(exporter.getOutput(file), "UTF-8"));
  }

  /**
   * Test against multiple properties containing integer values.
   */
  @Test
  void multiProperty() throws Exception{
    Module multiProperty = new Module();
    multiProperty.addProperty(new MultiProperty<>("key", 1, 2, 3));
    modules.add(multiProperty);
    ScadDocuFile file = new ScadDocuFile(null, modules);
    assertEquals("[{\"key\": [\n"
        + "  1,\n"
        + "  2,\n"
        + "  3\n"
        + "]}]", new String(exporter.getOutput(file), "UTF-8"));
  }

  /**
   * Test against a pair property containing an integer value.
   */
  @Test
  void pairProperty() throws Exception{
    Module pairProperty = new Module();
    pairProperty.addProperty(new PairProperty<>("price", 12, "EUR"));
    modules.add(pairProperty);
    ScadDocuFile file = new ScadDocuFile(null, modules);
    assertEquals("[{\"price\": {\n"
        + "  \"metric\": \"EUR\",\n"
        + "  \"value\": 12\n"
        + "}}]", new String(exporter.getOutput(file), "UTF-8"));
  }

  @Test
  void multipleProperties() throws Exception {
    Module mod = new Module();

    mod.addProperty(new SingleProperty<>("key1", "value1"));
    mod.addProperty(new SingleProperty<>("key2", "value2"));

    modules.add(mod);

    ScadDocuFile file = new ScadDocuFile(null, modules);
    assertEquals("[{\n"
        + "  \"key1\": \"value1\",\n"
        + "  \"key2\": \"value2\"\n"
        + "}]", new String(exporter.getOutput(file), "UTF-8"));
  }

  /**
   * Test against the defined example Json
   */
  @Test
  void sampleJSON() throws Exception{
    Module wheel = new Module();
    wheel.addProperty(new SingleProperty<>("part", "Wheel"));
    wheel.addProperty(new PairProperty<>("price", 100, "EUR"));
    wheel.addProperty(new SingleProperty<>("amount", 4));
    wheel.addProperty(new PairProperty<>("weight", 12, "kg"));
    wheel.addProperty(new MultiProperty<>("materials", "Rubber", "Aluminium"));
    wheel.addProperty(new SingleProperty<>("url", "https://example.com"));

    Module motor = new Module();
    motor.addProperty(new SingleProperty<>("part", "Motor"));
    motor.addProperty(new PairProperty<>("price", 1000, "USD"));
    motor.addProperty(new SingleProperty<>("amount", 1));
    motor.addProperty(new PairProperty<>("weight", 200, "kg"));
    motor.addProperty(new SingleProperty<>("materials", "Steel"));
    motor.addProperty(new SingleProperty<>("url", "https://example.com"));

    modules.add(wheel);
    modules.add(motor);
    ScadDocuFile file = new ScadDocuFile(null, modules);

    assertEquals("[\n"
            + "  {\n"
            + "    \"amount\": 4,\n"
            + "    \"materials\": [\n"
            + "      \"Rubber\",\n"
            + "      \"Aluminium\"\n"
            + "    ],\n"
            + "    \"price\": {\n"
            + "      \"metric\": \"EUR\",\n"
            + "      \"value\": 100\n"
            + "    },\n"
            + "    \"part\": \"Wheel\",\n"
            + "    \"weight\": {\n"
            + "      \"metric\": \"kg\",\n"
            + "      \"value\": 12\n"
            + "    },\n"
            + "    \"url\": \"https://example.com\"\n"
            + "  },\n"
            + "  {\n"
            + "    \"amount\": 1,\n"
            + "    \"materials\": \"Steel\",\n"
            + "    \"price\": {\n"
            + "      \"metric\": \"USD\",\n"
            + "      \"value\": 1000\n"
            + "    },\n"
            + "    \"part\": \"Motor\",\n"
            + "    \"weight\": {\n"
            + "      \"metric\": \"kg\",\n"
            + "      \"value\": 200\n"
            + "    },\n"
            + "    \"url\": \"https://example.com\"\n"
            + "  }\n"
            + "]",
        new String(exporter.getOutput(file), "UTF-8"));
  }
}