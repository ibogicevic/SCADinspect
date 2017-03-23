package scadinspect.parser.generated;

import java_cup.runtime.Symbol;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.Location;
import scadinspect.parser.*;
import scadinspect.parser.error.*;

%%

%public
%class OpenScadLexer
%unicode
%cup
%char
%line
%column

%yylexthrow scadinspect.parser.error.IllegalCharacterException


%eofval{
     return symbolFactory.newSymbol("EOF", OpenScadSymbols.EOF, new Location(yyline+1,yycolumn+1,yychar), new Location(yyline+1,yycolumn+1,yychar+1));
%eofval}

%{
  StringBuffer string = new StringBuffer();
  ComplexSymbolFactory symbolFactory;

  public OpenScadLexer(java.io.Reader in, ComplexSymbolFactory sf){
    this(in);
    symbolFactory = sf;
  }

  private Symbol symbol(String name, int sym) {
    return symbolFactory.newSymbol(name, sym, new Location(yyline+1,yycolumn+1,yychar), new Location(yyline+1,yycolumn+yylength(),yychar+yylength()));
  }

  private Symbol symbol(String name, int sym, Object val) {
    Location left = new Location(yyline+1,yycolumn+1,yychar);
    Location right= new Location(yyline+1,yycolumn+yylength(), yychar+yylength());
    return symbolFactory.newSymbol(name, sym, left, right,val);
  }

  private Symbol symbol(String name, int sym, Object val,int buflength) {
    Location left = new Location(yyline+1,yycolumn+yylength()-buflength,yychar+yylength()-buflength);
    Location right= new Location(yyline+1,yycolumn+yylength(), yychar+yylength());
    return symbolFactory.newSymbol(name, sym, left, right,val);
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

/* include */
/* TODO includes */
<YYINITIAL> include[ \t\r\n]*"<"  { yybegin(cond_include); }
<cond_include> {
[^\t\r\n>]*"/"	        {}
[^\t\r\n>/]+	          {}
">"		                  { yybegin(YYINITIAL);}
} /* close cond_include */

/* use */
<YYINITIAL> use[ \t\r\n]*"<"	{ yybegin(cond_use); }
<cond_use> {
[^\t\r\n>]+	            {}
 ">"		                { yybegin(YYINITIAL);
                          return symbol("use", OpenScadSymbols.TOK_USE);
                        }
} /* close cond_use */

/* keywords */
<YYINITIAL> "module"	  { return symbol("module", OpenScadSymbols.TOK_MODULE); }
<YYINITIAL> "function"	{ return symbol("function", OpenScadSymbols.TOK_FUNCTION); }
<YYINITIAL> "if"		    { return symbol("if", OpenScadSymbols.TOK_IF); }
<YYINITIAL> "else"		  { return symbol("else", OpenScadSymbols.TOK_ELSE); }
<YYINITIAL> "let"		    { return symbol("let", OpenScadSymbols.TOK_LET); }
<YYINITIAL> "assert"	  { return symbol("assert", OpenScadSymbols.TOK_ASSERT); }
<YYINITIAL> "echo"	    { return symbol("echo", OpenScadSymbols.TOK_ECHO); }
<YYINITIAL> "for"		    { return symbol("for", OpenScadSymbols.TOK_FOR); }
<YYINITIAL> "each"		  { return symbol("each", OpenScadSymbols.TOK_EACH); }
<YYINITIAL> "true"		  { return symbol("true", OpenScadSymbols.TOK_TRUE); }
<YYINITIAL> "false"		  { return symbol("false", OpenScadSymbols.TOK_FALSE); }
<YYINITIAL> "undef"		  { return symbol("undef", OpenScadSymbols.TOK_UNDEF); }

/* unicode */

/* TODO what to do with unicode, CUP can handle it, openscad cant, except for in comments and strings? */

/* numbers */
<YYINITIAL> {D}+{E}? |
{D}*\.{D}+{E}? |
{D}+\.{D}*{E}?      { return symbol("number", OpenScadSymbols.TOK_NUMBER); }

/* identifiers */
<YYINITIAL> "$"?[a-zA-Z0-9_]+   { return symbol("id", OpenScadSymbols.TOK_ID); }

/* strings */
/* TODO unicode handeling */
/* TODO build string */
<YYINITIAL> \"			    { yybegin(cond_string);}
<cond_string> {
\\n			                { }
\\t			                { }
\\r			                { }
\\\\		                { }
\\\"		                { }
\\x[0-7]{H}             { }
\\u{H}{4}|\\U{H}{6}     { }
[^\\\n\"]		            { }
[\n\r]		              { }
\"			                { yybegin(YYINITIAL);
                          return symbol("string", OpenScadSymbols.TOK_STRING);
                        }
} /* close cond_string */

/* whitespaces */
<YYINITIAL> [\t ]       { /* ignore */ }
<YYINITIAL> [\n\r]      { /* ignore */ }

/* line comments */
/* unicode allowed and handeled by [^\n] */
<YYINITIAL> "//"        { yybegin(cond_lcomment); }
<cond_lcomment> {
\n                      { yybegin(YYINITIAL); }
[^\n]                   { }
} /* close cond_lcomment */

/* comments */
/* unicode allowed and handeled in [^] */
<YYINITIAL> "/*"        { yybegin(cond_comment); }
<cond_comment> {
"*/"                    { yybegin(YYINITIAL); }
[^]                     { /* also matches unicode */ }
} /* close cond_comment */

/* comparisons */
<YYINITIAL> "<=" { return symbol("LE", OpenScadSymbols.LE); }
<YYINITIAL> ">=" { return symbol("GE", OpenScadSymbols.GE); }
<YYINITIAL> "==" { return symbol("EQ", OpenScadSymbols.EQ); }
<YYINITIAL> "!=" { return symbol("NE", OpenScadSymbols.NE); }
<YYINITIAL> "&&" { return symbol("AND", OpenScadSymbols.AND); }
<YYINITIAL> "||" { return symbol("OR", OpenScadSymbols.OR); }

/* selfdefined, not in origional grammar, can't pass char directly */
<YYINITIAL> ";" { return symbol(";", OpenScadSymbols.SEMICOLON); }
<YYINITIAL> "." { return symbol(".", OpenScadSymbols.DOT); }
<YYINITIAL> ":" { return symbol(":", OpenScadSymbols.COLON); }
<YYINITIAL> "," { return symbol(",", OpenScadSymbols.COMMA); }
<YYINITIAL> "{" { return symbol("{", OpenScadSymbols.BRACKET_CURLY_OPEN); }
<YYINITIAL> "}" { return symbol("}", OpenScadSymbols.BRACKET_CURLY_CLOSE); }
<YYINITIAL> "(" { return symbol("(", OpenScadSymbols.BRACKET_ROUND_OPEN); }
<YYINITIAL> ")" { return symbol(")", OpenScadSymbols.BRACKET_ROUND_CLOSE); }
<YYINITIAL> "[" { return symbol("[", OpenScadSymbols.BRACKET_SQUARE_OPEN); }
<YYINITIAL> "]" { return symbol("]", OpenScadSymbols.BRACKET_SQUARE_CLOSE); }
<YYINITIAL> "<" { return symbol("<", OpenScadSymbols.BRACKET_ANGLE_OPEN); }
<YYINITIAL> ">" { return symbol(">", OpenScadSymbols.BRACKET_ANGLE_CLOSE); }
<YYINITIAL> "=" { return symbol("=", OpenScadSymbols.EQUAL); }
<YYINITIAL> "!" { return symbol("!", OpenScadSymbols.EXCLAMATION_MARK); }
<YYINITIAL> "?" { return symbol("?", OpenScadSymbols.QUESTION_MARK); }
<YYINITIAL> "#" { return symbol("#", OpenScadSymbols.HASH); }
<YYINITIAL> "%" { return symbol("%", OpenScadSymbols.PERCENT); }
<YYINITIAL> "*" { return symbol("*", OpenScadSymbols.ASTERISK); }
<YYINITIAL> "/" { return symbol("/", OpenScadSymbols.SLASH); }
<YYINITIAL> "+" { return symbol("+", OpenScadSymbols.PLUS); }
<YYINITIAL> "-" { return symbol("-", OpenScadSymbols.MINUS); }

/* error fallback */
[^]             { throw new IllegalCharacterException( yytext() , null , yyline, yycolumn); }