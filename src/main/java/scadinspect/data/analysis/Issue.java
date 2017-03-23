package scadinspect.data.analysis;

/**
 * Data Model for Issues
 * @author Tim Walter
 */
public class Issue {
    private int lineNumber;
    private String priority;
    private String issueIdentifier; // Or Integer (1: High, 2: Middle, 3: Low) ?
    private String description;
    private String codeSnippet;

    /**
     * Constructor for an found issues
     * @param lineNumber Number of Line of Issue in SCAD File
     * @param priority Priority of issue
     * @param issueIdentifier Name or Identifier for issue
     * @param description Short Description for issue
     * @param codeSnippet Snippet of Code in Line
     */
    public Issue (int lineNumber, String priority, String issueIdentifier,String description, String codeSnippet) {
        this.lineNumber = lineNumber;
        this.priority = priority;
        this.issueIdentifier = issueIdentifier;
        this.description = description;
        this.codeSnippet = codeSnippet;
    }

    /*
     * Getters for an instance
     */
    public int getLineNumber() {
        return lineNumber;
    }

    public String getPriority() {
        return priority;
    }

    public String getIssueIdentifier() {
        return issueIdentifier;
    }

    public String getDescription() {
        return description;
    }

    public String getCodeSnippet() {
        return codeSnippet;
    }
}
