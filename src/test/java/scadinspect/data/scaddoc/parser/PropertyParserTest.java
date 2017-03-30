package scadinspect.data.scaddoc.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;
import scadinspect.data.scaddoc.JsonExport;
import scadinspect.data.scaddoc.Module;
import scadinspect.data.scaddoc.properties.MultiProperty;
import scadinspect.data.scaddoc.properties.PairProperty;
import scadinspect.data.scaddoc.properties.SingleProperty;

/**
 * @author simon, tom on 3/23/17.
 */
class PropertyParserTest {

  PropertyParser propertyParser;
  String content;

  /**
   * Test a single Module
   */
  @Test
  void parseOneModule() {
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

    Module parsed = propertyParser.parseModules().iterator().next();

    assertEquals(output, parsed);
  }

  /**
   * Test an empty Module
   */
  @Test
  void parseEmptyModule() {
    content = " ";
    propertyParser = new PropertyParser(content);
    Collection<Module> expected = new ArrayList<>();
    Collection<Module> parsed = propertyParser.parseModules();

    assertEquals(expected, parsed);
  }

  /**
   * Test against the defined example file
   */
  @Test
  void sampleFile() {
    Collection<Module> modules = new ArrayList<>();
    Module wheel = new Module();
    wheel.addProperty(new SingleProperty<>("part", "Wheel"));
    wheel.addProperty(new PairProperty<>("price", 100, "EUR"));
    wheel.addProperty(new SingleProperty<>("amount", 4));
    wheel.addProperty(new PairProperty<>("weight", 12, "kg"));
    wheel.addProperty(new MultiProperty<>("materials", "Rubber", "Aluminium"));
    wheel.addProperty(new SingleProperty<>("url", "https://example.com"));

    Module motor = new Module();
    motor.addProperty(new SingleProperty<>("part", "Motor"));
    motor.addProperty(new PairProperty<>("price", 1000, "USD"));
    motor.addProperty(new SingleProperty<>("amount", 1));
    motor.addProperty(new PairProperty<>("weight", 200, "kg"));
    motor.addProperty(new SingleProperty<>("materials", "Steel"));
    motor.addProperty(new SingleProperty<>("url", "https://example.com"));

    modules.add(motor);
    modules.add(wheel);

    String testFile = "// logo.scad - Basic example of module, top-level variable and $fn usage\n"
        + "\n"
        + "Logo(50);\n"
        + "\n"
        + "/** few\n"
        + "femfiweocm\n"
        + "@part Wheel\n"
        + "@price 100~EUR\n"
        + "@amount 4\n"
        + "@weight 12~kg\n"
        + "@materials Rubber; Aluminium\n"
        + "@url https://example.com\n"
        + "*/\n"
        + "module wheel(size=50, $fn=100) {\n"
        + "    // Temporary variables\n"
        + "    hole = size/2;\n"
        + "    cylinderHeight = size * 1.25;\n"
        + "\n"
        + "    // One positive object (sphere) and three negative objects (cylinders)\n"
        + "    difference() {\n"
        + "        sphere(d=size);\n"
        + "        /*\n"
        + "        block comment\n"
        + "        */\n"
        + "        cylinder(d=hole, h=cylinderHeight, center=true);\n"
        + "        // The '#' operator highlights the object\n"
        + "        #rotate([90, 0, 0]) cylinder(d=hole, h=cylinderHeight, center=true);\n"
        + "        rotate([0, 90, 0]) cylinder(d=hole, h=cylinderHeight, center=true);\n"
        + "    }\n"
        + "}\n"
        + "/**\n"
        + "@part Motor\n"
        + "@price 1000~USD\n"
        + "@amount 1\n"
        + "@weight 200~kg\n"
        + "@materials Steel\n"
        + "@url https://example.com\n"
        + "*/\n"
        + "echo(version=version());\n"
        + "// Written by Clifford Wolf <clifford@clifford.at> and Marius\n"
        + "// Kintel <marius@kintel.net>\n"
        + "//\n"
        + "// To the extent possible under law, the author(s) have dedicated all\n"
        + "// copyright and related and neighboring rights to this software to the\n"
        + "// public domain worldwide. This software is distributed without any\n"
        + "// warranty.\n"
        + "//\n"
        + "// You should have received a copy of the CC0 Public Domain\n"
        + "// Dedication along with this software.\n"
        + "// If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.\n"
        + "\n";
    propertyParser = new PropertyParser(testFile);
    Collection<Module> parsed = propertyParser.parseModules();

    JsonExport exporter = new JsonExport();
    JSONArray test = exporter.getJsonArray(modules);

    assertEquals(exporter.getJsonArray(modules).toString(2),
        exporter.getJsonArray(parsed).toString(2));

    // assertEquals(modules, parsed);
  }

