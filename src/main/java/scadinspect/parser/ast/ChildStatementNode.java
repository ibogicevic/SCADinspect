package scadinspect.parser.ast;

import java.util.Collections;
import java.util.List;

/**
 * Created by felix on 22.04.17.
 */
public class ChildStatementNode extends ASTNode {

  public enum Types implements NodeType {
    SEMICOLON,
    CHILD_STATEMENTS,
    MODULE_INSTANTIATION
  }

  public static ChildStatementNode createSemicolon() {
    return new ChildStatementNode(Types.SEMICOLON, Collections.emptyList());
  }

  public static ChildStatementNode createChildStatements(ChildStatementsNode childStatements) {
    return new ChildStatementNode(Types.CHILD_STATEMENTS,
        Collections.singletonList(childStatements));
  }

  public static ChildStatementNode createModuleInstantiation(ModuleInstantiationNode module) {
    return new ChildStatementNode(Types.MODULE_INSTANTIATION, Collections.singletonList(module));
  }

  protected ChildStatementNode(NodeType type, List<ASTNode> children) {
    super(type, children);
  }
}
