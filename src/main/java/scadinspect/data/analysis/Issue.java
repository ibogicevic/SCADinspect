package scadinspect.data.analysis;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;

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
     */
    public Issue (issueType type, String sourceFile, int lineNumber, String issueIdentifier, String description) {
        this.type = type;
        this.sourceFile = sourceFile;
        this.lineNumber = lineNumber;
        this.issueIdentifier = issueIdentifier;
        this.description = description;
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

    /* Set file name and code snippet after issue is instantiated */
    public void setCodeSnippet() {
        if(this.lineNumber != 0) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\IBM_ADMIN\\Documents\\GitHub\\SCADinspect\\TestCorpus\\Examples\\Basics\\CSG.scad"));
                String line = "";



                for(int i = 0; i < this.lineNumber; i++) {
                    line = reader.readLine();
                }

                this.codeSnippet = line;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
