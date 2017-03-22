package scadinspect.data.scaddoc;

import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.Test;
import scadinspect.data.scaddoc.properties.MultiProperty;
import scadinspect.data.scaddoc.properties.PairProperty;
import scadinspect.data.scaddoc.properties.SingleProperty;
import scadinspect.data.scaddoc.properties.helper.Pair;

/**
 * Created by desyon on 3/17/17.
 */
class JsonExportTest {

  @Test
  void oneLine() {
    List<Module> modules = new LinkedList<>();
    Module mod1 = new Module();

    mod1.addProperty(new SingleProperty<>("part", "Wheel"));
    mod1.addProperty(new PairProperty("price", new Pair(100, "EUR")));
    mod1.addProperty(new SingleProperty<>("amount", 4));
    mod1.addProperty(new PairProperty("weight", new Pair(12, "kg")));
    mod1.addProperty(new MultiProperty<>("materials", "Rubber", "Aluminium"));
    mod1.addProperty(new SingleProperty<>("url", "https://example.com"));
    mod1.addProperty(new SingleProperty<>("<key>", "<value"));

    Module mod2 = new Module();

    mod2.addProperty(new SingleProperty<>("part", "Motor"));
    mod2.addProperty(new PairProperty("price", new Pair(1000, "USD")));
    mod2.addProperty(new SingleProperty<>("amount", 1));
    mod2.addProperty(new PairProperty("weight", new Pair(200, "kg")));
    mod2.addProperty(new SingleProperty<>("materials", "Steel"));
    mod2.addProperty(new SingleProperty<>("url", "https://example.com"));
    mod2.addProperty(new SingleProperty<>("<key>", "<value>"));

    modules.add(mod1);
    modules.add(mod2);
  }
}