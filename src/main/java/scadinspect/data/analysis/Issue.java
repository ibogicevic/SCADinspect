package scadinspect.data.analysis;

/**
 * Data Model for Issues
 * @author Tim Walter
 */
public class Issue {

    public enum issueType {
        ERROR, WARNING, INFO
    }

    private issueType type;
    private String sourceFile;
    private int lineNumber;
    private String issueIdentifier;  // Maybe change this to enum
    private String description;
    private String codeSnippet;

    /**
     * Constructor for found issues
     * @param type Type of the issue
     * @param sourceFile Path to Source File
     * @param lineNumber Number of Line of Issue in SCAD File
     * @param issueIdentifier Name or Identifier for issue
     * @param description Short Description for issue
     * @param codeSnippet Snippet of Code in Line
     */
    public Issue (issueType type, String sourceFile, int lineNumber, String issueIdentifier,String description, String codeSnippet) {
        this.type = type;
        this.sourceFile = sourceFile;
        this.lineNumber = lineNumber;
        this.issueIdentifier = issueIdentifier;
        this.description = description;
        this.codeSnippet = codeSnippet;
    }

    /*
     * Getters for the instance
     */
    public String getType() {
        return type.toString();
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

    /* Set code snippet after issue is instantiated */
    public void setCodeSnippet() {

    }

    /* Set code snippet after issue is instantiated */
    public void setCodeSnippet() {

    }

    @Override
    public String toString() {
        return "Issue{" +
            "type=" + type +
            ", sourceFile='" + sourceFile + '\'' +
            ", lineNumber=" + lineNumber +
            ", issueIdentifier='" + issueIdentifier + '\'' +
            ", description='" + description + '\'' +
            ", codeSnippet='" + codeSnippet + '\'' +
            '}';
    }
}
