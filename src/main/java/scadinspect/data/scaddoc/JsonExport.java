package scadinspect.data.scaddoc;



import java.util.List;
import org.json.*;
import scadinspect.data.scaddoc.properties.Property;
import scadinspect.data.scaddoc.properties.helper.Pair;


/**
 * Created by simon on 3/17/17.
 */
public class JsonExport {

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


