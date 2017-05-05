package scadinspect.checkers;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import scadinspect.data.analysis.Issue;

/**
 * Created by felix on 22.04.17.
 */
public class CheckResult {

  private final Collection<Issue> issues;

  public CheckResult(Collection<Issue> issues) {
    this.issues = issues;
  }

  public Collection<Issue> getIssues() {
    return issues;
  }

  public CheckResult mergeWith(Collection<CheckResult> others) {
    return new CheckResult(
        Stream.concat(this.getIssues().stream(), others.stream().flatMap(o -> o.getIssues().stream()))
            .collect(Collectors.toList())
    );
  }

  public static CheckResult mergeAll(Stream<CheckResult> all) {
    return new CheckResult(
        all.flatMap(o -> o.getIssues().stream())
           .collect(Collectors.toList())
    );
  }

}
