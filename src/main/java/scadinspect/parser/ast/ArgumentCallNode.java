package scadinspect.parser.ast;

import java.util.Collections;
import java.util.List;

/**
 * Created by felix on 22.04.17.
 */
public class ArgumentCallNode extends ASTNode{

  public final String id;

  public enum Types implements NodeType {
    EXPR,
    ID_EXPR
  }

  public static ArgumentCallNode createExpr(ExprNode expr) {
    return new ArgumentCallNode(Types.EXPR, Collections.singletonList(expr), null);
  }

  public static ArgumentCallNode createIdExpr(String id, ExprNode expr) {
    return new ArgumentCallNode(Types.ID_EXPR, Collections.singletonList(expr), id);
  }

  protected ArgumentCallNode(NodeType type, List<ASTNode> children, String id) {
    super(type, children);
    this.id = id;
  }
}
