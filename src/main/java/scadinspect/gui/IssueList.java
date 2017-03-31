package scadinspect.gui;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Screen;
import scadinspect.data.analysis.Issue;

/**
 * Created by halecket on 17.03.2017.
 * edited by tim walter
 */

public class IssueList {

    private TableView<Issue> issueList = new TableView<>();

    private final ObservableList<Issue> issueData = FXCollections.observableArrayList();

    private IssueTableColumn typeCol = new IssueTableColumn("type");
    private IssueTableColumn lineNumberCol = new IssueTableColumn("#");
    private IssueTableColumn issueIdentifierCol = new IssueTableColumn("Identifier");
    private IssueTableColumn descriptionCol = new IssueTableColumn("Description");
    private IssueTableColumn codeSnippedCol = new IssueTableColumn("Preview");
    private IssueTableColumn resourcesCol = new IssueTableColumn("Source File");

    public TableView showList() {

        /* Dummy Data */
        ArrayList<Issue> issues = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Issue curr = new Issue(Issue.issueType.WARNING, "C:\\Users\\IBM_ADMIN\\Documents\\GitHub\\SCADinspect\\TestCorpus\\Examples\\Basics\\CSG.scad", 15+i, "randomIdentifier" + i, "description" + i);
            issues.add(i, curr);
        }
        addDataToTable(issues);


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
                new PropertyValueFactory<Issue, String>("type")
        );

        lineNumberCol.setCellValueFactory(
                new PropertyValueFactory<Issue, Integer>("lineNumber")
        );

        issueIdentifierCol.setCellValueFactory(
                new PropertyValueFactory<Issue, String>("issueIdentifier")
        );

        descriptionCol.setCellValueFactory(
                new PropertyValueFactory<Issue, String>("description")
        );

        codeSnippedCol.setCellValueFactory(
                new PropertyValueFactory<Issue, String>("codeSnippet")
        );

        resourcesCol.setCellValueFactory(
                new PropertyValueFactory<Issue, String>("sourceFile")
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
