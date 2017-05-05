package scadinspect.parser.ast;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by felix on 22.04.17.
 */
public class ListComprehensionElementsNode extends ASTNode {

  public enum Types implements NodeType {
    LET,
    EACH,
    FOR,
    FOR_WITH_SEMICOLONS,
    IF,
    IF_ELSE
  }

  public static ListComprehensionElementsNode createLet(ArgumentsCallNode args,
      ListComprehensionElementsPNode list) {
    return new ListComprehensionElementsNode(Types.LET, Arrays.asList(args, list));
  }
  public static ListComprehensionElementsNode createEach(ListComprehensionElementsOrExprNode list) {
    return new ListComprehensionElementsNode(Types.EACH, Collections.singletonList(list));
  }
  public static ListComprehensionElementsNode createFor(ArgumentsCallNode args,
      ListComprehensionElementsOrExprNode list) {
    return new ListComprehensionElementsNode(Types.FOR, Arrays.asList(args, list));
  }
  public static ListComprehensionElementsNode createForWithSemicolons(ArgumentsCallNode args1,
      ExprNode expr, ArgumentsCallNode args2, ListComprehensionElementsOrExprNode list) {
    return new ListComprehensionElementsNode(Types.FOR_WITH_SEMICOLONS,
        Arrays.asList(args1, expr, args2, list));
  }
  public static ListComprehensionElementsNode createIf(ExprNode expr,
      ListComprehensionElementsOrExprNode list) {
    return new ListComprehensionElementsNode(Types.IF, Arrays.asList(expr, list));
  }
  public static ListComprehensionElementsNode createIfElse(ExprNode expr,
      ListComprehensionElementsOrExprNode list1, ListComprehensionElementsOrExprNode list2) {
    return new ListComprehensionElementsNode(Types.IF_ELSE, Arrays.asList(expr, list1, list2));
  }
  protected ListComprehensionElementsNode(NodeType type, List<ASTNode> children) {
    super(type, children);
  }
}
