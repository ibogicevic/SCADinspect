package scadinspect.data.scaddoc.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import scadinspect.data.scaddoc.Module;
import scadinspect.data.scaddoc.properties.MultiProperty;
import scadinspect.data.scaddoc.properties.PairProperty;
import scadinspect.data.scaddoc.properties.SingleProperty;

/**
 * Property parser definition parsing a scadFile to the internal modules
 */
public class PropertyParser {

  private Collection<String> comments;

  // Parsing full comments starting with a /** and ending with a */
  private String commentRegex = "/\\*\\*(.|\\s)+?\\*/";
  // Parsing the key definition starting with a @. example: @material
  private String keyRegex = "(@\\w+)";
  // Parsing the content of the key (anything which is not an @)
  private String contentRegex = ("([^@]*)");

  private Pattern commentPattern = Pattern.compile(commentRegex);
  private Pattern propertyPattern = Pattern.compile(keyRegex + contentRegex);

  /**
   * Constructing a new property parser
   *
   * @param scadFile The file content to be parsed
   */
  public PropertyParser(String scadFile) {
    setScadFile(scadFile);
  }

  /**
   * Constructing a new property parser without file to be parsed
   */
  public PropertyParser() {
  }

  /**
   * Setting the scad file to a new input string
   *
   * @param scadFile The file content to be parsed
   */
  public void setScadFile(String scadFile) {
    Matcher commentMatcher = commentPattern.matcher(scadFile);
    comments = new HashSet<>();
    while (commentMatcher.find()) {
      //Removing all line breaks, comment start and ending signs and all *
      // Secondly replaces all whitespaces like tabs or spaces by a single white space
      comments.add(commentMatcher.group(0).replaceAll("\\r\\n?|\\*/|\\*|/\\*\\*", "")
          .replaceAll("\\s", " "));
    }
  }

  /**
   * Parsing the scad file content to a collection of internal modules
   *
   * @return Collection of internal modules, returns null if the parsable file is not set or does
   * not contain any parsable comment
   */
  public Collection<Module> parseModules() {
    if (comments == null) {
      return null;
    }
    Collection<Module> modules = new ArrayList<>();

    // For every found comment block
    for (String comment : comments) {
      Module module = new Module();
      Matcher propertyMatcher = propertyPattern.matcher(comment);

      // For every found property
      while (propertyMatcher.find()) {
        // Finding the key and removing the @ and unnecessary whitespaces
        String key = propertyMatcher.group(1).replaceFirst("@", "").trim();
        // Finding the content and removing unnecessary whitespaces
        String content = propertyMatcher.group(2).trim();
        // Check whether the property is a pair property (example: @cost: 200~EUR)
        String[] pair = content.split("~");
        if (pair.length == 2) {
          // Parsing content into double, integer or string
          try {
            module.addProperty(new PairProperty<>(key, Integer.parseInt(pair[0]), pair[1]));
          } catch (NumberFormatException e) {
            try {
              module.addProperty(new PairProperty<>(key, Double.parseDouble(pair[0]), pair[1]));
            } catch (NumberFormatException r) {
              module.addProperty(new PairProperty<>(key, pair[0], pair[1]));
            }
          }
        } else {
          // Check whether the property is a multi property
          String[] list = content.split(";\\s*");
          if (list.length > 1) {
            List<Object> castedList = new ArrayList<>();
            for (String elem : list) {
              elem = elem.trim();
              // Parsing content into double, integer or string
              try {
                castedList.add(Integer.parseInt(elem));
              } catch (NumberFormatException e) {
                try {
                  castedList.add(Double.parseDouble(elem));
                } catch (NumberFormatException r) {
                  castedList.add(elem);
                }
              }
            }
            module.addProperty(new MultiProperty<>(key, castedList));
          } else {
            // the property has to be a single property
            // Parsing content into double, integer or string
            try {
              module.addProperty(new SingleProperty<>(key, Integer.parseInt(content)));
            } catch (NumberFormatException e) {
              try {
                module.addProperty(new SingleProperty<>(key, Double.parseDouble(content)));
              } catch (NumberFormatException r) {
                module.addProperty(new SingleProperty<>(key, content));
              }
            }
          }
        }
      }
      if (!module.getProperties().isEmpty()) {
        modules.add(module);
      }
    }
    return modules;
  }
}