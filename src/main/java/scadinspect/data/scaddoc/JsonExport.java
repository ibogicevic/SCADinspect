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

  private JSONObject singleModule(Module module) {

    JSONObject element = new JSONObject();
    for (Property property : module.getProperties()) {
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

  public String getJson(List<Module> modules) {
    JSONArray list = new JSONArray();
    for (Module module : modules) {
      list.put(singleModule(module));
    }
    return list.toString();
  }

}


