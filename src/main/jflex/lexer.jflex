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

/* TODO insert others in rigth order here */

"<=" { return symbol(OpenScadSymbols.LE); }
">=" { return symbol(OpenScadSymbols.GE); }
"==" { return symbol(OpenScadSymbols.EQ); }
"!=" { return symbol(OpenScadSymbols.NE); }
"&&" { return symbol(OpenScadSymbols.AND); }
"||" { return symbol(OpenScadSymbols.OR); }

/* selfdefined, not in origional grammar, cant pass char directly */
";" { return symbol(OpenScadSymbols.SEMICOLON); }
"." { return symbol(OpenScadSymbols.DOT); }
":" { return symbol(OpenScadSymbols.COLON); }
"," { return symbol(OpenScadSymbols.COMMA); }
"{" { return symbol(OpenScadSymbols.BRACKET_CURLY_OPEN); }
"}" { return symbol(OpenScadSymbols.BRACKET_CURLY_CLOSE); }
"(" { return symbol(OpenScadSymbols.BRACKET_ROUND_OPEN); }
")" { return symbol(OpenScadSymbols.BRACKET_ROUND_CLOSE); }
"[" { return symbol(OpenScadSymbols.BRACKET_SQUARE_OPEN); }
"]" { return symbol(OpenScadSymbols.BRACKET_SQUARE_CLOSE); }
"<" { return symbol(OpenScadSymbols.BRACKET_ANGLE_OPEN); }
">" { return symbol(OpenScadSymbols.BRACKET_ANGLE_CLOSE); }
"=" { return symbol(OpenScadSymbols.EQUAL); }
"!" { return symbol(OpenScadSymbols.EXCLAMATION_MARK); }
"#" { return symbol(OpenScadSymbols.HASH); }
"%" { return symbol(OpenScadSymbols.PERCENT); }
"*" { return symbol(OpenScadSymbols.ASTERISK); }
"/" { return symbol(OpenScadSymbols.SLASH); }
"+" { return symbol(OpenScadSymbols.PLUS); }
"-" { return symbol(OpenScadSymbols.MINUS); }


/* error fallback */
[^]                              { throw new Error("Illegal character <"+
                                                    yytext()+">"); }