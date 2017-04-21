package scadinspect.parser.ast.checkers;

import java.util.Collection;
import scadinspect.data.analysis.Issue;

/**
 * Created by felix on 22.04.17.
 */
public class CheckResult {

  private final Collection<Issue> issues;

  public CheckResult(Collection<Issue> issues) {
    this.issues = issues;
  }

  public boolean hasIssues() {
    return issues != null && !issues.isEmpty();
  }

}
