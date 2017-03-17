package scadinspect.data.scaddoc.properties.impl;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import scadinspect.data.scaddoc.properties.MultiProperty;

/**
 * Created by richteto on 17.03.2017.
 */
public class StringMultiProperty extends MultiProperty {

  private String key;
  private List<String> values;

  public StringMultiProperty(String key, List<String> values) {
    this.key = key;
    this.values = values;
  }

  public StringMultiProperty(String key, String... values) {
    this.key = key;
    this.values = new LinkedList<>(Arrays.asList(values));
  }

  @Override
  public String getKey() {
    return key;
  }

  @Override
  public List<String> getValue() {
    return values;
  }
}
