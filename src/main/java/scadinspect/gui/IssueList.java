package scadinspect.gui;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import scadinspect.data.analysis.Issue;

/**
 * Created by halecket on 17.03.2017.
 * edited by tim walter
 */

public class IssueList {

    private final TableView<Issue> issueList = new TableView<>();
    private final ObservableList<Issue> issueData = FXCollections.observableArrayList();
    private final IssueTableColumn typeCol = new IssueTableColumn("type");
    private final IssueTableColumn lineNumberCol = new IssueTableColumn("#");
    private final IssueTableColumn issueIdentifierCol = new IssueTableColumn("Identifier");
    private final IssueTableColumn descriptionCol = new IssueTableColumn("Description");
    private final IssueTableColumn codeSnippedCol = new IssueTableColumn("Preview");
    private final IssueTableColumn resourcesCol = new IssueTableColumn("Source File");

    public TableView showList() {

        /* Table Configuration */
        issueList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        issueList.setEditable(false);

        typeCol.setStyle("-fx-alignment: CENTER;");
        typeCol.setPercentWidth(0.05);
        typeCol.setMinWidth(typeCol.getPercentWidth());

        lineNumberCol.setStyle("-fx-alignment: CENTER;");
        lineNumberCol.setPercentWidth(0.015);
        lineNumberCol.setMinWidth(lineNumberCol.getPercentWidth());

        issueIdentifierCol.setPercentWidth(0.2);
        issueIdentifierCol.setMinWidth(issueIdentifierCol.getPercentWidth());

        descriptionCol.setPercentWidth(0.2);
        descriptionCol.setMinWidth(descriptionCol.getPercentWidth());

        codeSnippedCol.setPercentWidth(0.2);
        codeSnippedCol.setMinWidth(codeSnippedCol.getPercentWidth());

        resourcesCol.setPercentWidth(0.335);
        resourcesCol.setMinWidth(resourcesCol.getPercentWidth());


        /* Value Factories */
        typeCol.setCellValueFactory(
                new PropertyValueFactory<>("type")
        );

        lineNumberCol.setCellValueFactory(
                new PropertyValueFactory<>("lineNumber")
        );

        issueIdentifierCol.setCellValueFactory(
                new PropertyValueFactory<>("issueIdentifier")
        );

        descriptionCol.setCellValueFactory(
                new PropertyValueFactory<>("description")
        );

        codeSnippedCol.setCellValueFactory(
                new PropertyValueFactory<>("codeSnippet")
        );

        resourcesCol.setCellValueFactory(
                new PropertyValueFactory<>("sourceFile")
        );

        // Set Items and add all columns
        issueList.setItems(issueData);
        issueList.getColumns().addAll(typeCol, lineNumberCol, issueIdentifierCol, descriptionCol, codeSnippedCol, resourcesCol);

        return issueList;
    }

    public void addDataToTable(ArrayList<Issue> issues) {
        for (int i = 0; i < issues.size(); i++) {
            issueData.add(i, issues.get(i));
        }
    }

    public void clearList() {
        issueList.getItems().clear();
    }
}
