package scadinspect.parser.ast;

import java.util.Collections;
import java.util.List;

/**
 * Created by felix on 22.04.17.
 */
public class ListComprehensionElementsPNode extends ASTNode {

  public enum Types implements NodeType {
    LIST_COMPREHENSION_ELEMENTS,
    PARENTHESES
  }

  public static ListComprehensionElementsPNode createList(ListComprehensionElementsNode list) {
    return new ListComprehensionElementsPNode(Types.LIST_COMPREHENSION_ELEMENTS, Collections.singletonList(list));
  }

  public static ListComprehensionElementsPNode createParentheses(ListComprehensionElementsNode list) {
    return new ListComprehensionElementsPNode(Types.PARENTHESES, Collections.singletonList(list));
  }

  protected ListComprehensionElementsPNode(NodeType type, List<ASTNode> children) {
    super(type, children);
  }
}
