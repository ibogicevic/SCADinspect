package scadinspect.data.scaddoc.parser;

/**
 * Created by richteto on 23.03.2017.
 */
public class ContentParser<T> {

  T value;

  public ContentParser(String input) {
    Class<?> classObj = (Class<?>) getClass().getTypeParameters()[0].getBounds()[0];

    if (classObj.isAssignableFrom(String.class)) {
      value = (T) input;
    } else if (classObj.isAssignableFrom(Integer.class)) {
      value = (T) Integer.valueOf(input);
    } else if (classObj.isAssignableFrom(Double.class)) {
      value = (T) Double.valueOf(input);
    } else {
      value = (T) input;
    }
  }

  public T getValue() {
    return value;
  }
}
