package scadinspect.data.scaddoc;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import scadinspect.data.scaddoc.parser.PropertyParser;
import scadinspect.data.scaddoc.properties.Property;

/**
 * The internal documentation of a single Scad file
 *
 * @author richteto on 30.03.2017.
 */
public class ScadDocuFile {

  private Path path;
  private Collection<Module> modules;

  /**
   * Creating a new ScadDocuFile
   *
   * @param path The path of the parsed file
   * @param modules The modules of the file
   */
  public ScadDocuFile(Path path, Collection<Module> modules) {
    this.path = path;
    this.modules = modules;
  }

  /**
   * Creating a new ScadDocuFile without Modules
   *
   * @param path The path of the parsed file
   */
  public ScadDocuFile(Path path) {
    this.path = path;
    this.modules = new ArrayList<>();
  }

  /**
   * Creating a new ScadDocuFile and parses the modules directly
   *
   * @param path The path of the parsed file
   * @param fileContent The file content of the file to be parsed
   */
  public ScadDocuFile(Path path, String fileContent) {
    this.path = path;
    this.modules = new PropertyParser(fileContent).parseModules();
  }

  /**
   * Adding Modules to the ScadDocuFile
   *
   * @param module The module to be added
   */
  public void addModule(Module module) {
    modules.add(module);
  }

  public Path getPath() {
    return path;
  }

  public Collection<Module> getModules() {
    return modules;
  }

  public Collection<String> getAllKeys() {
    Collection<String> keys = new HashSet<>();
    for(Module module : modules){
      for(Property property: module.getProperties()){
        if(!keys.contains(property.getKey())){
          keys.add(property.getKey());
        }
      }
    }
    return keys;
  }
}
