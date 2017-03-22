package scadinspect.data.scaddoc.properties;

/**
 * Single value property definition
 * as property with a named key and a single value
 *
 * @author Created by desyon on 3/17/17.
 */
public class SingleProperty<T> implements Property {

  private String key;
  private T value;

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
  public Object getValue() {
    return value;
  }

  @Override
  public String toString() {
    return getKey() + ": " + getValue();
  }
}
