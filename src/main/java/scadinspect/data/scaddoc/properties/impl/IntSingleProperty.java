package scadinspect.data.scaddoc.properties.impl;

import scadinspect.data.scaddoc.properties.SingleProperty;

/**
 * Created by richteto on 17.03.2017.
 */
public class IntSingleProperty extends SingleProperty {

  private String key;
  private long value;

  public IntSingleProperty(String key, long value) {
    this.key = key;
    this.value = value;
  }

  @Override
  public String getKey() {
    return key;
  }

  @Override
  public Long getValue() {
    return value;
  }
}
