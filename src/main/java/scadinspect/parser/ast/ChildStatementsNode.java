package scadinspect.parser.ast;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by felix on 22.04.17.
 */
public class ChildStatementsNode extends ASTNode {

  protected ChildStatementsNode(NodeType type, List<ASTNode> children) {
    super(type, children);
  }

  public static ChildStatementsNode createEmpty() {
    return new ChildStatementsNode(Types.EMPTY, Collections.emptyList());
  }

  public static ChildStatementsNode createChildStatement(ChildStatementsNode childStatements,
      ChildStatementNode childStatement) {
    return new ChildStatementsNode(Types.CHILD_STATEMENT,
        Arrays.asList(childStatement, childStatement));
  }

  public static ChildStatementsNode createAssignment(ChildStatementsNode childStatements,
      AssignmentNode assignment) {
    return new ChildStatementsNode(Types.ASSIGNMENT, Arrays.asList(childStatements, assignment));
  }

  public enum Types implements NodeType {
    EMPTY,
    CHILD_STATEMENT,
    ASSIGNMENT
  }
}
