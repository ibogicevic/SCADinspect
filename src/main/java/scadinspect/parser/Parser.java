package scadinspect.parser;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Collection;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ScannerBuffer;
import java_cup.runtime.Symbol;
import scadinspect.data.analysis.Issue;
import scadinspect.parser.ast.ASTNode;
import scadinspect.parser.error.*;
import scadinspect.parser.generated.*;

/**
 * Created by Felix Stegmaier on 14.03.17.
 */
public class Parser {

  public static ParserResult parse(BufferedReader in) {
    ParseTree parseTree = null;
    Symbol parseSymbol = null;
    Collection<Issue> issues = new ArrayList<>();
    boolean success = true; /* flipped to false, if any errors occur */

    /* prepare the parsing */
    ComplexSymbolFactory csf = new ComplexSymbolFactory();
    ScannerBuffer lexer = new ScannerBuffer(new OpenScadLexer(in, csf));
    CustomErrorOpenScadParser parser = new CustomErrorOpenScadParser(lexer, csf);

    try {
      parseSymbol = parser.parse();
      parseTree = new ParseTree(parseSymbol, (ASTNode) parseSymbol.value);

    } catch (ParserException pe) {
      issues.add(pe.toIssue());
      success = false;

    } catch (Exception e) {
      //TODO convert to issue, log out the message using the logger
      issues.add(new Issue(Issue.issueType.ERROR, null, 0, "E-003", "Unknown Parser Error", null));
      success = false;
    }

    /* even if an exception was thrown, there may be more issues to handle */
    if (!parser.getErrors().isEmpty()) {
      issues.addAll(parser.getErrors());
      success = false;
    }

    return new ParserResult(success, parseTree, parseSymbol, issues);
  }
}
