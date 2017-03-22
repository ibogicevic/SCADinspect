package scadinspect.data.scaddoc;

import java.util.LinkedList;
import java.util.List;
import scadinspect.data.scaddoc.properties.Property;

/**
 * @author richteto on 17.03.2017.
 */
public class Module {

  private List<Property> properties;

  public Module(List<Property> properties) {
    this.properties = properties;
  }

  public Module() {
    this(new LinkedList<>());
  }

  public void addProperty(Property property) {
    properties.add(property);
  }

  public List<Property> getProperties() {
    return properties;
  }

}
