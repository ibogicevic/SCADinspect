package scadinspect.data.scaddoc.export.format;

import scadinspect.data.scaddoc.Module;
import scadinspect.data.scaddoc.ScadDocuFile;
import scadinspect.data.scaddoc.export.FileExport;
import scadinspect.data.scaddoc.properties.MultiProperty;
import scadinspect.data.scaddoc.properties.PairProperty;
import scadinspect.data.scaddoc.properties.SingleProperty;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Created by Orhan Bilir on 28.04.2017
 */
public class ExcelExporterTest {
  
  String lineSeparator = System.lineSeparator();
  private Exporter exporter;
  private List<Module> modules;
  
  @BeforeEach
  void instantiate(){
    exporter = new ExcelExporter();
    modules = new ArrayList<>();
  }
  
  @Test
  void sampleFile() throws Exception {
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
    ScadDocuFile file = new ScadDocuFile(Paths.get("MyDummy"), modules);
    //FileExport fileOut = new FileExport();
    //fileOut.save(ExportFormat.EXCEL, file, "./export.xls");
    exporter.getOutput(file);
    /*
    assertEquals("Testing" + lineSeparator
            + "part,price,amount,weight,materials,url" + lineSeparator
            + "Wheel,100 EUR,4,12 kg,Rubber: Aluminium,https://example.com" + lineSeparator
            + "Motor,1000 USD,1,200 kg,Steel,https://example.com" + lineSeparator,
        exporter.getOutput(file));
        */
  }

}