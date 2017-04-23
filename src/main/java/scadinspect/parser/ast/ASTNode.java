package scadinspect.parser.ast;

import java.util.List;
import scadinspect.parser.ast.checkers.CheckResult;
import scadinspect.parser.ast.checkers.CheckState;
import scadinspect.parser.ast.checkers.Checker;

/**
 * Created by Felix Stegmaier on 23.03.17.
 */
public abstract class ASTNode {

  //TODO implement checkable for this to get checkers

  public final NodeType type;
  public final List<ASTNode> children;

  protected ASTNode(NodeType type, List<ASTNode> children) {
    this.type = type;
    this.children = children;
  }

  public CheckResult check(Checker checker, CheckState state) {
    return checker.check(this, state);
  }

}
