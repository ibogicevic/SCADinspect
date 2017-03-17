package scadinspect.data.scaddoc.properties;

import scadinspect.data.scaddoc.properties.helper.Pair;

/**
 * Created by richteto on 17.03.2017.
 */
public class PairProperty implements Property {

  private String key;
  private Pair value;

  public PairProperty(String key, Pair value) {
    this.key = key;
    this.value = value;
  }

  @Override
  public String getKey() {
    return key;
  }

  @Override
  public Pair getValue() {
    return value;
  }

  @Override
  public String toString() {
    return getKey() + ": " + getValue();
  }
}
