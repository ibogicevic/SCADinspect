package scadinspect.parser;

import java.io.File;
import java.util.Collection;
import java_cup.runtime.Symbol;
import scadinspect.data.analysis.Issue;

/**
 * Created by felix on 23.03.17.
 *
 * Data bag class returned by
 *
 * @see scadinspect.parser.Parser#parse(File)
 *
 * Contains the ParseTree, the original root parseSymbol and a list of Issues that arrised during
 * the parsing
 */
public class ParserResult {

  private final boolean success;
  private final ParseTree parseTree;
  private final Symbol parseSymbol;
  private final Collection<Issue> issues;

  public ParserResult(boolean success, ParseTree parseTree, Symbol parseSymbol, Collection<Issue> issues) {
    this.success = success;
    this.parseTree = parseTree;
    this.parseSymbol = parseSymbol;
    this.issues = issues;
  }

  public boolean isSuccess() {
    return success;
  }

  public ParseTree getParseTree() {
    return parseTree;
  }

  public Collection<Issue> getIssues() {
    return issues;
  }

  @Override
  public String toString() {
    return "ParserResult{" +
        "success=" + success +
        ", parseTree=" + parseTree +
        ", parseSymbol=" + parseSymbol +
        ", issues=" + issues +
        '}';
  }
}
