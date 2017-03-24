package scadinspect.data.scaddoc.parser;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;
import java.util.HashSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scadinspect.data.scaddoc.Module;
import scadinspect.data.scaddoc.properties.MultiProperty;
import scadinspect.data.scaddoc.properties.PairProperty;
import scadinspect.data.scaddoc.properties.Property;
import scadinspect.data.scaddoc.properties.SingleProperty;


/**
 * @author simon, tom on 3/23/17.
 */
class PropertyParserTest {

  PropertyParser propertyParser;
  String content;

  @Test
  void parseModule() {
    content = "/**"
        + "* @title Module_1"
        + "* @price 100~EUR"
        + "* @material wood; metal"
        + "* @comment So könnte das Ganze funktionieren..."
        + "*/";
    propertyParser = new PropertyParser(content);
    Module output = new Module();
    output.addProperty(new SingleProperty<>("title", "Module_1"));
    output.addProperty(new PairProperty<>("price", "100", "EUR"));
    output.addProperty(new MultiProperty<>("material", "wood", "metal"));
    output.addProperty(new SingleProperty<>("comment", "So könnte das Ganze funktionieren..."));
    System.out.println(propertyParser.parseModules());
    Collection<Property> parsed = propertyParser.parseModules().iterator().next().getProperties();
    Collection<String> stringParsed = new HashSet<>();
    for (Property property : parsed) {
      stringParsed.add(property.toString());
    }
    Collection<String> stringOutput = new HashSet<>();
    for (Property property : output.getProperties()) {
      stringOutput.add(property.toString());
    }
    boolean isEqual = true;
    for (String propertyString : stringOutput) {
      isEqual &= stringParsed.contains(propertyString);
    }
    assertEquals(true, isEqual);
  }
}