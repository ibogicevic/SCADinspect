// based on http://stackoverflow.com/questions/37559584/how-to-add-dynamic-columns-and-rows-to-tableview-in-java-fxml

package scadinspect.demo;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.*;

public class DynamicTableView extends Application {

	private static int columnCount = 1;
	private static int rowCount = 5;

	private TableView<ObservableList<String>> tableView = new TableView<>();

	private void addColumn(int colIndex, String columnName) {
		TableColumn<ObservableList<String>, String> column = new TableColumn<>(columnName);
		column.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(colIndex)));
		tableView.getColumns().add(column);
		columnCount++;
	}

	public void start(Stage stage) throws Exception {
		TestDataGenerator dataGenerator = new TestDataGenerator();

		// add columns
		List<String> columnNames = dataGenerator.getNext(3);
		for (int i = 0; i < columnNames.size(); i++) {
			addColumn(i, columnNames.get(i));
		}

		// add data
		for (int i = 0; i < rowCount; i++) {
			tableView.getItems().add(
					FXCollections.observableArrayList(
							dataGenerator.getNext(10*columnCount)
							)
					);
		}

		tableView.setPrefHeight(200);
		tableView.setPrefWidth(800);

		Button addButton = new Button("Add Column");
		addButton.setOnAction(e -> addColumn(columnCount, "Column"+columnCount));
		BorderPane mainPane = new BorderPane();
		mainPane.setTop(addButton);
		mainPane.setCenter(tableView);

		Scene scene = new Scene(mainPane);

		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	private static class TestDataGenerator {
		private static final String[] LOREM = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc tempus cursus diam ac blandit. Ut ultrices lacus et mattis laoreet. Morbi vehicula tincidunt eros lobortis varius. Nam quis tortor commodo, vehicula ante vitae, sagittis enim. Vivamus mollis placerat leo non pellentesque. Nam blandit, odio quis facilisis posuere, mauris elit tincidunt ante, ut eleifend augue neque dictum diam. Curabitur sed lacus eget dolor laoreet cursus ut cursus elit. Phasellus quis interdum lorem, eget efficitur enim. Curabitur commodo, est ut scelerisque aliquet, urna velit tincidunt massa, tristique varius mi neque et velit. In condimentum quis nisi et ultricies. Nunc posuere felis a velit dictum suscipit ac non nisl. Pellentesque eleifend, purus vel consequat facilisis, sapien lacus rutrum eros, quis finibus lacus magna eget est. Nullam eros nisl, sodales et luctus at, lobortis at sem.".split(" ");

		private int curWord = 0;

		public List<String> getNext(int nWords) {
			List<String> words = new ArrayList<>();

			for (int i = 0; i < nWords; i++) {
				if (curWord == Integer.MAX_VALUE) {
					curWord = 0;
				}

				words.add(LOREM[curWord % LOREM.length]);
				curWord++;
			}

			return words;
		}
	}

}
