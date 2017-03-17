package scadinspect.data.scaddoc.Properties;

/**
 * Created by richteto on 17.03.2017.
 */
public class IntSingleProperty implements Property {
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
