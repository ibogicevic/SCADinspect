package scadinspect.data.scaddoc.export.format;

import java.util.Collection;
import org.json.JSONArray;
import org.json.JSONObject;
import scadinspect.data.scaddoc.Module;
import scadinspect.data.scaddoc.ScadDocuFile;
import scadinspect.data.scaddoc.properties.Property;
import scadinspect.data.scaddoc.properties.helper.Pair;

/**
 * @author simon, richteto on 3/17/17.
 */

public class JsonExporter implements Exporter {

  /**
   * Overrides getOutput from Exporter
   *
   * @param file a ScadDocuFile containing a list of modules that are supposed to be exported
   * @return returns the JSON document as String that can be written to a file or used further
   * internally
   */
  @Override
  public String getOutput(ScadDocuFile file) {
    return getJsonArray(file.getModules()).toString(2);
  }

  /**
   * Overrides getOutput from Exporter
   *
   * @param files a Collection of ScadDocuFiles containing a list of modules that are supposed to be
   * exported
   * @return returns the JSON document as String that can be written to a file or used further
   * internally
   */
  @Override
  public String getOutput(Collection<ScadDocuFile> files) throws Exception {
    JSONArray result = new JSONArray();
    for (ScadDocuFile docuFile : files) {
      JSONObject file = new JSONObject();
      file.accumulate(docuFile.getPath().toString(), getJsonArray(docuFile.getModules()));
      result.put(file);
    }
    //indent factor is 2, without the jsonString is compressed in one line
    return result.toString(2);
  }

  /**
   * Creates a JSON Node from a given module with all properties as sub-nodes
   *
   * @param module The module that a JSON node shall be created for
   * @return returns a JSONObject that contains the module with all its properties
   */
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

  /**
   * @param modules a Collection of all modules that are supposed to be exported. This list will be
   * converted into a JSON document as specified.
   * @return returns the JSON document as a Json Array Object
   */
  private JSONArray getJsonArray(Collection<Module> modules) {
    JSONArray list = new JSONArray();
    for (Module module : modules) {
      //converts each module to json object and adds it to the json array
      list.put(singleModule(module));
    }
    return list;
  }
}
