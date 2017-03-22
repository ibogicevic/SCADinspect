package scadinspect.data.scaddoc.properties.helper;

/**
 * Created by richteto on 17.03.2017.
 */
public class Pair<T> {
  private T value;
  private  String metric;

  public Pair(T value, String metric) {
    this.value = value;
    this.metric = metric;
  }

  public T getValue() {
    return value;
  }

  public String getMetric() {
    return metric;
  }

  @Override
  public String toString() {
    return "{value: "+value+", metric: "+metric+"}";
  }
}
