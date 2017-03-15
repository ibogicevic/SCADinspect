package scadinspect.parser;

import java.io.BufferedReader;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ScannerBuffer;
import java_cup.runtime.Symbol;

/**
 * Created by felix on 14.03.17.
 */
public class Parser {

    public void parse(java.io.Reader in) throws Exception {
        ComplexSymbolFactory csf = new ComplexSymbolFactory();
        ScannerBuffer lexer = new ScannerBuffer(new OpenScadLexer(new BufferedReader(in)));
        OpenScadParser parser = new OpenScadParser(lexer, csf);
        Symbol symbol = parser.parse();
    }
}
