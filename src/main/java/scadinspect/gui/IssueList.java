package gui;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Created by halecket on 17.03.2017.
 */
public class IssueList {
  TableView issueList = new TableView();
  TableColumn issues = new TableColumn("issues");

  public TableView showList () {
    issues.setPrefWidth(100);
    issueList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    issueList.getColumns().add(issues);
    return issueList;
  }

  //TO DO get data

  public void dummyData () {
    
  }

}
