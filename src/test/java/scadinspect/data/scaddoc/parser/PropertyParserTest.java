package scadinspect.data.scaddoc.parser;

import static org.junit.jupiter.api.Assertions.*;

import com.sun.org.apache.xpath.internal.operations.Mod;
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
 * Created by richteto on 23.03.2017.
 */
class PropertyParserTest {

  PropertyParser propertyParser;
  String content = "/**"
      + "* @title Module_1"
      + "* @price 100~EUR"
      + "* @material wood; metal"
      + "* @comment So könnte das Ganze funktionieren..."
      + "*/";

  @BeforeEach
  void instantiate() {
    propertyParser = new PropertyParser(content);
  }

  @Test
  void parseModule() {
    Module output = new Module();
    output.addProperty(new SingleProperty("title", "Module_1"));
    output.addProperty(new PairProperty("price", "100", "EUR"));
    output.addProperty(new MultiProperty("material", "wood", "metal"));
    output.addProperty(new SingleProperty("comment", "So könnte das Ganze funktionieren..."));
    Collection<Property> parsed = propertyParser.parseModule().getProperties();
    Collection<String> stringparsed = new HashSet<>();
    for (Property property : parsed) {
      stringparsed.add(property.toString());
    }
    Collection<String> stringOutput = new HashSet<>();
    for (Property property : output.getProperties()) {
      stringOutput.add(property.toString());
    }
    boolean isEqual = true;
    for (String propertyString : stringOutput) {
      isEqual &= stringparsed.contains(propertyString);
    }
    assertEquals(true, isEqual);
  }
}