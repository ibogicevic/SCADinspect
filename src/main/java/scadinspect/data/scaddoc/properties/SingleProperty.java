package scadinspect.data.scaddoc.properties;

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