  /**
   * Test parsing of Pair Property with type Int Double and String
   */
  @Test
  void parsePairProperty() {
    content = "/**"
        + "* @float 0.1~a"
        + "* @int 1~b"
        + "* @string a~c"
        + "*/";
    propertyParser = new PropertyParser(content);

    Module output = new Module();

    output.addProperty(new PairProperty<>("float", 0.1, "a"));
    output.addProperty(new PairProperty<>("int", 1, "b"));
    output.addProperty(new PairProperty<>("string", "a", "c"));

    Module parsed = propertyParser.parseModules().iterator().next();

    assertEquals(output, parsed);
  }

  /**
   * Test parsing of Single Property with type Int Double and String
   */
  @Test
  void parseSingleProperty() {
    content = "/**"
        + "* @float 0.1"
        + "* @int 1"
        + "* @string a"
        + "*/";
    propertyParser = new PropertyParser(content);

    Module output = new Module();

    output.addProperty(new SingleProperty<>("float", 0.1));
    output.addProperty(new SingleProperty<>("int", 1));
    output.addProperty(new SingleProperty<>("string", "a"));

    Module parsed = propertyParser.parseModules().iterator().next();

    assertEquals(output, parsed);
  }

  /**
   * Test parsing of Multi Property with type Int Double and String
   */
  @Test
  void parseMultiProperty() {
    content = "/**"
        + "* @float 0.1;0.2"
        + "* @int 1;2"
        + "* @string     a    ; b  ;  c   "
        + "* @doubleInt 0.1 ; 1.0"
        + "* @mixed 0.1 ; 1          ;   c    "
        + "*/";
    propertyParser = new PropertyParser(content);

    Module output = new Module();

    output.addProperty(new MultiProperty<>("float", 0.1, 0.2));
    output.addProperty(new MultiProperty<>("int", 1, 2));
    output.addProperty(new MultiProperty<>("string", "a", "b", "c"));
    output.addProperty(new MultiProperty<>("doubleInt", 0.1, 1));
    output.addProperty(new MultiProperty<>("mixed", 0.1, 1, "c"));

    propertyParser = new PropertyParser(content);
    Collection<Module> parsed = propertyParser.parseModules();
    Collection<Module> expected = new ArrayList<>();
    expected.add(output);
    JsonExport exporter = new JsonExport();
    assertEquals(exporter.getJson(expected), exporter.getJson(parsed));

  }

  /**
   * Test what happens with no file
   */
  @Test
  void wrongInitialisation() {
    propertyParser = new PropertyParser();
    assertEquals(null, propertyParser.parseModules());
  }

  /**
   * Test what happens with added file
   */
  @Test
  void alternativeConstructor() {
    content = "/**"
        + "* @float 0.1;0.2"
        + "* @int 1;2"
        + "* @string     a    ; b  ;  c   "
        + "* @doubleInt 0.1 ; 1.0"
        + "* @mixed 0.1 ; 1          ;   c    "
        + "*/";
    propertyParser = new PropertyParser();
    propertyParser.setScadFile(content);
    Module output = new Module();

    output.addProperty(new MultiProperty<>("float", 0.1, 0.2));
    output.addProperty(new MultiProperty<>("int", 1, 2));
    output.addProperty(new MultiProperty<>("string", "a", "b", "c"));
    output.addProperty(new MultiProperty<>("doubleInt", 0.1, 1));
    output.addProperty(new MultiProperty<>("mixed", 0.1, 1, "c"));

    propertyParser = new PropertyParser(content);
    Collection<Module> parsed = propertyParser.parseModules();
    Collection<Module> expected = new ArrayList<>();
    expected.add(output);
    JsonExport exporter = new JsonExport();
    assertEquals(exporter.getJson(expected), exporter.getJson(parsed));
  }
}