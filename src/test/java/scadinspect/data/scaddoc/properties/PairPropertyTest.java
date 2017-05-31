package scadinspect.data.scaddoc.properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PairPropertyTest {

  /**
   * Tests the toString output.
   */
  @Test
  void pairPropertyToString() {
    assertEquals("key: 1 m", new PairProperty("key", 1, "m").toString());

  }
}