package scadinspect.data.scaddoc.Properties;

import scadinspect.data.scaddoc.Properties.Helper.Pair;

/**
 * Created by richteto on 17.03.2017.
 */
public class PairProperty implements Property{

  private String key;
  private Pair value;

  public PairProperty(String key, Pair value) {
    this.key = key;
    this.value = value;
  }

  @Override
  public String getKey() {
    return null;
  }

  @Override
  public Pair getValue() {
    return null;
  }
}
