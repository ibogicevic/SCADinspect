package scadinspect.data.scaddoc.properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Created by desyon on 3/22/17.
 */
class PairPropertyTest {

  @Test
  void pairPropertyToString() {
    assertEquals("key: {value: 1, metric: m}", new PairProperty("key", 1, "m").toString());

  }
}