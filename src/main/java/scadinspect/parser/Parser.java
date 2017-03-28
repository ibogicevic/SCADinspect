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
    boolean success = true;

    ComplexSymbolFactory csf = new ComplexSymbolFactory();
    ScannerBuffer lexer = new ScannerBuffer(new OpenScadLexer(in, csf));
    CustomErrorOpenScadParser parser = new CustomErrorOpenScadParser(lexer, csf);
    try {
      //parseSymbol = parser.debug_parse(); //TODO change to non debug
      parseSymbol = parser.parse();
      parseTree = new ParseTree(parseSymbol, (ASTNode) parseSymbol.value);
    } catch (ParserException pe) {
      issues.add(pe.toIssue());
      success = false;
    } catch (Exception e) {
      //TODO convert to issue
      System.out.println(e);
      issues.add(new Issue(true, null, 0, "E-003", "Unknown Parser Error", null));
      success = false;
    }
    if (!parser.getErrors().isEmpty()) {
      parser.getErrors().forEach(System.out::println);
      issues.addAll(parser.getErrors());
      success = false;
    }
    return new ParserResult(success, parseTree, parseSymbol, issues);
  }
}
