package scadinspect.data.scaddoc.properties.helper;

/**
 * Created by richteto on 17.03.2017.
 */
public class Pair {
  private double value;
  private  String metric;

  public Pair(double value, String metric) {
    this.value = value;
    this.metric = metric;
  }

  public double getValue() {
    return value;
  }

  public String getMetric() {
    return metric;
  }
}
