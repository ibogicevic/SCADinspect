package scadinspect.parser.ast;

import java.util.Collections;
import java.util.List;

/**
 * Created by felix on 22.04.17.
 */
public class ListComprehensionElementsOrExprNode extends ASTNode{

  public enum Types implements NodeType {
    LIST_COMPREHENSION_ELEMENTS_P,
    EXPR
  }

  public static ListComprehensionElementsOrExprNode createListP(
      ListComprehensionElementsPNode list) {
    return new ListComprehensionElementsOrExprNode(Types.LIST_COMPREHENSION_ELEMENTS_P,
        Collections.singletonList(list));
  }

  public static ListComprehensionElementsOrExprNode createExpr(ExprNode expr) {
    return new ListComprehensionElementsOrExprNode(Types.EXPR, Collections.singletonList(expr));
  }

  protected ListComprehensionElementsOrExprNode(NodeType type, List<ASTNode> children) {
    super(type, children);
  }
}
