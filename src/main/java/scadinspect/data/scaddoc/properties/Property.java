package scadinspect.data.scaddoc.properties;

/**
 * The interface of any Property
 * Every property contains of a pair of a key and a value
 *
 * @author Created by richteto on 17.03.2017.
 */
public interface Property<T> {

  public String getKey();

  public T getValue();
}
