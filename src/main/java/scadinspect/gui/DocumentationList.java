package scadinspect.gui;

import javafx.scene.control.cell.PropertyValueFactory;
import scadinspect.data.scaddoc.Documentation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;

public class DocumentationList {
  //TableView of Documentation List
  private TableView docList = new TableView();

  //Data structure with associated data for documentation List
  private final ObservableList<Documentation> docData = FXCollections.observableArrayList();

  //Define table columns for Documentation
  private TableColumn partCol = new TableColumn(Messages.getString("DocumentationList.partCol"));
  private TableColumn priceCol = new TableColumn(Messages.getString("DocumentationList.priceCol"));
  private TableColumn amountCol = new TableColumn(Messages.getString("DocumentationList.amountCol"));
  private TableColumn weightCol = new TableColumn(Messages.getString("DocumentationList.weightCol"));
  private TableColumn materialCol = new TableColumn(Messages.getString("DocumentationList.materialCol"));
  private TableColumn urlCol = new TableColumn(Messages.getString("DocumentationList.urlCol"));

  //Generate the list which is shown withing the table
  public TableView generateList () {
    addData(generateDummyData());

    //add Data to columns
    partCol.setCellValueFactory(
        new PropertyValueFactory<Documentation, Integer>("part")
    );

    priceCol.setCellValueFactory(
        new PropertyValueFactory<Documentation,String>("price")
    );

    amountCol.setCellValueFactory(
        new PropertyValueFactory<Documentation, String>("amount")
    );

    weightCol.setCellValueFactory(
        new PropertyValueFactory<Documentation, String>("weight")
    );

    materialCol.setCellValueFactory(
        new PropertyValueFactory<Documentation, String>("material")
    );

    urlCol.setCellValueFactory(
        new PropertyValueFactory<Documentation, String>("url")
    );

    docList.setItems(docData);



    docList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    docList.setItems(docData);
    docList.getColumns().addAll(partCol, priceCol, amountCol, weightCol, materialCol, urlCol);
    return docList;
  }

  //add the Data (currently as ArrayList) to the observableList
  public void addData (ArrayList<Documentation> documentations){
    for(int i = 0; i < documentations.size(); i++){
      docData.add(i, documentations.get(i));
    }
  }

  //generate Dummy Data. This function will later on be replaced with the actual data.
  public ArrayList<Documentation> generateDummyData () {
    ArrayList<Documentation> documentations = new ArrayList<>();

    for(int i = 0; i < 10; i++){
      documentations.add(i, new Documentation("Engine " + i, "Metal " + i, "www.coole-teile"+i+".com", 200*(i+1)^i, i+2, i+20));
    }
    return documentations;
  }
}
