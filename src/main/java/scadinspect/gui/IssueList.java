package scadinspect.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import scadinspect.data.analysis.Issue;

/**
 * Created by halecket on 17.03.2017.
 */

public class IssueList {
  private TableView<Issue> issueList = new TableView<>();

  private final ObservableList<Issue> issueData =
      FXCollections.observableArrayList(
          new Issue(true, "asdf", 15, "asdf", "asdf", "asdf", "asdf")
      );
/*
  public void showTable () {
    issueList.setEditable(true);

    TableColumn issuesCol = new TableColumn("Issues");
    issuesCol.setMinWidth(100);
    TableColumn resourcesCol = new TableColumn("Resources");


  }
  */
  TableColumn issues = new TableColumn("issues");
  TableColumn resources = new TableColumn("resources");

  public TableView showList () {

    issueList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    //addDataToTable();

    issues.setCellValueFactory(
        new PropertyValueFactory<Issue, String>("description")
    );

    issueList.setItems(issueData);
    
    issueList.getColumns().add(issues);
    issueList.getColumns().add(resources);




    return issueList;
  }

  ArrayList<Issue> dummy = new ArrayList<>();


  /*
  addDataToTable gets an array list with issue objects.
  a for loop circles through the list
  issue objects get added to row
  */

  public void addDataToTable () {
    dummyData();



    for(int i = 0; i < dummy.size(); i++){
      Integer tempLineNumber = dummy.get(i).getLineNumber();
      tempLineNumber.toString();
      String tempIsError;
      if(dummy.get(i).getIsError()){
        tempIsError = "Error";
      } else {
        tempIsError = "Warning";
      }

      //issueData.add(i, tempIsError + " " + dummy.get(i).getSourceFile() + " " + tempLineNumber + " " + dummy.get(i).getIssueIdentifier() + " " + dummy.get(i).getDescription());
    }



  }

  public void dummyData () {
    Issue asdf = new scadinspect.data.analysis.Issue(true,"asdf",15,"asdf","asdf","asdf", "asdf");
    Issue fdsa = new scadinspect.data.analysis.Issue(false,"fdsa",15,"fdsa","fdsa","fdsa", "fdsa");
    dummy.add(0, asdf);
    dummy.add(1, fdsa);
  }

}
