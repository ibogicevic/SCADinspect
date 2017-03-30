package scadinspect.data.scaddoc.properties;

import java.util.Arrays;
import java.util.List;

/**
 * Multiple value property definition like [1, 2, 3, 4]
 * as property with a named key and a list as value
 *
 * @author desyon on 3/17/17.
 */
public class MultiProperty<T> implements Property<List<T>> {

  private String key;
  private List<T> values;

  /**
   * Constructing a new multiple value property
   *
   * @param key The key name to store the values
   * @param values The collection of values to be stored
   */
  public MultiProperty(String key, List<T> values) {
    this.key = key;
    this.values = values;
  }

  /**
   * Constructing a new multiple value property
   *
   * @param key The key name to store the values
   * @param values The parameters of the collection of values to be stored
   */
  public MultiProperty(String key, T... values) {
    this(key, Arrays.asList(values));
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
