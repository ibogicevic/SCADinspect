package scadinspect.data.scaddoc.properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * @author desyon on 3/22/17.
 */
class MultiPropertyTest {

  /**
   * Tests the toString output.
   */
  @Test
  void multiPropertyToString() {
    assertEquals("key: [a, b]", new MultiProperty<>("key", "a", "b").toString());
  }

  /**
   * Tests the toString output created from a list.
   */
  @Test
  void multiPropertyFromList() {
    List<String> values = new LinkedList<String>();
    values.add("a");
    values.add("b");
    values.add("c");
    assertEquals("key: [a, b, c]", new MultiProperty<>("key", values).toString());
  }
}