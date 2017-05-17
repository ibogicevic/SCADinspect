package scadinspect.parser.error;

import scadinspect.data.analysis.Issue;

/**
 * Created by Felix Stegmaier on 23.03.17.
 *
 * Generic Exception that can be thrown during the parsing by
 *
 * @see scadinspect.parser.CustomErrorOpenScadParser
 *
 * Can be tranformed into a Issue using
 * @see ParserException#toIssue()
 *
 * Always has the generic Issue Id defined by
 * @see ParserException#defaultIssueId
 */
public class ParserException extends Exception {

  private final String defaultIssueId = "E-000";
  private final String file;
  private final String info;
  private final int line;
  private final int column;

  /**
   *
   * @param info the info for the Issue
   * @param file the file associated
   * @param line line of the issue
   * @param column of the parser at the moment of throwing
   */
  public ParserException(String info, String file, int line, int column) {
    super(info);
    this.file = file;
    this.line = line;
    this.column = column;
    this.info = info;
  }

  /**
   * Constructor with cause
   */
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
    // code snippet can be found later
    // filename will be inserted by Parser.parse()
    return new Issue(Issue.IssueType.ERROR, this.getFile(), this.getLine(), this.getIssueId(),
        this.getInfo());
  }
}
