package scadinspect.data.scaddoc;

import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import scadinspect.data.scaddoc.properties.Property;
import scadinspect.data.scaddoc.properties.helper.Pair;

/**
 * Created by simon on 3/17/17.
 */

public class JsonExport {
  //takes a module and reruns it as a json object
  private JSONObject singleModule(Module module) {

    //object to be returned
    JSONObject element = new JSONObject();

    //a module consist of multiple properties
    for (Property property : module.getProperties()) {
      //if the property is a pair property it needs special treatment
      if (Pair.class.isInstance(property.getValue())) {
        JSONObject pair = new JSONObject();
        Pair tmp = (Pair) property.getValue();
        pair.accumulate("value", tmp.getValue());
        pair.accumulate("metric", tmp.getMetric());
        element.accumulate(property.getKey(), pair);
        /*pair gets this format:
        "a":{
          "value":"b",
          "metric":"c"
        }
        */
      }
      //for all other properties we can just set them as a json key,pair
      else {
        element.accumulate(property.getKey(), property.getValue());
      }
    }
    return element;
  }

  //takes a List of modules and returns a json formatted String
  public String getJson(List<Module> modules) {
    JSONArray list = new JSONArray();
    for (Module module : modules) {
      //converts each module to json object and adds it to the json array
      list.put(singleModule(module));
    }
    return list.toString();
  }

}


