package scadinspect.data.scaddoc.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;
import java.util.HashSet;
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

  @Test
  void parseEmptyModule() {
    content = " ";
    propertyParser = new PropertyParser(content);
    Collection<Module> expected=new HashSet<>();
    Collection<Module> parsed = propertyParser.parseModules();

    assertEquals(expected, parsed);
  }

  @Test
  void sampleFile() {
    Collection<Module> modules=new HashSet<>();
    Module wheel = new Module();
    wheel.addProperty(new SingleProperty<>("part", "Wheel"));
    wheel.addProperty(new PairProperty<>("price", "100", "EUR"));
    wheel.addProperty(new SingleProperty<>("amount", "4"));
    wheel.addProperty(new PairProperty<>("weight", "12", "kg"));
    wheel.addProperty(new MultiProperty<>("materials", "Rubber", "Aluminium"));
    wheel.addProperty(new SingleProperty<>("url", "https://example.com"));

    Module motor = new Module();
    motor.addProperty(new SingleProperty<>("part", "Motor"));
    motor.addProperty(new PairProperty<>("price", "1000", "USD"));
    motor.addProperty(new SingleProperty<>("amount", "1"));
    motor.addProperty(new PairProperty<>("weight", "200", "kg"));
    motor.addProperty(new SingleProperty<>("materials", "Steel"));
    motor.addProperty(new SingleProperty<>("url", "https://example.com"));

    modules.add(motor);
    modules.add(wheel);

    String testFile="// logo.scad - Basic example of module, top-level variable and $fn usage\n"
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

    JsonExport exporter=new JsonExport();
    assertEquals(exporter.getJson(parsed),exporter.getJson(modules));

   // assertEquals(modules, parsed);
  }
}