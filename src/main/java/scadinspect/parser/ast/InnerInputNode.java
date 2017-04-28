package scadinspect.parser.ast;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by felix on 21.04.17.
 */
public class InnerInputNode extends ASTNode {

  public enum Types implements NodeType {
    EMPTY,
    STATEMENT
  }

  public static InnerInputNode createEmpty() {
    return new InnerInputNode(Types.EMPTY, Collections.emptyList());
  }

  public static InnerInputNode createStatement(StatementNode statement, InnerInputNode inner) {
    return new InnerInputNode(Types.STATEMENT, Arrays.asList(statement, inner));
  }

  protected InnerInputNode(NodeType type, List<ASTNode> children) {
    super(type, children);
  }
}
