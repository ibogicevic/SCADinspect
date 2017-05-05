package scadinspect.gui;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;


/**
 * Area for 'Issues' and 'Documentation' Tabulators, sets content for Tabs here.
 *
 * @author Tim Walter
 */
public class TabArea extends TabPane {

  private IssueList issueList = new IssueList();
  private DocumentationList documentationList = new DocumentationList();

  private Tab issues;
  private Tab documentation;

  public TabArea() {

    // Tabs can't be closed
    this.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

    TableView issue = generateIssueTable(issueList);
    TableView documentations = generateDocTable(documentationList);

    // issue tab
    issues = new Tab();
    issues.setText(Messages.getString("TabArea.issueText"));
    issues.setContent(issue); //SET CONTENT FOR issueArea HERE

    // documentation tab
    documentation = new Tab();
    documentation.setText(Messages.getString("TabArea.docuText"));
    documentation.setContent(documentations); //SET CONTENT FOR documentationArea HERE

    this.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
    this.getTabs().add(issues);
    this.getTabs().add(documentation);
  }

  private TableView generateIssueTable(IssueList issueList) {
    TableView table = issueList.showList();
    return table;
  }

  public TableView generateDocTable(DocumentationList documentationList) {
    TableView table = documentationList.generateList();
    return table;
  }

  /* For Help Modal get Issue and Documentation Tab */
  public Tab getIssues() {
    return issues;
  }

  public Tab getDocumentation() {
    return documentation;
  }

  public IssueList getIssueList() {
    return issueList;
  }

  public DocumentationList getDocumentationList() {
    return documentationList;
  }
}

