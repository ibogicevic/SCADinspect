package scadinspect.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ScannerBuffer;
import java_cup.runtime.Symbol;
import scadinspect.data.analysis.Issue;
import scadinspect.gui.Main;
import scadinspect.parser.ast.ASTNode;
import scadinspect.parser.error.ParserException;
import scadinspect.parser.generated.OpenScadLexer;

/**
 * Created by Felix Stegmaier on 14.03.17.
 *
 * The Parser to generate the syntax tree and find possible syntax errors in a OpenSCAD file.
 * Only contains one static method
 * @see scadinspect.parser.Parser#parse(File)
 *
 */
public class Parser {

  /**
   * Parse a OpenSCAD file into a syntax tree and find syntax errors
   * @param file the file to parse
   * @return a parser result
   * @see scadinspect.parser.ParserResult
   * @throws FileNotFoundException in case opensing the file in a FileReader fails
   *
   * This method performs the following steps to generate the result:
   *  - open the File
   *  - perform the parsing using
   *  @see scadinspect.parser.CustomErrorOpenScadParser
   *  - catch all the possible Exceptions that occure during parsing
   *  @see scadinspect.parser.error.ParserException
   *  - collect issues from the CustomErrorOpenScadParser and the exceptions
   *  - construct a parserResult from this
   *
   */
  public static ParserResult parse(File file) throws FileNotFoundException {
    Main.logger.log(Level.INFO, "Syntax tree parsing starting for <" + file.getPath() + ">");
    BufferedReader in = new BufferedReader(new FileReader(file));
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
      //Main.logger.log(Level.INFO, pe.toIssue().toString());
      success = false;

    } catch (Exception e) {
      //convert to issue, log out the message using the logger
      //issues.add(new Issue(Issue.issueType.ERROR, null, 0, "E-003", "Unknown Parser Error"));
      Main.logger.log(Level.WARNING, "Unkown Parser Error for <" + file.getPath() + ">", e);
      success = false;
    }

    /* even if an exception was thrown, there may be more issues to handle */
    if (!parser.getErrors().isEmpty()) {
      issues.addAll(parser.getErrors());
      success = false;
    }

    /* filename not known in parsing, so we set them now */
    for (Issue issue: issues) {
      issue.setSourceFile(file.getPath());
    }

    Main.logger.log(Level.INFO, "Syntax tree parsing done for <" + file.getPath() + ">");
    return new ParserResult(success, parseTree, parseSymbol, issues);
  }
}
