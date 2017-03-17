package scadinspect.data.scaddoc.properties.impl;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import scadinspect.data.scaddoc.properties.MultiProperty;

/**
 * Created by richteto on 17.03.2017.
 */
public class IntMultiProperty extends MultiProperty {

  private String key;
  private List<Long> values;

  public IntMultiProperty(String key, List<Long> values) {
    this.key = key;
    this.values = values;
  }

  public IntMultiProperty(String key, Long... values) {
    this.key = key;
    this.values = new LinkedList<>(Arrays.asList(values));
  }

  @Override
  public String getKey() {
    return key;
  }

  @Override
  public List<Long> getValue() {
    return values;
  }


}
