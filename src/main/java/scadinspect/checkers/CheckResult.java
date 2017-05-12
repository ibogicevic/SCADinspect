package scadinspect.checkers;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import scadinspect.data.analysis.Issue;

/**
 * Created by felix on 22.04.17.
 *
 * Result of checking an ASTNode with a Checker.
 * Mainly includes the Issues that were found.
 * Is merged with other results of child ast nodes,
 * see SpecialVariable checker for more info.
 *
 * @see scadinspect.checkers.SpecialVariablesChecker
 *
 * This might be changed or subclassed to adopt to checkers, that require more state info.
 */
public class CheckResult {

  private final Collection<Issue> issues;

  public CheckResult(Collection<Issue> issues) {
    this.issues = issues;
  }

  public static CheckResult mergeAll(Stream<CheckResult> all) {
    return new CheckResult(
        all.flatMap(o -> o.getIssues().stream())
            .collect(Collectors.toList())
    );
  }

  public Collection<Issue> getIssues() {
    return issues;
  }

}
