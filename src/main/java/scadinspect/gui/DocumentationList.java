package scadinspect.gui;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
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

  //Define table columns for Documentation
  private TableColumn partCol = new TableColumn(Messages.getString("DocumentationList.partCol"));
  private TableColumn priceCol = new TableColumn(Messages.getString("DocumentationList.priceCol"));
  private TableColumn amountCol = new TableColumn(Messages.getString("DocumentationList.amountCol"));
  private TableColumn weightCol = new TableColumn(Messages.getString("DocumentationList.weightCol"));
  private TableColumn materialCol = new TableColumn(Messages.getString("DocumentationList.materialCol"));
  private TableColumn urlCol = new TableColumn(Messages.getString("DocumentationList.urlCol"));
    private int columnCount = 0;

    //Generate the list which is shown withing the table
    public TableView generateList() {
        columnCount = 0;
        docList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        Collection<String> headers = new TreeSet<>();
        if (docData != null) {
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
        for (ScadDocuFile file : docData) {
            for (Module module : file.getModules()) {
                List<String> row = new LinkedList<>();
                row.add(file.getPath().toString());
                row.add("DocumentationList.moduleRow");


                for (Property property : module.getProperties()) {

                    String value = "";
                    if (property.getValue() instanceof Pair) {
                        value = ((Pair) property.getValue()).getValue() + " " + ((Pair) property.getValue())
                                .getMetric();
                    } else if (property.getValue() instanceof String) {
                        value = (String) property.getValue();
                    } else if (property.getValue() instanceof Collection) {
                        value = "DocumentationList.mpropertyVal";
                    } else {
                        value = property.getValue().getClass().toGenericString();
                     //   value="Error";
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

    public void refresh() {
        docList.getItems().clear();
        docList.getColumns().clear();
        docData = loadFiles();
        //  System.out.println(Main.getInstance().getFileList());
        Main.getInstance().tabArea.generateDocTable(this);
    }

    private List<ScadDocuFile> loadFiles() {
        List<ScadDocuFile> docData = new LinkedList<>();
        try {
            for (File file : Main.getInstance().getFileList()) {

                docData.add(new ScadDocuFile(file.toPath()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return docData;


    }
}
