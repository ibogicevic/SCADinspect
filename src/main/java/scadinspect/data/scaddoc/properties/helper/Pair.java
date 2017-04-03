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

  /**
   * Returns the value of the pair. Can be of generic type
   *
   * @return value of the pair.
   */
  public T getValue() {
    return value;
  }

  /**
   * Returns the metric of the pair, e.g. a specific currency
   *
   * @return String of the metric of the pair
   */
  public String getMetric() {
    return metric;
  }

  @Override
  public String toString() {
    return value + " " + metric;
  }
}
