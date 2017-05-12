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

/**
 * author simon
 */
public class DocumentationList {

  Map<String, String> headerValueMap = new TreeMap<>();
  //TableView of Documentation List
  private TableView<ObservableList<String>> docTableView = new TableView<ObservableList<String>>();
  //Data structure with associated data for documentation List
  private List<ScadDocuFile> parsedDocFiles;

  private int columnCount = 0;

  /**
   * Generates Table from List of parsedDocFiles
   *
   * @return the View of the Table
   */
  public TableView generateTableView() {
    columnCount = 0;
    docTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    //sorted list of all headers
    Collection<String> headerList = new TreeSet<>();

    if (parsedDocFiles != null) {
      //scan through all files and save unique headers
      for (ScadDocuFile file : parsedDocFiles) {
        headerList.addAll(file.getAllKeys());
      }

      //first column should be Path
      addColumn(columnCount, "Path");

      //add all other headers (sorted by treeset)
      for (String header : headerList) {
        addColumn(columnCount, header);

        //initialize map with headers mapped to nothing
        headerValueMap.put(header, null);
      }

      //
      generateRows();
    }

    //resize columns, has to be called after all columns have been added
    docTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    return docTableView;
  }

  /**
   * Adds a new column to the Table View
   * Increases the column count
   *
   * @param colIndex index of the new column
   * @param columnName displayed in Header
   */
  private void addColumn(int colIndex, String columnName) {
    // create new column
    TableColumn<ObservableList<String>, String> column = new TableColumn<>(columnName);
    //set cell value factory to allow dynamic additions
    column
        .setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(colIndex)));

    //add the creted colum to the list of existing columns
    docTableView.getColumns().add(column);
    columnCount++;
  }

  /**
   * scans over all parsedDocFiles
   * over all modules
   * to add every property of the module in one row
   *
   * every row equals one module
   */
  private void generateRows() {
    for (ScadDocuFile file : parsedDocFiles) {
      //every module for every file gets its own row
      for (Module module : file.getModules()) {
        //contains all values of one row, empty cells have to be null or ""
        List<String> row = new LinkedList<>();

        //first element is Path
        row.add(file.getPath().toString());

        //scan over all properties of a module
        for (Property property : module.getProperties()) {
          String value;
          //get the string value of the property
          if (property.getValue() instanceof Pair) {
            value = property.getValue().toString();
          } else if (property.getValue() instanceof String) {
            value = (String) property.getValue();
          } else if (property.getValue() instanceof Collection) {
            value = property.getValue().toString().replaceAll("\\[|]", "");
          } else {  //for debugging if one implementation is missing
            value = property.getValue().getClass().toGenericString();
          }
          //the value and key of the property is added to the header map to keep sorting and uniqueness
          headerValueMap.put(property.getKey(), value);
        }
        //add all values of the module to row, sorted my use of treemap
        row.addAll(headerValueMap.values());
        //add row to table
        addRow(row);

        //clear header map for next iteration
        for (Property property : module.getProperties()) {
          headerValueMap.put(property.getKey(), null);
        }
      }
    }
  }

  /**
   * Helper function to add a row of data to table
   *
   * @param Data row to be added, if longer than header data is truncated
   */
  private void addRow(List<String> Data) {
    docTableView.getItems().add(
        FXCollections.observableArrayList(Data)
    );
  }

  /**
   * Refreshes the whole table
   */
  public void refresh() {
    //clear existing data
    docTableView.getItems().clear();
    docTableView.getColumns().clear();

    //load new fileList and parse it
    parsedDocFiles = loadFiles();
    //recreate Table
    Main.getInstance().tabArea.generateDocTable(this);
  }

  /**
   * Loads a list of Paths from Main (FileList)
   * Saves parsed Files in Main (internal file format)
   *
   * @return a List of parsed Files (internal file format)
   */
  private List<ScadDocuFile> loadFiles() {
    List<ScadDocuFile> parsedFiles = new LinkedList<>();
    try {
      for (File file : Main.getInstance().getFileList()) {
        //parse every file in fileList, save it
        parsedFiles.add(new ScadDocuFile(file.toPath()));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    //set DocuFiles in Main and return parsedFiles
    Main.getInstance().setDocuFiles(parsedFiles);
    return parsedFiles;
  }
}
