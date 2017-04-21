package scadinspect.gui;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


import java.util.ArrayList;

import scadinspect.data.scaddoc.Module;
import scadinspect.data.scaddoc.ScadDocuFile;
import scadinspect.data.scaddoc.properties.Property;
import scadinspect.data.scaddoc.properties.helper.Pair;

public class DocumentationList {

    //TableView of Documentation List
    private TableView<ObservableList<String>> docList = new TableView<ObservableList<String>>();
    Map<String, String> dataRow = new TreeMap<>();

    //Data structure with associated data for documentation List
    private List<ScadDocuFile> docData;

    private static int columnCount = 0;
    private static int rowCount = 0;


    //Generate the list which is shown withing the table
    public TableView generateList() {
        docList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
       // docData = dummyData();//TODO

        Collection<String> headers = new TreeSet<>();
        if (docData!=null) {
            for (ScadDocuFile file : docData) {
                headers.addAll(file.getAllKeys());
            }
            addColumn(columnCount, "Path");
            addColumn(columnCount, "Module");
            for (String header : headers) {
                addColumn(columnCount, header);
                dataRow.put(header, null);
            }
            loadDocList();
        }
        return docList;
    }

    private void addColumn(int colIndex, String columnName) {
        TableColumn<ObservableList<String>, String> column = new TableColumn<>(columnName);
        column
                .setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(colIndex)));
        docList.getColumns().add(column);
        columnCount++;
    }

    private void loadDocList() {
        // List<ScadDocuFile> docList = Main.getInstance().getDocuFiles();

        for (ScadDocuFile file : docData) {
            for (Module module : file.getModules()) {
                List<String> row = new LinkedList<>();
                row.add(file.getPath().toString());
                row.add("TODO ModuleName");


                for (Property property : module.getProperties()) {

                    String value = "";
                    if (property.getValue() instanceof Pair) {
                        value = ((Pair) property.getValue()).getValue() + " " + ((Pair) property.getValue())
                                .getMetric();
                    } else if (property.getValue() instanceof String) {
                        value = (String) property.getValue();
                    } else if (property.getValue() instanceof Collection) {
                        value = "TODO Multi Property";
                    } else {
                        value = property.getValue().getClass().toGenericString();
                    }
                    dataRow.put(property.getKey(), value);
                }
                row.addAll(dataRow.values());
                for (Property property : module.getProperties()) {
                    dataRow.put(property.getKey(), null);
                }
                addData(row);

            }

        }
    }

    private void addData(List<String> Data) {
        docList.getItems().add(
                FXCollections.observableArrayList(Data)
        );
    }

    public void clearList() {
        docList.getItems().clear();
        docList.getColumns().clear();
docData=dummyData();
        System.out.println(Main.getInstance().getDocuFiles());
        Main.getInstance().tabArea.generateDocTable(this);
    }

    //add the Data (currently as ArrayList) to the observableList
  /*
  public void addData (ArrayList<Documentation> documentations){
    for(int i = 0; i < documentations.size(); i++){
      docData.add(i, documentations.get(i));
    }
  }
  */
    private List<ScadDocuFile> dummyData() {
        List<ScadDocuFile> dummy = new LinkedList<>();
        try {
            dummy.add(new ScadDocuFile(Paths.get("./spec/samples/sample.scad")));
            dummy.add(new ScadDocuFile(Paths.get("./spec/samples/sample2.scad")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //return dummy;

        return Main.getInstance().getDocuFiles();
    }
}
