package scadinspect.parser.ast;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Felix Stegmaier on 30.03.2017.
 */
public class InputNode extends ASTNode {

  public final String use;

  protected InputNode(NodeType type, List<ASTNode> children, String use) {
    super(type, children);
    this.use = use;
  }

  public static InputNode createEmpty() {
    return new InputNode(Types.EMPTY, Collections.emptyList(), null);
  }

  public static InputNode createUse(String use, InputNode input) {
    return new InputNode(Types.USE, Collections.singletonList(input), use);
  }

  public static InputNode createStatement(StatementNode statementNode, InputNode input) {
    return new InputNode(Types.STATEMENT, Arrays.asList(statementNode, input), null);
  }

  public enum Types implements NodeType {
    EMPTY,
    USE,
    STATEMENT
  }


}
