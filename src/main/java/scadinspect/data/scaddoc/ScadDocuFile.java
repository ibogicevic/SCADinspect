package scadinspect.data.scaddoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
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
  public ScadDocuFile(Path path) throws IOException {
    this.path = path;
    this.modules = new PropertyParser(new String(Files.readAllBytes(path), "UTF-8")).parseModules();
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

  /**
   * Finds and returns all keys in all modules of one SCAD file
   *
   * @return all keys available
   */
  public Collection<String> getAllKeys() {
    Collection<String> keys = new ArrayList<>();
    for (Module module : modules) {
      for (Property property : module.getProperties()) {
        if (!keys.contains(property.getKey())) {
          keys.add(property.getKey());
        }
      }
    }
    return keys;
  }

  public Path getPath() {
    return path;
  }

  public Collection<Module> getModules() {
    return modules;
  }
}
