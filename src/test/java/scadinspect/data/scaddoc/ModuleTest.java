package scadinspect.data.scaddoc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import scadinspect.data.scaddoc.properties.SingleProperty;

/**
 * Created by richteto on 30.03.2017.
 */
class ModuleTest {

  @Test
  void toStringTest() {
    Module module = new Module();
    module.addProperty(new SingleProperty<>("a", 123));
    assertEquals("{a: 123\n}", module.toString());
  }
}