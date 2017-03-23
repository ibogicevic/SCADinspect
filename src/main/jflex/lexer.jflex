/* JFlex example: partial Java language lexer specification */
package scadinspect.parser;

import java_cup.runtime.*;
import scadinspect.parser.*;
import scadinspect.parser.error.*;

/**
 * This class is a simple example lexer.
 */
%%

%class OpenScadLexer
%unicode
%cup
%line
%column

%yylexthrow scadinspect.parser.error.IllegalCharacterException

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
                          return symbol(OpenScadSymbols.TOK_USE);
                        }
} /* close cond_use */

/* keywords */
<YYINITIAL> "module"	  { return symbol(OpenScadSymbols.TOK_MODULE); }
<YYINITIAL> "function"	{ return symbol(OpenScadSymbols.TOK_FUNCTION); }
<YYINITIAL> "if"		    { return symbol(OpenScadSymbols.TOK_IF); }
<YYINITIAL> "else"		  { return symbol(OpenScadSymbols.TOK_ELSE); }
<YYINITIAL> "let"		    { return symbol(OpenScadSymbols.TOK_LET); }
<YYINITIAL> "assert"	  { return symbol(OpenScadSymbols.TOK_ASSERT); }
<YYINITIAL> "echo"	    { return symbol(OpenScadSymbols.TOK_ECHO); }
<YYINITIAL> "for"		    { return symbol(OpenScadSymbols.TOK_FOR); }
<YYINITIAL> "each"		  { return symbol(OpenScadSymbols.TOK_EACH); }
<YYINITIAL> "true"		  { return symbol(OpenScadSymbols.TOK_TRUE); }
<YYINITIAL> "false"		  { return symbol(OpenScadSymbols.TOK_FALSE); }
<YYINITIAL> "undef"		  { return symbol(OpenScadSymbols.TOK_UNDEF); }

/* unicode */

/* TODO what to do with unicode, CUP can handle it, openscad cant, except for in comments and strings? */

/* numbers */
<YYINITIAL> {D}+{E}? |
{D}*\.{D}+{E}? |
{D}+\.{D}*{E}?      { return symbol(OpenScadSymbols.TOK_NUMBER); }

/* identifiers */
<YYINITIAL> "$"?[a-zA-Z0-9_]+   { return symbol(OpenScadSymbols.TOK_ID); }

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
                          return symbol(OpenScadSymbols.TOK_STRING);
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
<YYINITIAL> "<=" { return symbol(OpenScadSymbols.LE); }
<YYINITIAL> ">=" { return symbol(OpenScadSymbols.GE); }
<YYINITIAL> "==" { return symbol(OpenScadSymbols.EQ); }
<YYINITIAL> "!=" { return symbol(OpenScadSymbols.NE); }
<YYINITIAL> "&&" { return symbol(OpenScadSymbols.AND); }
<YYINITIAL> "||" { return symbol(OpenScadSymbols.OR); }

/* selfdefined, not in origional grammar, can't pass char directly */
<YYINITIAL> ";" { return symbol(OpenScadSymbols.SEMICOLON); }
<YYINITIAL> "." { return symbol(OpenScadSymbols.DOT); }
<YYINITIAL> ":" { return symbol(OpenScadSymbols.COLON); }
<YYINITIAL> "," { return symbol(OpenScadSymbols.COMMA); }
<YYINITIAL> "{" { return symbol(OpenScadSymbols.BRACKET_CURLY_OPEN); }
<YYINITIAL> "}" { return symbol(OpenScadSymbols.BRACKET_CURLY_CLOSE); }
<YYINITIAL> "(" { return symbol(OpenScadSymbols.BRACKET_ROUND_OPEN); }
<YYINITIAL> ")" { return symbol(OpenScadSymbols.BRACKET_ROUND_CLOSE); }
<YYINITIAL> "[" { return symbol(OpenScadSymbols.BRACKET_SQUARE_OPEN); }
<YYINITIAL> "]" { return symbol(OpenScadSymbols.BRACKET_SQUARE_CLOSE); }
<YYINITIAL> "<" { return symbol(OpenScadSymbols.BRACKET_ANGLE_OPEN); }
<YYINITIAL> ">" { return symbol(OpenScadSymbols.BRACKET_ANGLE_CLOSE); }
<YYINITIAL> "=" { return symbol(OpenScadSymbols.EQUAL); }
<YYINITIAL> "!" { return symbol(OpenScadSymbols.EXCLAMATION_MARK); }
<YYINITIAL> "?" { return symbol(OpenScadSymbols.QUESTION_MARK); }
<YYINITIAL> "#" { return symbol(OpenScadSymbols.HASH); }
<YYINITIAL> "%" { return symbol(OpenScadSymbols.PERCENT); }
<YYINITIAL> "*" { return symbol(OpenScadSymbols.ASTERISK); }
<YYINITIAL> "/" { return symbol(OpenScadSymbols.SLASH); }
<YYINITIAL> "+" { return symbol(OpenScadSymbols.PLUS); }
<YYINITIAL> "-" { return symbol(OpenScadSymbols.MINUS); }

/* error fallback */
[^]             { throw new IllegalCharacterException( yytext() , null , yyline, yycolumn); }