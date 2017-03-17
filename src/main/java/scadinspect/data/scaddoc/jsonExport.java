package scadinspect.data.scaddoc;

import java.util.LinkedList;
import java.util.List;
import jdk.nashorn.internal.runtime.JSONListAdapter;
import org.json.*;
import scadinspect.data.scaddoc.properties.*;
import scadinspect.data.scaddoc.properties.Property;
import scadinspect.data.scaddoc.properties.helper.Pair;
import scadinspect.data.scaddoc.properties.impl.IntMultiProperty;
import scadinspect.data.scaddoc.properties.impl.IntSingleProperty;
import scadinspect.data.scaddoc.properties.impl.RealMultiProperty;
import scadinspect.data.scaddoc.properties.impl.RealSingleProperty;
import scadinspect.data.scaddoc.properties.impl.StringMultiProperty;
import scadinspect.data.scaddoc.properties.impl.StringSingleProperty;

/**
 */
public class jsonExport {

  public static void main(String[] args) {
    jsonExport export = new jsonExport();
    System.out.println("test");
    Module test = new Module();
    test.addProperty(new IntSingleProperty("amount", 20));
    test.addProperty(new RealSingleProperty("weight",123.33));
    test.addProperty(new IntMultiProperty("longmult", 1l,2l,3l));
    test.addProperty(new RealMultiProperty("realmult", 1.0,2.6));
    test.addProperty(new PairProperty("pair",new Pair(100,"EUR")));
    test.addProperty(new StringSingleProperty("asdfs", "asdfsdfasdf"));
    test.addProperty(new StringMultiProperty("asdf", "sasdf","aasdfasdfsdfasdfasdf"));
    System.out.println(export.oneLine(test));
  }

  public String oneLine(Module part) {
    JSONArray module = new JSONArray();
    for (Property property : part.getProperties()) {
      System.out.println(property);
    }
    return module.toString();
  }
}


