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
package data.properties.helper;

/**
 * Pair Definition for pair values like '10 EUR' or 'hundred USD'
 */
public class Pair<T> {

  private final T value;
  private final String metric;

  /**
   * Constructing a new pair value
   *
   * @param value The generic value of the pair
   * @param metric The string currency or metric of the value
   */
  public Pair(T value, String metric) {
    this.value = value;
    this.metric = metric;
  }

  /**
   * Returns the value of the pair. Can be of generic type
   *
   * @return value of the pair.
   */
  public T getValue() {
    return value;
  }

  /**
   * Returns the metric of the pair, e.g. a specific currency
   *
   * @return String of the metric of the pair
   */
  public String getMetric() {
    return metric;
  }

  @Override
  public String toString() {
    return value + " " + metric;
  }
}
