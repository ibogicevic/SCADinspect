/*******************************************************************************
 * SCADinspect – https://github.com/ibogicevic/SCADinspect
 * Copyright (C) 2017 Ivan Bogicevic and others
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package data;

import java.util.ArrayList;
import java.util.Collection;
import data.properties.Property;

/**
 * The object of a full comment inside the scadfile containing all properties as collection
 */
public class Module {

  private Collection<Property> properties;

  /**
   * Creating a new Module knowing some properties already
   *
   * @param properties The properties to use within the module
   */
  public Module(Collection<Property> properties) {
    this.properties = properties;
  }

  /**
   * Creating a new module with an empty collection of properties
   */
  public Module() {
    this(new ArrayList<>());
  }

  /**
   * Adding a new property to the module
   *
   * @param property The property to be added
   */
  public void addProperty(Property property) {
    properties.add(property);
  }

  /**
   * Getting all properties of the module
   *
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

    Collection<String> stringOther = new ArrayList<>();
    for (Property property : ((Module) other).getProperties()) {
      stringOther.add(property.toString());
    }
    Collection<String> stringThis = new ArrayList<>();
    for (Property property : properties) {
      stringThis.add(property.toString());
    }
    for (String stringProperty : stringThis) {
      if (!stringOther.contains(stringProperty)) {
        //TODO: Replace System.out with proper logging
        System.out.println("Error In This: " + stringProperty.toString());
        return false;
      }
    }
    for (String stringProperty : stringOther) {
      if (!stringThis.contains(stringProperty)) {
        //TODO: Replace System.out with proper logging
        System.out.println("Error In Other: " + stringProperty.toString());
        return false;
      }
    }
    return true;
  }


}
