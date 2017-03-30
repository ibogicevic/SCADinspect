package scadinspect.data.scaddoc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scadinspect.data.scaddoc.properties.MultiProperty;
import scadinspect.data.scaddoc.properties.PairProperty;
import scadinspect.data.scaddoc.properties.SingleProperty;

/**
 * @author desyon on 3/23/17.
 */
class FileExportTest {

  static private List<Module> modules;
  private FileExport fExport;

  @BeforeAll
  static void setupModules() {
    modules = new LinkedList<>();

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
  }

  @BeforeEach
  void instantiate() {
    fExport = new FileExport();
  }

  @Test
  void saveAsJson() throws IOException {

    File sampleFile = new File("./spec/samples/output_sample.json");
    File exportedFile = fExport.saveAsJson(modules, "./export.json");

    byte[] sample = Files.readAllBytes(sampleFile.toPath());
    byte[] exported = Files.readAllBytes(exportedFile.toPath());

    exportedFile.delete();

    assertEquals(new String(sample).replaceAll("\\r\\n?","\n"), new String(exported).replaceAll("\\r\\n?","\n"));
  }
}