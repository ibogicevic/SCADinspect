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
    boolean success = false;

    ComplexSymbolFactory csf = new ComplexSymbolFactory();
    ScannerBuffer lexer = new ScannerBuffer(new OpenScadLexer(in, csf));
    OpenScadParser parser = new OpenScadParser(lexer, csf);
    try {
      parseSymbol = parser.debug_parse(); //TODO change to non debug
      parseTree = new ParseTree(parseSymbol, (ASTNode) parseSymbol.value);
      success = true;
    } catch (ParserException pe) {
      issues.add(pe.toIssue());
      success = false;
    } catch (Exception e) {
      //TODO convert to issue
      System.out.println(e);
      success = false;
    }
    return new ParserResult(success, parseTree, parseSymbol, issues);
  }
}