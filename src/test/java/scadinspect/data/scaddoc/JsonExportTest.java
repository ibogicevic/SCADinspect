package scadinspect.data.scaddoc;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Created by desyon on 3/17/17.
 */
class JsonExportTest {

  private JsonExport exporter;
  private List<Module> modules;

  @BeforeEach
  void instanciate() {
    exporter = new JsonExport();
    modules = new LinkedList<>();
  }

  @AfterEach
  void output() {
    System.out.println(exporter.multiLine(modules));
  }

  @Test
  void emptyList() {
    assertEquals("[]",exporter.multiLine(modules));
  }

  @Test
  void singlePropertyInt(){
    Module singleProperty = new Module();
    singleProperty.addProperty(new SingleProperty<>("key",1));
    modules.add(singleProperty);
    assertEquals("[{\"key\":1}]",exporter.multiLine(modules));
  }
  @Test
  void singlePropertyFloat(){
    Module singleProperty = new Module();
    singleProperty.addProperty(new SingleProperty<>("key",0.1));
    modules.add(singleProperty);
    assertEquals("[{\"key\":0.1}]",exporter.multiLine(modules));
  }
  @Test
  void singlePropertyString(){
    Module singleProperty = new Module();
    singleProperty.addProperty(new SingleProperty<>("key","value"));
    modules.add(singleProperty);
    assertEquals("[{\"key\":\"value\"}]",exporter.multiLine(modules));
  }
  @Test
  void singlePropertyToString(){
    assertEquals("key: value",new SingleProperty<>("key","value").toString());

  }
  @Test
  void multiPropertyInt(){
    Module multiProperty = new Module();
    multiProperty.addProperty(new MultiProperty<>("key",1,2,3));
    modules.add(multiProperty);
    assertEquals("[{\"key\":[1,2,3]}]",exporter.multiLine(modules));
  }
  @Test
  void multiPropertyFloat(){
    Module multiProperty = new Module();
    multiProperty.addProperty(new MultiProperty<>("key",0.1,1.1,2,4));
    modules.add(multiProperty);
    assertEquals("[{\"key\":[0.1,1.1,2,4]}]",exporter.multiLine(modules));
  }
  @Test
  void multiPropertyString(){
    Module multiProperty = new Module();
    multiProperty.addProperty(new MultiProperty<>("key","a","b","c"));
    modules.add(multiProperty);
    assertEquals("[{\"key\":[\"a\",\"b\",\"c\"]}]",exporter.multiLine(modules));
  }
  @Test
  void multiPropertyList(){
    Module multiProperty = new Module();
    List property=new LinkedList<String>();
    property.add("a");
    property.add("b");
    property.add("c");
    multiProperty.addProperty(new MultiProperty("key",property));
    modules.add(multiProperty);
    assertEquals("[{\"key\":[\"a\",\"b\",\"c\"]}]",exporter.multiLine(modules));
  }
  @Test
  void multiPropertyToString(){
    assertEquals("key: [a, b]",new MultiProperty<>("key","a","b").toString());

  }
//  @Test
//  void pairPropertyInt(){
//    Module pairProperty = new Module();
//    pairProperty.addProperty(new PairProperty<>("key","12"));
//    modules.add(pairProperty);
//    assertEquals("[{\"key\":[1,2,3]}]",exporter.multiLine(modules));
//  }
//  @Test
//  void pairPropertyFloat(){
//    Module pairProperty = new Module();
//    pairProperty.addProperty(new PairProperty<>("key",0.1,1.1,2,4));
//    modules.add(pairProperty);
//    assertEquals("[{\"key\":[0.1,1.1,2,4]}]",exporter.multiLine(modules));
//  }
//  @Test
//  void pairPropertyString(){
//    Module pairProperty = new Module();
//    pairProperty.addProperty(new PairProperty<>("key","a","b","c"));
//    modules.add(pairProperty);
//    assertEquals("[{\"key\":[\"a\",\"b\",\"c\"]}]",exporter.multiLine(modules));
//  }
//    @Test
//  void pairPropertyToString(){
//    assertEquals("key: [a, b]",new PairProperty<>("key","a","b").toString());
//
//  }
}