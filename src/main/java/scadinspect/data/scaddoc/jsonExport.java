package scadinspect.data.scaddoc;

import java.util.List;
import jdk.nashorn.internal.runtime.JSONListAdapter;
import org.json.*;
import scadinspect.data.scaddoc.properties.*;
import scadinspect.data.scaddoc.properties.Property;
import scadinspect.data.scaddoc.properties.impl.IntSingleProperty;

/**
 * Created by simon on 3/17/17.
 */
public class jsonExport {

  public static void main(String[] args) {
    jsonExport export = new jsonExport();
    System.out.println("test");
    Module test = new Module();
    test.addProperty(new IntSingleProperty("amount", 20));
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


