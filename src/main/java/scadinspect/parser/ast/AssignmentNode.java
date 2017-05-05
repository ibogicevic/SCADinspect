package scadinspect.parser.ast;

import java.util.Collections;
import java.util.List;

/**
 * Created by felix on 21.04.17.
 */
public class AssignmentNode extends ASTNode {

  public final String id;

  public enum Types implements NodeType {
    ASSIGNMENT
  }

  public static AssignmentNode createAssignment(String id, ExprNode expr) {
    return new AssignmentNode(Types.ASSIGNMENT, Collections.singletonList(expr), id);
  }

  protected AssignmentNode(NodeType type, List<ASTNode> children, String id) {
    super(type, children);
    this.id = id;
  }
}
