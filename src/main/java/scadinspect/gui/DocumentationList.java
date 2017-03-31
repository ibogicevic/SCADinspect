package scadinspect.gui;

import scadinspect.data.scaddoc.Documentation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DocumentationList {
  //TableView of Documentation List
  private TableView docList = new TableView();

  //Data structure with associated data for documentation List
  private final ObservableList<Documentation> docData = FXCollections.observableArrayList();

  private TableColumn part = new TableColumn("Part");
  private TableColumn price = new TableColumn("Price");
  private TableColumn amount = new TableColumn("Amount");
  private TableColumn weight = new TableColumn("Weight");
  private TableColumn material = new TableColumn("Material");
  private TableColumn url = new TableColumn("URL");

  public TableView generateList () {
      //addData(); ---- dummy data currently missing


      docList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
      docList.setItems(docData);
    docList.getColumns().addAll(part, price, amount, weight, material, url);
    return docList;
  }

  public void addData (ArrayList<Documentation> documentations){
      for(int i = 0; i < documentations.size(); i++){
          docData.add(i, documentations.get(i));
      }
  }
}
