package scadinspect.data.scaddoc.properties.helper;

/**
 * Pair Definition for pair values like '10 EUR' or 'hundred USD'
 *
 * @author Created by richteto on 17.03.2017
 */
public class Pair<T> {

  private T value;
  private String metric;

  /**
   * Constructing a new pair value
   *
   * @param value The generic value of the pair
   * @param metric The string currency or metric of the value
   */
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
    return "{value: " + value + ", metric: " + metric + "}";
  }
}
