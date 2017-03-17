package scadinspect.data.scaddoc;

import org.json.*;
import scadinspect.data.scaddoc.properties.Property;
import scadinspect.data.scaddoc.properties.impl.*;


/**
 * Created by simon on 3/17/17.
 */
public class jsonExport {

  public static void main(String[] args) {
    jsonExport export = new jsonExport();
    System.out.println("test");
    Module test = new Module();
    test.addProperty(new IntSingleProperty("amount", 20));
    test.addProperty(new RealSingleProperty("amount", 24.9));
    System.out.println(export.oneLine(test));
  }

  public String oneLine(Module part) {
    JSONArray module = new JSONArray();
    for (Property property : part.getProperties()) {

      System.out.println(property.getKey()+"\t\t"+property.getValue());
      System.out.println(property);
      System.out.println();

      JSONObject element =new JSONObject();
      element.append(property.getKey(),property.getValue());
      module.put(element);
    }
    return module.toString();
  }
}


