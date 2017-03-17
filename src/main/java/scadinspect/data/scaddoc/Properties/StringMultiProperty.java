package scadinspect.data.scaddoc.Properties;

import java.util.List;

/**
 * Created by richteto on 17.03.2017.
 */
public class StringMultiProperty implements Property {
  private String key;
  private List<String> values;

  public StringMultiProperty(String key, List<String> values) {
    this.key = key;
    this.values = values;
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
