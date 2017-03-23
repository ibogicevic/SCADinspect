package gui;

import java.util.ArrayList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Created by halecket on 17.03.2017.
 */
public class IssueList {
  TableView<String> issueList = new TableView();
  TableColumn issues = new TableColumn("issues");
  TableColumn ressources = new TableColumn("ressources");

  public TableView showList () {
    issueList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    issueList.getColumns().addAll(issues, ressources);



    return issueList;
  }

  ArrayList dummy = new ArrayList();

  /*
  addDataToTable gets an array list with issue objects.
  a for loop circles through the list
  issue objects get added to row
  */

  public void addDataToTable () {
    for(int i = 0; i < dummy.size(); i++){
      String tempLineNumber = new String(dummy.get(i).lineNumber.toString);
      String tempIsError
      if(dummy.get(i).isError){
        tempIsError = "Error";
      } else {
        tempIsError = "Warning";
      }

      issueList.getItems(tempIsError + " " + dummy.get(i).sourceFile + " " + tempLineNumber + " " + dummy.get(i).issueIdentifier + " " + dummy.get(i).description);
    }
  }

  //TO DO get data

  public void dummyData () {
    
  }

}
