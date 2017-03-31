package scadinspect.data.scaddoc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scadinspect.data.scaddoc.error.FileExportException;
import scadinspect.data.scaddoc.properties.MultiProperty;
import scadinspect.data.scaddoc.properties.PairProperty;
import scadinspect.data.scaddoc.properties.Property;
import scadinspect.data.scaddoc.properties.SingleProperty;

/**
 * @author desyon, eric on 3/23/17.
 */
class FileExportTest {

  static private List<Module> modules;
  private FileExport fExport;

  /**
   * Prepares the list of modules that is used to test the exporting before any tests are run.
   */
  @BeforeAll
  static void setupModules() {
    modules = new LinkedList<>();
    List<Property> wheelProps = new LinkedList<>();
    List<Property> motorProps = new LinkedList<>();

    wheelProps.add(new SingleProperty<>("amount", 4));
    wheelProps.add(new MultiProperty<>("materials", "Rubber", "Aluminium"));
    wheelProps.add(new PairProperty<>("price", 100, "EUR"));
    wheelProps.add(new SingleProperty<>("part", "Wheel"));
    wheelProps.add(new PairProperty<>("weight", 12, "kg"));
    wheelProps.add(new SingleProperty<>("url", "https://example.com"));
    Module wheel = new Module(wheelProps);

    motorProps.add(new SingleProperty<>("amount", 1));
    motorProps.add(new SingleProperty<>("materials", "Steel"));
    motorProps.add(new PairProperty<>("price", 1000, "USD"));
    motorProps.add(new SingleProperty<>("part", "Motor"));
    motorProps.add(new PairProperty<>("weight", 200, "kg"));
    motorProps.add(new SingleProperty<>("url", "https://example.com"));
    Module motor = new Module(motorProps);

    modules.add(wheel);
    modules.add(motor);
  }

  /**
   * Instantiates the file exporter before each of the tests.
   */
  @BeforeEach
  void instantiate() {
    fExport = new FileExport();
  }

  /**
   * Tests the export to JSON format
   *
   * @throws FileExportException if something went wrong during the export
   * @throws IOException if the creation of the test file failed
   */
  @Test
  void saveAsJson() throws FileExportException, IOException {

    File sampleFile = new File("./spec/samples/output_sample.json");
    File exportedFile = fExport.saveAsJson(modules, "./export.json");

    byte[] sample = Files.readAllBytes(sampleFile.toPath());
    byte[] exported = Files.readAllBytes(exportedFile.toPath());

    exportedFile.delete();

    assertEquals(new String(sample).replaceAll("\\r\\n?", "\n"),
        new String(exported).replaceAll("\\r\\n?", "\n"));
  }


  /**
   * Tests the export to XML format
   *
   * @throws FileExportException if something went wrong during the export
   * @throws IOException if the creation of the test file failed
   */
  @Test
  void saveAsXml() throws FileExportException, IOException {

    File sampleFile = new File("./spec/samples/output_sample.xml");
    File exportedFile = fExport.saveAsXml(modules, "./export.xml");

    byte[] sample = Files.readAllBytes(sampleFile.toPath());
    byte[] exported = Files.readAllBytes(exportedFile.toPath());

    exportedFile.delete();

    assertEquals(new String(sample).replaceAll("\\r\\n?", "\n"),
        new String(exported).replaceAll("\\r\\n?", "\n"));
  }

  /**
   * Tests if an exception is thrown on XMLExport on wrong input
   */
  @Test
  void throwFromXML() {
    assertThrows(FileExportException.class, () ->
        fExport.saveAsXml(null, null));
  }

  /**
   * Tests if an exception is thrown on JSONExport on wrong input
   */
  @Test
  void throwFromJson() {
    assertThrows(FileExportException.class, () ->
        fExport.saveAsJson(null, null));
  }
}