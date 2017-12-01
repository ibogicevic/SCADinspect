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
 * The interface of any Property
 * Every property contains of a pair of a key and a value
 */
public interface Property<T> {

  /**
   * Returns the key of the property
   *
   * @return the key of the property
   */
  String getKey();

  /**
   * Returns the value of the property
   *
   * @return the value of the property
   */
  T getValue();
}
