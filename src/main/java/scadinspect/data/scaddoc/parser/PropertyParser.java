package scadinspect.data.scaddoc.parser;

import java.util.Collection;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import scadinspect.data.scaddoc.Module;
import scadinspect.data.scaddoc.properties.MultiProperty;
import scadinspect.data.scaddoc.properties.PairProperty;
import scadinspect.data.scaddoc.properties.SingleProperty;

/**
 * Created by richteto on 23.03.2017.
 */
public class PropertyParser {

  private Collection<String> comments;

  private String commentPattern = "/\\*\\*.+\\*/";
  private String keyPattern = "(@~*\\w+)";
  private String contentPattern = ("([^@]*)");
  private Pattern comment = Pattern.compile(commentPattern);
  private Pattern property = Pattern.compile(keyPattern + contentPattern);

  public PropertyParser(String scadFile) {
    comments = new HashSet<>();
    Matcher commentMatcher = comment.matcher(scadFile);
    while (commentMatcher.find()) {
      comments.add(commentMatcher.group(0).replaceAll("\n|\\*|/|\\*", "").replaceAll("\\s", " "));
    }
  }

  public Collection<Module> parseModules() {
    Collection<Module> modules = new HashSet<>();

    for (String comment : comments) {
      Module module = new Module();
      Matcher propertyMatcher = property.matcher(comment);

      while (propertyMatcher.find()) {
        String key = propertyMatcher.group(1).replaceFirst("@", "").trim();
        String content = propertyMatcher.group(2).trim();
        // Check whether it is a pair property
        String[] pair = content.split("~");
        if (pair.length == 2) {
          module.addProperty(new PairProperty<>(key, pair[0], pair[1]));
        } else {
          // List Check
          String[] list = content.split(";\\s*");
          if (list.length > 1) {
            module.addProperty(new MultiProperty<>(key, list));
          } else {
            module.addProperty(new SingleProperty<>(key, content));
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