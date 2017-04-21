package scadinspect.parser.ast;

import java.util.Collections;
import java.util.List;

/**
 * Created by felix on 22.04.17.
 */
public class ChildStatementsNode extends ASTNode {

  public enum Types implements NodeType {
    EMPTY,
    CHILD_STATEMENT,
    ASSIGNMENT
  }

  public static ChildStatementsNode createEmpty() {
    return new ChildStatementsNode(Types.EMPTY, Collections.emptyList());
  }

  public static ChildStatementsNode createChildStatement(ChildStatementNode childStatement) {
    return new ChildStatementsNode(Types.CHILD_STATEMENT,
        Collections.singletonList(childStatement));
  }

  public static ChildStatementsNode createAssignment(AssignmentNode assignment) {
    return new ChildStatementsNode(Types.ASSIGNMENT, Collections.singletonList(assignment));
  }

  protected ChildStatementsNode(NodeType type, List<ASTNode> children) {
    super(type, children);
  }
}
