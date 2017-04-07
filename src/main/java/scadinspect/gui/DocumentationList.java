package scadinspect.gui;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


import java.util.ArrayList;
import scadinspect.data.scaddoc.ScadDocuFile;

public class DocumentationList {
  //TableView of Documentation List
  private TableView<ObservableList<String>> docList = new TableView<ObservableList<String>>();

  //Data structure with associated data for documentation List
  private final ObservableList<String> docData = FXCollections.observableArrayList();

  private static int columnCount = 0;
  private static int rowCount = 0;

  //Generate the list which is shown withing the table
  public TableView generateList () {
    

    docList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    addColumn(columnCount, "Path");
    loadDocList();

    return docList;
  }

  private void addColumn(int colIndex, String columnName) {
    TableColumn<ObservableList<String>, String> column = new TableColumn<>(columnName);
    column.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(colIndex)));
    docList.getColumns().add(column);
    columnCount++;
  }

  private void loadDocList(){
   // List<ScadDocuFile> docList = Main.getInstance().getDocuFiles();
    List<ScadDocuFile> docList = dummyData();
    for(ScadDocuFile file : docList){
      addData(file.getPath().toString());
    }
  }

  private void addData (String Data) {
    docList.getItems().add(
        FXCollections.observableArrayList(Data)
    );
  }

  //add the Data (currently as ArrayList) to the observableList
  /*
  public void addData (ArrayList<Documentation> documentations){
    for(int i = 0; i < documentations.size(); i++){
      docData.add(i, documentations.get(i));
    }
  }
  */
  private List<ScadDocuFile> dummyData(){
    List <ScadDocuFile> dummy = new LinkedList<>();
    try {
      dummy.add(new ScadDocuFile(Paths.get("./spec/samples/sample.scad")));
      dummy.add(new ScadDocuFile(Paths.get("./spec/samples/sample2.scad")));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return  dummy;
  }
}
