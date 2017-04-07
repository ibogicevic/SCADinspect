package scadinspect.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;
import java_cup.runtime.Scanner;
import java_cup.runtime.Symbol;
import java_cup.runtime.SymbolFactory;
import scadinspect.data.analysis.Issue;
import scadinspect.parser.error.ParserException;
import scadinspect.parser.generated.OpenScadParser;

/**
 * Created by Felix Stegmaier on 24.03.2017.
 */
public class CustomErrorOpenScadParser extends OpenScadParser {

  Collection<Issue> errors = new ArrayList<>();

  public static final String errorID = "E-002";

  //TODO use logger everywhere

  public CustomErrorOpenScadParser(Scanner s, SymbolFactory sf) {
    super(s, sf);
  }

  public Collection<Issue> getErrors() {
    return errors;
  }

  @Override
  public void report_fatal_error(String message, Object info) throws Exception {
    this.done_parsing();
    this.report_error(message, info);
    throw new ParserException("Can\'t recover from parser error(s)", null, 0, 0);
  }

  @Override
  public void report_error(String message, Object info) {
    if(info instanceof ComplexSymbol) {
      ComplexSymbol cs = (ComplexSymbol)info;
      //TODO get filename, code snippet?
      //TODO really, we need to get that filename somehow
      errors.add(new Issue(Issue.issueType.ERROR, null, cs.getLeft().getLine() , this.errorID, message + " for input symbol \"" + cs.getName() + "\" spanning from " + cs.getLeft() + " to " + cs.getRight()));
    } else if(info instanceof Symbol) {
        if(((Symbol)info).left != -1) {
            errors.add(new Issue(Issue.issueType.ERROR, null, 0, this.errorID, message +  " at character " + ((Symbol)info).left + " of input"));
        } else {
           errors.add(new Issue(Issue.issueType.ERROR, null, 0, this.errorID, message));
        }
    } else {
        errors.add(new Issue(Issue.issueType.ERROR, null, 0, this.errorID, message));
    }
  }

  @Override
  public void syntax_error(Symbol cur_token) {
    this.report_error("Syntax error", cur_token);
    this.report_expected_token_ids();
  }

  @Override
  protected void report_expected_token_ids() {
    List ids = this.expected_token_ids();
    LinkedList list = new LinkedList();
    Iterator var3 = ids.iterator();

    while(var3.hasNext()) {
      Integer expected = (Integer)var3.next();
      list.add(this.symbl_name_from_id(expected.intValue()));
    }

      //TODO get this into the respective issue
      //and log it
    //System.out.println("instead expected token classes are " + list);
  }

}
