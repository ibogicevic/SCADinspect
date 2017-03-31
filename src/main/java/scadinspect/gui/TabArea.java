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

  public IssueList issueList = new IssueList();
  DocumentationList documentationList = new DocumentationList();

  public TabArea() {

    // Tabs can't be closed
    this.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

      TableView issue = generateIssueTable(issueList);
    TableView documentations = generateDocTable(documentationList);

    //issue tab
    Tab issues = new Tab();
    issues.setText("Issues");
    issues.setContent(issue); //SET CONTENT FOR issueArea HERE

    //documentation tab
    Tab documentation = new Tab();
    documentation.setText("Documentation");
    documentation.setContent(documentations); //SET CONTENT FOR documentationArea HERE

    this.getTabs().add(issues);
    this.getTabs().add(documentation);
  }

  private TableView generateIssueTable(IssueList issueList) {
    TableView table = issueList.showList();
    return table;
  }

  private TableView generateDocTable(DocumentationList documentationList) {
    TableView table = documentationList.showList();
    return table;
  }
}