package scadinspect.data.scaddoc.properties;

import java.util.Arrays;
import java.util.List;

/**
 * Created by desyon on 3/17/17.
 */
public class MultiProperty<T> implements Property {

  private String key;
  private List<T> values;

  public MultiProperty(String key, List<T> values) {
    this.key = key;
    this.values = values;
  }

  public MultiProperty(String key, T... values) {
    this.key = key;
    this.values = Arrays.asList(values);
  }

  @Override
  public String getKey() {
    return key;
  }

  @Override
  public List<T> getValue() {
    return values;
  }

  @Override
  public String toString() {
    return getKey() + ": " + getValue().toString();
  }
}
