package scadinspect.data.scaddoc.Properties;

/**
 * Created by richteto on 17.03.2017.
 */
public class RealSingleProperty implements Property {
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
