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

import data.properties.helper.Pair;

/**
 * Pair property definition for pair values like '10 EUR' or 'hundred USD'
 * as property with a named key
 */
public class PairProperty<T> implements Property {

  private String key;
  private Pair<T> value;

  /**
   * Constructing a new pair property
   *
   * @param key The key name to store the pair
   * @param value The pair to be stored
   */
  public PairProperty(String key, Pair<T> value) {
    this.key = key;
    this.value = value;
  }

  /**
   * Constructing a new pair property
   *
   * @param key The key name to store the pair
   * @param value The generic value of the pair
   * @param metric The string currency or metric of the value
   */
  public PairProperty(String key, T value, String metric) {
    this(key, new Pair<>(value, metric));
  }

  @Override
  public String getKey() {
    return key;
  }

  @Override
  public Pair<T> getValue() {
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
