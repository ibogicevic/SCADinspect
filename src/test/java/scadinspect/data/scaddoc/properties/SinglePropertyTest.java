package scadinspect.data.scaddoc.properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SinglePropertyTest {

  /**
   * Tests the toString output.
   */
  @Test
  void singlePropertyToString() {
    assertEquals("key: value", new SingleProperty<>("key", "value").toString());

  }
}