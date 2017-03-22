package scadinspect.data.scaddoc.properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Created by desyon on 3/22/17.
 */
class SinglePropertyTest {

  @Test
  void singlePropertyToString() {
    assertEquals("key: value", new SingleProperty<>("key", "value").toString());

  }
}