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
package data.properties;

/**
 * Single value property definition
 * as property with a named key and a single value
 */
public class SingleProperty<T> implements Property<T> {

  private final String key;
  private final T value;

  /**
   * Constructing a new single value property
   *
   * @param key The key name to store the values
   * @param value The value to be stored
   */
  public SingleProperty(String key, T value) {
    this.key = key;
    this.value = value;
  }

  @Override
  public String getKey() {
    return key;
  }

  @Override
  public T getValue() {
    return value;
  }

  /**
   * Returns the property as String in the format <key>: <value>
   *
   * @return the proper as as String in the given format
   */
  @Override
  public String toString() {
    return getKey() + ": " + getValue();
  }
}
