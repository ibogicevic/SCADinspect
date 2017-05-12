package scadinspect.parser.ast;

import java.util.Collections;
import java.util.List;

/**
 * Created by felix on 22.04.17.
 */
public class ModuleIdNode extends ASTNode {

  public final String id;

  protected ModuleIdNode(NodeType type,
      List<ASTNode> children, String id) {
    super(type, children);
    this.id = id;

  }

  public static ModuleIdNode createId(String id) {
    return new ModuleIdNode(Types.ID, Collections.emptyList(), id);
  }

  public static ModuleIdNode createFor() {
    return new ModuleIdNode(Types.FOR, Collections.emptyList(), null);
  }

  public static ModuleIdNode createLet() {
    return new ModuleIdNode(Types.LET, Collections.emptyList(), null);
  }

  public static ModuleIdNode createAssert() {
    return new ModuleIdNode(Types.ASSERT, Collections.emptyList(), null);
  }

  public static ModuleIdNode createEcho() {
    return new ModuleIdNode(Types.ECHO, Collections.emptyList(), null);
  }

  public static ModuleIdNode createEach() {
    return new ModuleIdNode(Types.EACH, Collections.emptyList(), null);
  }

  public enum Types implements NodeType {
    ID,
    FOR,
    LET,
    ASSERT,
    ECHO,
    EACH
  }
}
