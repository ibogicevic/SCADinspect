package scadinspect.data.scaddoc.export;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scadinspect.data.scaddoc.Module;
import scadinspect.data.scaddoc.ScadDocuFile;
import scadinspect.data.scaddoc.error.FileExportException;
import scadinspect.data.scaddoc.export.format.ExportFormat;
import scadinspect.data.scaddoc.properties.MultiProperty;
import scadinspect.data.scaddoc.properties.PairProperty;
import scadinspect.data.scaddoc.properties.Property;
import scadinspect.data.scaddoc.properties.SingleProperty;

/**
 * @author desyon, eric on 3/23/17.
 */
class FileExportTest {

  static private ScadDocuFile file;
  private FileExport fExport;

  /**
   * Prepares the list of modules that is used to test the exporting before any tests are run.
   */
  @BeforeAll
  static void setUpScadFile() {
    List <Module> modules = new LinkedList<>();
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

    file = new ScadDocuFile(Paths.get("testPath"), modules);
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
    File exportedFile = fExport.save(ExportFormat.JSON, file, "./export.json");

    byte[] sample = Files.readAllBytes(sampleFile.toPath());
    byte[] exported = Files.readAllBytes(exportedFile.toPath());

    exportedFile.delete();

    assertEquals(new String(sample).replaceAll("\\r?\\n", "\n"),
        new String(exported).replaceAll("\\r?\\n", "\n"));
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
    File exportedFile = fExport.save(ExportFormat.XML, file, "./export.xml");

    byte[] sample = Files.readAllBytes(sampleFile.toPath());
    byte[] exported = Files.readAllBytes(exportedFile.toPath());

    exportedFile.delete();

    assertEquals(new String(sample).replaceAll("\\r?\\n", "\n"),
        new String(exported).replaceAll("\\r?\\n", "\n"));
  }

  @Test
  void saveAsMd() throws FileExportException, IOException {

    File sampleFile = new File("./spec/samples/output_sample.md");
    File exportedFile = fExport.save(ExportFormat.MD, file, "./export.md");

    byte[] sample = Files.readAllBytes(sampleFile.toPath());
    byte[] exported = Files.readAllBytes(exportedFile.toPath());

    exportedFile.delete();

    assertEquals(new String(sample).replaceAll("\\r?\\n", "\n"),
        new String(exported).replaceAll("\\r?\\n", "\n"));
  }

  @Test
  void saveAsCsv() throws FileExportException, IOException {

    File sampleFile = new File("./spec/samples/output_sample.csv");
    File exportedFile = fExport.save(ExportFormat.CSV, file, "./export.csv");

    byte[] sample = Files.readAllBytes(sampleFile.toPath());
    byte[] exported = Files.readAllBytes(exportedFile.toPath());

    exportedFile.delete();

    assertEquals(new String(sample).replaceAll("\\r?\\n", "\n"),
        new String(exported).replaceAll("\\r?\\n", "\n"));
  }
  
  @Test
  void saveAsExcel() throws FileExportException, IOException {
    File sampleFile = new File("./spec/samples/output_sample.xls");
    File exportedFile = fExport.save(ExportFormat.EXCEL, file, "./export.xls");

    byte[] sample = Files.readAllBytes(sampleFile.toPath());
    byte[] exported = Files.readAllBytes(exportedFile.toPath());

    exportedFile.delete();

    assertEquals(new String(sample).replaceAll("\\r?\\n", "\n"),
        new String(exported).replaceAll("\\r?\\n", "\n"));
  }

  /**
   * Tests if an exception is thrown on JSONExport on wrong input
   */
  @Test
  void throwSingleFile() {
    assertThrows(FileExportException.class, () ->
        fExport.save(ExportFormat.JSON, file, null));
  }

  /**
   * Tests if an exception is thrown on XMLExport on wrong input
   */
  @Test
  void throwCollection() {
    HashSet<ScadDocuFile> set = new HashSet<>();
    set.add(file);
    assertThrows(FileExportException.class, () ->
        fExport.save(ExportFormat.XML, set, null));
  }
}