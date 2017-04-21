package scadinspect.parser.ast;

import java.util.Arrays;
import java.util.List;

/**
 * Created by felix on 22.04.17.
 */
public class IfStatementNode extends ASTNode {

  public enum Types implements NodeType {
    IF
  }

  public static IfStatementNode createIf(ExprNode expr, ChildStatementNode childStatement) {
    return new IfStatementNode(Types.IF, Arrays.asList(expr, childStatement));
  }

  protected IfStatementNode(NodeType type, List<ASTNode> children) {
    super(type, children);
  }
}
