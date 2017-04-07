package scadinspect.gui;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.control.cell.PropertyValueFactory;
import scadinspect.data.scaddoc.Documentation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;

public class DocumentationList {
  //TableView of Documentation List
  private TableView<ObservableList<String>> docList = new TableView<ObservableList<String>>();

  //Data structure with associated data for documentation List
  private final ObservableList<String> docData = FXCollections.observableArrayList();

  private static int columnCount = 1;
  private static int rowCount = 0;

  //Generate the list which is shown withing the table
  public TableView generateList () {
    docList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    return docList;
  }

  private void addColumn(int colIndex, String columnName) {
    TableColumn<ObservableList<String>, String> column = new TableColumn<>(columnName);
    column.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(colIndex)));
    docList.getColumns().add(column);
    columnCount++;
  }


  //add the Data (currently as ArrayList) to the observableList
  /*
  public void addData (ArrayList<Documentation> documentations){
    for(int i = 0; i < documentations.size(); i++){
      docData.add(i, documentations.get(i));
    }
  }
  */
}
