package scadinspect.parser.ast;

import java.util.Collections;
import java.util.List;

/**
 * Created by felix on 22.04.17.
 */
public class ArgumentDeclNode extends ASTNode {

  public final String id;

  protected ArgumentDeclNode(NodeType type, List<ASTNode> children, String id) {
    super(type, children);
    this.id = id;
  }

  public static ArgumentDeclNode createId(String id) {
    return new ArgumentDeclNode(Types.ID, Collections.emptyList(), id);
  }

  public static ArgumentDeclNode createIdExpr(String id, ExprNode expr) {
    return new ArgumentDeclNode(Types.ID_EXPR, Collections.singletonList(expr), id);
  }

  public enum Types implements NodeType {
    ID,
    ID_EXPR
  }
}
