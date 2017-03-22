package scadinspect.data.scaddoc.properties;

/**
 * Created by desyon on 3/17/17.
 */
public class SingleProperty<T> implements Property {

  private String key;
  private T value;

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
