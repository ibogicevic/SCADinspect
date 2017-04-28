package scadinspect.parser.ast.checkers;

import java.util.Collections;
import scadinspect.data.analysis.Issue;
import scadinspect.data.analysis.Issue.issueType;
import scadinspect.parser.ast.ASTNode;

/**
 * Created by Felix Stegmaier on 23.04.2017.
 */
public class ExampleChecker implements Checker {

  //TODO this is just a test, it does nothing of use

  @Override
  public CheckResult check(ASTNode astNode, CheckState state) {
    if (state.nestLevel > 5) {
      return new CheckResult(Collections.singletonList(new Issue(issueType.WARNING, null, 0, "W-000-test", "Nesting > 5")));
    } else {
      CheckState nextState = state.increaseNestLevelBy(1);
      return CheckResult.mergeAll(astNode.children.stream().map(n -> this.check(n, nextState)));
    }
  }
}
