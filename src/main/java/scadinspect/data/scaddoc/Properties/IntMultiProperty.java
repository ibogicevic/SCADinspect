package scadinspect.data.scaddoc.Properties;

import java.util.List;

/**
 * Created by richteto on 17.03.2017.
 */
public class IntMultiProperty implements Property {
  private String key;
  private List<Long> values;

  public IntMultiProperty(String key, List<Long> values) {
    this.key = key;
    this.values = values;
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
