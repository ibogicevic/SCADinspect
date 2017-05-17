package scadinspect.parser.ast;

import java.util.Arrays;
import java.util.List;

/**
 * Created by felix on 22.04.17.
 */
public class IfStatementNode extends ASTNode {

  protected IfStatementNode(NodeType type, List<ASTNode> children) {
    super(type, children);
  }

  public static IfStatementNode createIf(ExprNode expr, ChildStatementNode childStatement) {
    return new IfStatementNode(Types.IF, Arrays.asList(expr, childStatement));
  }

  public enum Types implements NodeType {
    IF
  }
}
