package scadinspect.parser;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Collection;
import java_cup.runtime.ScannerBuffer;
import scadinspect.data.analysis.Issue;
import scadinspect.parser.error.ParserException;

/**
 * Created by Felix Stegmaier on 14.03.17.
 */
public class Parser {

    public static ParserResult parse(BufferedReader in) {
        ParseTree parseTree = null;
        Collection<Issue> issues = new ArrayList<>();
        boolean success = false;
        ScannerBuffer lexer = new ScannerBuffer(new OpenScadLexer(in));
        /* deprecated, but the new one with ComplexSymbolFactory fails */
        OpenScadParser parser = new OpenScadParser(lexer);
        try {
            parser.debug_parse();
            success = true;
        } catch (ParserException pe) {
            issues.add(pe.toIssue());
            success = false;
        } catch (Exception e) {
            System.out.println(e);
            success = false;
        }
        return new ParserResult(success, parseTree, issues);
    }
}
