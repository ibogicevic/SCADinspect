package scadinspect.parser.error;

import scadinspect.data.analysis.Issue;

/**
 * Created by Felix Stegmaier on 23.03.17.
 */
public class ParserException extends Exception {

    private final String defaultIssueId = "E-000";

    private final String file;
    private final int line;
    private final int column;
    private final String info;


    public ParserException(String info, String file, int line, int column) {
        super(info);
        this.file = file;
        this.line = line;
        this.column = column;
        this.info = info;
    }

    public ParserException(String info, Throwable cause, String file, int line, int column) {
        super(info, cause);
        this.file = file;
        this.line = line;
        this.column = column;
        this.info = info;
    }

    public String getIssueId() {
        return defaultIssueId;
    }

    public String getFile() {
        return file;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public String getInfo() {
        return info;
    }


    public Issue toIssue() {
        //TODO code snippet, File
        return new Issue(true, this.getFile(), this.getLine(), this.getIssueId(), this.getInfo(), null);
    }

}
