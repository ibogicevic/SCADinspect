package scadinspect.data.scaddoc.properties;

/**
 * Created by til on 3/17/17.
 */
public abstract class SingleProperty implements Property {

  private String key;

  @Override
  public String getKey() {
    return key;
  }

  @Override
  public String toString() {
    return getKey() + ": " + getValue();
  }
}
