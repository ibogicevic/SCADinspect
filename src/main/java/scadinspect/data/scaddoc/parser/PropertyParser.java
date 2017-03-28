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
 *
 * @author richteto on 3/23/17.
 */
public class PropertyParser {

  private Collection<String> comments;

  private String commentRegex = "/\\*\\*(.|\\s)+?\\*/";
  private String keyRegex = "(@~*\\w+)";
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
      comments.add(commentMatcher.group(0).replaceAll("\\r\\n?|\\*/|\\*|/\\*\\*", "")
          .replaceAll("\\s", " "));
    }
  }

  /**
   * Parsing the scad file content to a collection of internal modules
   *
   * @return Collection of internal modules
   */
  public Collection<Module> parseModules() {
    if (comments == null) {
      return null;
    }
    Collection<Module> modules = new HashSet<>();

    for (String comment : comments) {
      Module module = new Module();
      Matcher propertyMatcher = propertyPattern.matcher(comment);

      while (propertyMatcher.find()) {
        String key = propertyMatcher.group(1).replaceFirst("@", "").trim();
        String content = propertyMatcher.group(2).trim();
        // Check whether it is a pair property
        String[] pair = content.split("~");
        if (pair.length == 2) {
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
          // List Check
          String[] list = content.split(";\\s*");
          if (list.length > 1) {
            List<Object> castedList = new ArrayList<>();
            for (String elem : list) {
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