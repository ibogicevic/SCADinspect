package scadinspect.data.scaddoc.Properties;

import java.util.List;

/**
 * Created by richteto on 17.03.2017.
 */
public class RealMultiProperty implements Property {
  private String key;
  private List<Double> values;

  public RealMultiProperty(String key, List<Double> values) {
    this.key = key;
    this.values = values;
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
