package scadinspect.data.scaddoc.properties.impl;

import scadinspect.data.scaddoc.properties.SingleProperty;

/**
 * Created by richteto on 17.03.2017.
 */
public class RealSingleProperty extends SingleProperty {

  private String key;
  private double value;

  public RealSingleProperty(String key, double value) {
    this.key = key;
    this.value = value;
  }

  @Override
  public String getKey() {
    return key;
  }

  @Override
  public Double getValue() {
    return value;
  }
}
