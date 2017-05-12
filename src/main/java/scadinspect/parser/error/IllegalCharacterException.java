package scadinspect.parser.error;

/**
 * Created by Felix Stegmaier on 23.03.17.
 *
 * specific ParserException thrown by the lexer when an Illegal Character is found
 */
public class IllegalCharacterException extends ParserException {

  private final String issueId = "E-001";

  //TODO externalize strings

  public IllegalCharacterException(String character, String file, int line, int column) {
    super("Illegal character <" + character + ">", file, line, column);
  }

  public IllegalCharacterException(String character, Throwable cause, String file, int line,
      int column) {
    super("Illegal character <" + character + ">", cause, file, line, column);
  }

  @Override
  public String getIssueId() {
    return issueId;
  }
}
