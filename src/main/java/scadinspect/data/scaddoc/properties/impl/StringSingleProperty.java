package scadinspect.data.scaddoc.properties.impl;

import scadinspect.data.scaddoc.properties.SingleProperty;

/**
 * Created by richteto on 17.03.2017.
 */
public class StringSingleProperty extends SingleProperty {

  private String key;
  private String value;

  public StringSingleProperty(String key, String value) {
    this.key = key;
    this.value = value;
  }

  @Override
  public String getKey() {
    return key;
  }

  @Override
  public String getValue() {
    return value;
  }
}
