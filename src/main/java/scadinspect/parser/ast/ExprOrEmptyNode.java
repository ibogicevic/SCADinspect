package scadinspect.parser.ast;

import java.util.Collections;
import java.util.List;

/**
 * Created by felix on 22.04.17.
 */
public class ExprOrEmptyNode extends ASTNode{

  public enum Types implements NodeType {
    EMPTY,
    EXPR
  }

  public static ExprOrEmptyNode createEmpty() {
    return new ExprOrEmptyNode(Types.EMPTY, Collections.emptyList());
  }

  public static ExprOrEmptyNode createExpr(ExprNode expr) {
    return new ExprOrEmptyNode(Types.EXPR, Collections.singletonList(expr));
  }

  protected ExprOrEmptyNode(NodeType type, List<ASTNode> children) {
    super(type, children);
  }
}
