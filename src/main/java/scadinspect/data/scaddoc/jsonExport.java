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
    test.addProperty(new IntSingleProperty("Singlel Int", 20));
    test.addProperty(new RealSingleProperty("Single Flaot", 123.33));
    test.addProperty(new IntMultiProperty("Multiple Int", 1l, 2l, 3l));
    test.addProperty(new RealMultiProperty("Multiple Float", 1.0, 2.6));
    test.addProperty(new PairProperty("Pair", new Pair(100, "EUR")));
    test.addProperty(new StringSingleProperty("Single String", "test"));
    test.addProperty(new StringMultiProperty("Multiple Strings", "abc", "def"));
    System.out.println(export.oneLine(test));
  }

  public String oneLine(Module part) {
    JSONArray module = new JSONArray();
    JSONObject element = new JSONObject();
    for (Property property : part.getProperties()) {
      if (Pair.class.isInstance(property.getValue())) {
        JSONObject pair = new JSONObject();
        Pair tmp = (Pair) property.getValue();
        pair.accumulate("value", tmp.getValue());
        pair.accumulate("metric", tmp.getMetric());
        element.accumulate(property.getKey(), pair);
      } else {
        element.accumulate(property.getKey(), property.getValue());
      }
    }
    module.put(element);
    return module.toString();
  }
}


