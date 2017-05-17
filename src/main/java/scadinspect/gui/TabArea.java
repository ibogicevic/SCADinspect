package scadinspect.gui;

import java.util.prefs.Preferences;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;


/**
 * Area for 'Issues' and 'Documentation' Tabulators, sets content for Tabs here.
 *
 * @author Tim Walter
 */
public class TabArea extends TabPane {

  private final IssueList issueList = new IssueList();
  private final DocumentationList documentationList = new DocumentationList();

  private final Tab issues, documentation;

  public TabArea() {

    // Tabs can't be closed
    this.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

    TableView issue = generateIssueTable(issueList);
    TableView documentations = generateDocTable(documentationList);

    // issue tab
    issues = new Tab();
    issues.setText("Issues");
    issues.setContent(issue); //SET CONTENT FOR issueArea HERE

    // documentation tab
    documentation = new Tab();
    documentation.setText("Documentation");
    documentation.setContent(documentations); //SET CONTENT FOR documentationArea HERE

    Preferences userPrefs = Preferences.userRoot().node("DHBW.SCADInspect.Settings");    
    
    this.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
    if(userPrefs.getBoolean(SettingsDialog.SETTING_STATIC_ANALYSIS, true)) {
      this.getTabs().add(issues);
    }
    if(userPrefs.getBoolean(SettingsDialog.SETTING_DOCUMENTATION, true)) {
      this.getTabs().add(documentation);
    }
  }

  private TableView generateIssueTable(IssueList issueList) {
    TableView table = issueList.showList();
    return table;
  }

  public TableView generateDocTable(DocumentationList documentationList) {
    TableView table = documentationList.generateTableView();
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

