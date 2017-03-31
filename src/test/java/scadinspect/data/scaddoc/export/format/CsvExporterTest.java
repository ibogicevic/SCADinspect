package scadinspect.data.scaddoc.export.format;

import static org.junit.jupiter.api.Assertions.*;

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
 * Created by richteto on 31.03.2017.
 */
class CsvExporterTest {

  private Exporter exporter;
  private List<Module> modules;

  @BeforeEach
  void instantiate() {
    exporter = new CsvExporter();
    modules = new ArrayList<>();
  }

  @Test
  void emptyList() throws Exception {
    ScadDocuFile file = new ScadDocuFile(Paths.get("Testing"), modules);
    assertEquals("Testing\n\n", exporter.getOutput(file));
  }

  @Test
  void singlePropertyInt() throws Exception {
    Module singleProperty = new Module();
    singleProperty.addProperty(new SingleProperty<>("key", 1));
    modules.add(singleProperty);
    ScadDocuFile file = new ScadDocuFile(Paths.get("Testing"), modules);
    assertEquals("Testing\n"
        + "key,\n"
        + "1,\n", exporter.getOutput(file));
  }

  @Test
  void sampleProperty() throws Exception {
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
    ScadDocuFile file = new ScadDocuFile(Paths.get("Testing"), modules);

    assertEquals("Testing\n"
            + "part,price,amount,weight,materials,url,\n"
            + "Wheel,100 EUR,4,12 kg,Rubber: Aluminium,https://example.com,\n"
            + "Motor,1000 USD,1,200 kg,Steel,https://example.com,\n",
        exporter.getOutput(file));
  }

}