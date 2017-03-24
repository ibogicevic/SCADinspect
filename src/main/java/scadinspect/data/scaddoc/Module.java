package scadinspect.data.scaddoc;

import java.util.Collection;
import java.util.HashSet;
import scadinspect.data.scaddoc.properties.Property;

/**
 * The object of a full comment inside the scadfile containing all properties as collection
 *
 * @author richteto on 17.03.2017.
 */
public class Module {

  private Collection<Property> properties;

  /**
   * Creating a new Module knowing some properties already
   * @param properties The properties to use within the module
   */
  public Module(Collection<Property> properties) {
    this.properties = properties;
  }

  /**
   * Creating a new module with an empty collection of properties
   */
  public Module() {
    this(new HashSet<>());
  }

  /**
   * Adding a new property to the module
   * @param property The property to be added
   */
  public void addProperty(Property property) {
    properties.add(property);
  }

  /**
   * Getting all properties of the module
   * @return The collection of properties
   */
  public Collection<Property> getProperties() {
    return properties;
  }

  @Override
  public String toString() {
    String res = "{";
    for (Property property : properties) {
      res += property.toString() + "\n";
    }
    res += "}";
    return res;
  }

  @Override
  public boolean equals(Object other) {
    if (other == null) {
      return false;
    }
    if (other == this) {
      return true;
    }
    if (!(other instanceof Module)) {
      return false;
    }

    Collection<String> stringOther = new HashSet<>();
    for (Property property : ((Module) other).getProperties()) {
      stringOther.add(property.toString());
    }
    Collection<String> stringThis = new HashSet<>();
    for (Property property : properties) {
      stringThis.add(property.toString());
    }
    for (String stringProperty : stringThis) {
      if (!stringOther.contains(stringProperty)) {
        System.out.println("Error In This: " + stringProperty.toString());
        return false;
      }
    }
    for (String stringProperty : stringOther) {
      if (!stringThis.contains(stringProperty)) {
        System.out.println("Error In Other: " + stringProperty.toString());
        return false;
      }
    }
    return true;
  }
}
