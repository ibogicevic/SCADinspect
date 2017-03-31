package scadinspect.gui;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import scadinspect.data.analysis.Issue;
import scadinspect.data.analysis.Issue.issueType;

/**
 * Created by halecket on 17.03.2017.
 */

public class IssueList {

  private TableView<Issue> issueList = new TableView<>();

  private final ObservableList<Issue> issueData = FXCollections.observableArrayList();

  //TableColumn issuesCol = new TableColumn("issues");
  TableColumn resourcesCol = new TableColumn("resources");
  //TableColumn typeCol = new TableColumn("type");
  TableColumn lineNumberCol = new TableColumn("Line number");
  TableColumn issueIdentifierCol = new TableColumn("Identifier");
  TableColumn descriptionCol = new TableColumn("Description");
  TableColumn codeSnippedCol = new TableColumn("Preview");

  public TableView showList() {
    ArrayList<Issue> issues = new ArrayList<>();

    for(int i = 0; i < 10; i++){
      issues.add(i, new Issue(Issue.issueType.ERROR, "example.scad"+i,15+(i*2), "randomIdentifier"+i, "description"+i,"console.log(\"error\")"+i
      ));
    }

    addDataToTable(issues);

    issueList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    //addDataToTable();

    /*
    typeCol.setCellValueFactory(
        new PropertyValueFactory<Issue,String >("isError")
    );
    */

    lineNumberCol.setCellValueFactory(
        new PropertyValueFactory<Issue, Integer>("lineNumber")
    );

    issueIdentifierCol.setCellValueFactory(
        new PropertyValueFactory<Issue,String>("issueIdentifier")
    );

    descriptionCol.setCellValueFactory(
        new PropertyValueFactory<Issue, String>("description")
    );

    codeSnippedCol.setCellValueFactory(
        new PropertyValueFactory<Issue, String>("codeSnippet")
    );

    resourcesCol.setCellValueFactory(
        new PropertyValueFactory<Issue, String>("sourceFile")
    );

    issueList.setItems(issueData);
    issueList.getColumns().addAll(issueIdentifierCol, descriptionCol, codeSnippedCol, resourcesCol, lineNumberCol);

    return issueList;
  }

  public void addDataToTable(ArrayList<Issue> issues){
    for(int i = 0; i < issues.size(); i++){
      issueData.add(i, issues.get(i));
    }
  }
}
