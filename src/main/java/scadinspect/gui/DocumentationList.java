package gui;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Created by halecket on 17.03.2017.
 */
public class DocumentationList {
  TableView documentationList = new TableView();
  TableColumn documentation = new TableColumn("Documentation");

  public TableView showList () {
    documentation.setPrefWidth(100);
    documentationList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    documentationList.getColumns().add(documentation);
    return documentationList;
  }
}
