package scadinspect.data.scaddoc.parser;

import com.sun.org.apache.xpath.internal.operations.Mod;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jdk.nashorn.internal.runtime.Debug;
import scadinspect.data.scaddoc.Module;
import scadinspect.data.scaddoc.properties.MultiProperty;
import scadinspect.data.scaddoc.properties.PairProperty;
import scadinspect.data.scaddoc.properties.SingleProperty;

/**
 * Created by richteto on 23.03.2017.
 */
public class PropertyParser {

  private String comment;

  private String keyPattern = "(@~*\\w+)";
  private String contentPattern = ("([^@]*)");
  private Pattern property = Pattern.compile(keyPattern + contentPattern);
  private Matcher matcher;

  public PropertyParser(String comment) {
    this.comment = comment.replaceAll("\n|\\*|/|\\*", "").replaceAll("\\s", " ");
    this.matcher = property.matcher(this.comment);
  }

  public Module parseModule() {
    Module module = new Module();
    while (matcher.find()) {
      System.out.println(matcher.group(0));
      String key = matcher.group(1).replaceFirst("@", "").trim();
      System.out.println(key);
      String content = matcher.group(2).trim();
      System.out.println(content);
      //Check whether it is a pair property
      String[] pair = content.split("~");
      if (pair.length == 2) {
        module.addProperty(new PairProperty(key, pair[0], pair[1]));
      } else {
        //List Check
        String[] list = content.split(";\\s*");
        if (list.length > 1) {
          module.addProperty(new MultiProperty(key, list));
        } else {
          module.addProperty(new SingleProperty(key, content));
        }
      }
    }
    return module;
  }
}