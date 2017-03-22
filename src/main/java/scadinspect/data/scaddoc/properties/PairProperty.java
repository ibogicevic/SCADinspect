package scadinspect.data.scaddoc.properties;

import scadinspect.data.scaddoc.properties.helper.Pair;

/**
 * Pair property definition for pair values like '10 EUR' or 'hundred USD'
 * as property with a named key
 *
 * @author Created by richteto on 17.03.2017
 */
public class PairProperty<T> implements Property {

  private String key;
  private Pair value;

  /**
   * Constructing a new pair property
   *
   * @param key The key name to store the pair
   * @param value The pair to be stored
   */
  public PairProperty(String key, Pair value) {
    this.key = key;
    this.value = value;
  }

  /**
   * Constructing a new pair property
   *
   * @param key The key name to store the pair
   * @param value The generic value of the pair
   * @param metric The string currency or metric of the value
   */
  public PairProperty(String key, T value, String metric) {
    this(key, new Pair<>(value, metric));
  }

  @Override
  public String getKey() {
    return key;
  }

  @Override
  public Pair getValue() {
    return value;
  }

  @Override
  public String toString() {
    return getKey() + ": " + getValue();
  }
}
