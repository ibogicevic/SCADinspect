package scadinspect.gui;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import scadinspect.data.analysis.Issue;
import scadinspect.data.scaddoc.ScadDocuFile;

/**
 * Created by halecket on 05.05.2017.
 */
public class IssueListFilter extends ComboBox{
  private ObservableList<String> options = FXCollections.observableArrayList();
  private final ArrayList<Issue> filteredList = new ArrayList<>();

  public IssueListFilter () {
    this.setItems(getIssueOptions());
    this.setValue("Show all");
    this.setVisibleRowCount(5);

    this.setOnAction(e -> {
      if(Main.getInstance().tabArea.getSelectionModel().selectedItemProperty().equals("Documentation")){
        if (this.getValue().equals("Show all")) {
          this.setItems(getDocumentOptions());
        } else {
          Main.getInstance().tabArea.getIssueList().getIssues().clear();
          Main.getInstance().tabArea.getIssueList()
              .addDataToTable(Main.getInstance().tabArea.getIssueList().dummyData());
          for (Issue i : Main.getInstance().tabArea.getIssueList().getIssues()) {
            if (this.getValue().equals(i.getSourceFile())) {
              filteredList.add(i);
            }
          }
          Main.getInstance().tabArea.getIssueList().addDataToTable(filteredList);
          filteredList.clear();
        }
      } else {
        if (this.getValue().equals("Show all")) {
          Main.getInstance().tabArea.getIssueList().getIssues().clear();
          Main.getInstance().tabArea.getIssueList()
              .addDataToTable(Main.getInstance().tabArea.getIssueList().dummyData());
        } else {
          Main.getInstance().tabArea.getIssueList().getIssues().clear();
          Main.getInstance().tabArea.getIssueList()
              .addDataToTable(Main.getInstance().tabArea.getIssueList().dummyData());
          for (Issue i : Main.getInstance().tabArea.getIssueList().getIssues()) {
            if (this.getValue().equals(i.getSourceFile())) {
              filteredList.add(i);
            }
          }
          Main.getInstance().tabArea.getIssueList().addDataToTable(filteredList);
          filteredList.clear();
        }
      }
    });
  }

  public ObservableList<String> getIssueOptions () {
    options.clear();
    options.add("Show all");
    for (Issue i : Main.getInstance().tabArea.getIssueList().getIssues()){
      options.add(i.getSourceFile());
    }
    return options;
  }

  public ObservableList<String> getDocumentOptions () {
    options.clear();
    options.add("Show all");
    if(Main.getInstance().getDocuFiles() != null) {
      for (ScadDocuFile i : Main.getInstance().getDocuFiles()) {
        options.add(i.getPath().toString());
      }
    }
    return options;
  }
}
