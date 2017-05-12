package scadinspect.parser.ast;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by felix on 22.04.17.
 */
public class IfElseStatementNode extends ASTNode {

  protected IfElseStatementNode(NodeType type, List<ASTNode> children) {
    super(type, children);
  }

  public static IfElseStatementNode createIf(IfStatementNode ifStatement) {
    return new IfElseStatementNode(Types.IF, Collections.singletonList(ifStatement));
  }

  public static IfElseStatementNode createIfElse(IfStatementNode ifStatement,
      ChildStatementNode childStatement) {
    return new IfElseStatementNode(Types.IF_ELSE, Arrays.asList(ifStatement, childStatement));
  }

  public enum Types implements NodeType {
    IF,
    IF_ELSE
  }
}
