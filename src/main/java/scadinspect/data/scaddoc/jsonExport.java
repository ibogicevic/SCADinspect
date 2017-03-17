package scadinspect.data.scaddoc;


import java.util.ArrayList;
import java.util.List;
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
    test.addProperty(new IntSingleProperty("Single Int", 20));
    test.addProperty(new RealSingleProperty("Single Flaot", 123.33));
    test.addProperty(new IntMultiProperty("Multiple Int", 1l, 2l, 3l));
    test.addProperty(new RealMultiProperty("Multiple Float", 1.0, 2.6));
    test.addProperty(new PairProperty("Pair", new Pair(100, "EUR")));
    test.addProperty(new StringSingleProperty("Single String", "test"));
    test.addProperty(new StringMultiProperty("Multiple Strings", "abc", "def"));

    List<Module> multuTest=new ArrayList<>();
    multuTest.add(test);
    multuTest.add(test);
    System.out.println(export.multiLine(multuTest));
  }

  public JSONObject oneLine(Module part) {

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

    return element;
  }

  public String multiLine(List<Module> parts) {
    JSONArray list=new JSONArray();
    for (Module part:parts){
      list.put(oneLine(part));
    }
    return list.toString();
  }

}


