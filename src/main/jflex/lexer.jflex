/* JFlex example: partial Java language lexer specification */
package scadinspect.parser;

import java_cup.runtime.*;
import scadinspect.parser.OpenScadSymbols;

/**
 * This class is a simple example lexer.
 */
%%

%class OpenScadLexer
%unicode
%cup
%line
%column

%eofval{
  return new java_cup.runtime.Symbol(OpenScadSymbols.EOF);
%eofval}

%{
  StringBuffer string = new StringBuffer();

  private Symbol symbol(int type) {
    return new Symbol(type, yyline, yycolumn);
  }
  private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline, yycolumn, value);
  }
%}

/* macros */

D = [0-9]
E = [Ee][+-]?{D}+
H = [0-9a-fA-F]

/* states */

%state cond_comment
%state cond_lcomment
%state cond_string
%state cond_include
%state cond_use

%%

/* keywords */
/* test */
<YYINITIAL> "abstract"            { return symbol(OpenScadSymbols.ABSTRACT); }
<YYINITIAL> "boolean"            { return symbol(OpenScadSymbols.BOOLEAN); }
<YYINITIAL> "break"              { return symbol(OpenScadSymbols.BREAK); }

/* error fallback */
[^]                              { throw new Error("Illegal character <"+
                                                    yytext()+">"); }