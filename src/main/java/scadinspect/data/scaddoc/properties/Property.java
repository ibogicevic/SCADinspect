package scadinspect.data.scaddoc.properties;

/**
 * The interface of any Property
 * Every property contains of a pair of a key and a value
 *
 * @author richteto on 17.03.2017.
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
