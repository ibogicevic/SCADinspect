package scadinspect.data.scaddoc;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import scadinspect.data.scaddoc.properties.Property;

/**
 * @author richteto on 17.03.2017.
 */
public class Module {

  private Collection<Property> properties;

  public Module(Collection<Property> properties) {
    this.properties = properties;
  }

  public Module() {
    this(new HashSet<>());
  }

  public void addProperty(Property property) {
    properties.add(property);
  }

  public Collection<Property> getProperties() {
    return properties;
  }

}
