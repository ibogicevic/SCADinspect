package scadinspect.parser.ast;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by felix on 22.04.17.
 */
public class VectorExprNode extends ASTNode {

  protected VectorExprNode(NodeType type, List<ASTNode> children) {
    super(type, children);
  }

  public static VectorExprNode createExpr(ExprNode expr) {
    return new VectorExprNode(Types.EXPR, Collections.singletonList(expr));
  }

  public static VectorExprNode createListComprehensionElements(ListComprehensionElementsNode list) {
    return new VectorExprNode(Types.LIST_COMPREHENSION_ELEMENTS, Collections.singletonList(list));
  }

  public static VectorExprNode createWithComma(VectorExprNode vector, OptionalCommasNode commas,
      ListComprehensionElementsOrExprNode list) {
    return new VectorExprNode(Types.WITH_COMMA, Arrays.asList(vector, commas, list));
  }

  public enum Types implements NodeType {
    EXPR,
    LIST_COMPREHENSION_ELEMENTS,
    WITH_COMMA
  }
}
