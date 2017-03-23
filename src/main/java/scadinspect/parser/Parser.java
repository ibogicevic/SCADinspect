package scadinspect.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java_cup.runtime.ScannerBuffer;
import java_cup.runtime.Symbol;

/**
 * Created by felix on 14.03.17.
 */
public class Parser {

    public void parse(java.io.Reader in) throws Exception {
        ScannerBuffer lexer = new ScannerBuffer(new OpenScadLexer(new BufferedReader(in)));
        OpenScadParser parser = new OpenScadParser(lexer);
        Symbol symbol = parser.debug_parse();
        System.out.println("done");
    }

    public static void main() throws Exception {
        FileReader in = new FileReader("/home/felix/Documents/DHBW/4th_Semester/SE/SCADinspect/logo/proposals/2.scad");
        Parser parser = new Parser();
        parser.parse(in);
    }
}
