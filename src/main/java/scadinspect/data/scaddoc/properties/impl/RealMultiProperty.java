package scadinspect.data.scaddoc.properties.impl;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import scadinspect.data.scaddoc.properties.MultiProperty;

/**
 * Created by richteto on 17.03.2017.
 */
public class RealMultiProperty extends MultiProperty {

  private String key;
  private List<Double> values;

  public RealMultiProperty(String key, List<Double> values) {
    this.key = key;
    this.values = values;
  }

  public RealMultiProperty(String key, Double... values) {
    this.key = key;
    this.values = new LinkedList<>(Arrays.asList(values));
  }

  @Override
  public String getKey() {
    return key;
  }

  @Override
  public List<Double> getValue() {
    return values;
  }
}
