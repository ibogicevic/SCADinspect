package scadinspect.data.scaddoc.properties;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Created by desyon on 3/22/17.
 */
class MultiPropertyTest {

  @Test
  void multiPropertyToString() {
    assertEquals("key: [a, b]", new MultiProperty<>("key", "a", "b").toString());

  }

}