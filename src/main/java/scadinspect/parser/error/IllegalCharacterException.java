package scadinspect.parser.error;

/**
 * Created by Felix Stegmaier on 23.03.17.
 */
public class IllegalCharacterException extends ParserException {

    private final String priority = "high";

    private final String issueId = "E-001";

    public IllegalCharacterException(String character, String file, int line, int column) {
        super("Illegal character <"+ character + ">", file, line, column);
    }

    public IllegalCharacterException(String character, Throwable cause, String file, int line,
        int column) {
        super("Illegal character <"+ character + ">", cause, file, line, column);
    }

    @Override
    public String getPriority() {
        return priority;
    }

    @Override
    public String getIssueId() {
        return issueId;
    }
}
