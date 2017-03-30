package scadinspect.data.analysis;

/**
 * Data Model for Issues
 * @author Tim Walter
 */
public class Issue {
    private boolean isError;
    private String sourceFile;
    private int lineNumber;
    private String issueIdentifier;  // Maybe change this to enum
    private String description;
    private String codeSnippet;

    /**
     * Constructor for found issues
     * @param isError Bool value if it is a error, if not -> warning
     * @param sourceFile Path to Source File
     * @param lineNumber Number of Line of Issue in SCAD File
     * @param issueIdentifier Name or Identifier for issue
     * @param description Short Description for issue
     * @param codeSnippet Snippet of Code in Line
     */
    public Issue (boolean isError, String sourceFile, int lineNumber, String issueIdentifier,String description, String codeSnippet) {
        this.isError = isError;
        this.sourceFile = sourceFile;
        this.lineNumber = lineNumber;
        this.issueIdentifier = issueIdentifier;
        this.description = description;
        this.codeSnippet = codeSnippet;
    }

    /*
     * Getters for the instance
     */
    public boolean getIsError () {
        return isError;
    }

    public String getSourceFile() {
        return sourceFile;
    }

    public int getLineNumber() {
        return lineNumber;
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

    @Override
    public String toString() {
        return "Issue{" +
            "isError=" + isError +
            ", sourceFile='" + sourceFile + '\'' +
            ", lineNumber=" + lineNumber +
            ", issueIdentifier='" + issueIdentifier + '\'' +
            ", description='" + description + '\'' +
            ", codeSnippet='" + codeSnippet + '\'' +
            '}';
    }
}
