package scadinspect.data.scaddoc;


import org.json.*;
import scadinspect.data.scaddoc.properties.*;
import scadinspect.data.scaddoc.properties.Property;
import scadinspect.data.scaddoc.properties.helper.Pair;
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
    test.addProperty(new RealSingleProperty("weight", 123.33));
    test.addProperty(new IntMultiProperty("longmult", 1l, 2l, 3l));
    test.addProperty(new RealMultiProperty("realmult", 1.0, 2.6));
    test.addProperty(new PairProperty("pair", new Pair(100, "EUR")));
    test.addProperty(new StringSingleProperty("asdfs", "asdfsdfasdf"));
    test.addProperty(new StringMultiProperty("asdf", "sasdf", "aasdfasdfsdfasdfasdf"));
    System.out.println(export.oneLine(test));
  }

  public String oneLine(Module part) {
    JSONArray module = new JSONArray();
    for (Property property : part.getProperties()) {

      System.out.println(property.getKey() + "\t\t" + property.getValue());
      System.out.println(property);
      System.out.println();

      JSONObject element = new JSONObject();
      if (Pair.class.isInstance(property.getValue())) {
        System.out.println(property.getValue());
      }
      else{
        element.append(property.getKey(), property.getValue());
      }
      module.put(element);
    }
    return module.toString();
  }
}


