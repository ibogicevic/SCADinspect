package scadinspect.parser.ast;

import java.util.Collections;
import java.util.List;

/**
 * Created by Felix Stegmaier on 30.03.2017.
 */
public class InputNode extends ASTNode {

  public final String use;

  public enum Types implements NodeType {
    EMPTY,
    USE,
    STATEMENT
  }

  public static InputNode createEmptyInput() {
    return new InputNode(Types.EMPTY, Collections.emptyList(), null);
  }

  public static InputNode createUseInput(String use) {
    return new InputNode(Types.USE, Collections.emptyList(), use);
  }

  public static InputNode createStatementInput(List<ASTNode> children) {
    return new InputNode(Types.STATEMENT, children, null);
  }

  protected InputNode(NodeType type, List<ASTNode> children, String use) {
    super(type, children);
    this.use = use;
  }


}
