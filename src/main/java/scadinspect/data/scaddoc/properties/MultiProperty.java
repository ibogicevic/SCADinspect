package scadinspect.data.scaddoc.properties;

/**
 * Created by desyon on 3/17/17.
 */
public abstract class MultiProperty implements Property {

  @Override
  public String toString() {
    return getKey() + ": " + getValue().toString();
  }
}
