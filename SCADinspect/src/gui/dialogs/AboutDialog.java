/**
 * Opens the about dialog if the About-Button is clicked
 */
package gui.dialogs;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import gui.Main;
import gui.Resources;

import java.util.ArrayList;
import java.util.List;

public class AboutDialog {

	private final static Dialog<Boolean> dialog = new Dialog<>();

	// Text content
	private final String headerText = "SCADinspect - Version 0.1";

	private final String aboutContent =
			"Javadoc-like module documentation for your OpenSCAD-Files \n" +
					"\n" +
					"© 2017 Licensed under GNU General Public License v3.0\n" +
					"For more information visit:";

	// list of contributors
	private final String contributorsContent =
			"Main developer: Ivan Bogicevic \n"
					+ "Contributors:"
					+ "Christoph Auf der Landwehr, "
					+ "Jonas Balsfulland, "
					+ "Maik Baumgartner, "
					+ "Jonas Bernsdorff, "
					+ "Orhan Bilir, "
					+ "Desyon, "
					+ "Jokke Jansen, "
					+ "Malcolm Malam, "
					+ "Lisa Milius, "
					+ "Tom Richter, "
					+ "Romy Römke, "
					+ "Julian Schmidt, "
					+ "Felix Stegmaier, "
					+ "Simon Steinrücken, "
					+ "Tim Walter, "
					+ "and others.";

	private final String thirdPartyContent =
			"Third Party Artefacts\n" +
					"SCADinspect uses Icons from the \"Font Awesome\"-Package by Dave Gandy released under the CC BY 3.0 Licence\n";

	/**
	 * Application startup function
	 */
	public void open() {

		dialog.setTitle("About");
		dialog.setHeaderText(headerText);

		// add ok button
		ButtonType okButton = new ButtonType("OK", ButtonData.CANCEL_CLOSE);
		dialog.getDialogPane().getButtonTypes().addAll(okButton);

		// arrange content
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);

		// display logo
		ImageView logo = Resources.loadIcon("logo2");
		logo.setPreserveRatio(true);
		logo.setFitHeight(25);
		StackPane stackPane = new StackPane(logo);
		stackPane.setAlignment(Pos.CENTER);
		grid.add(stackPane, 0, 0);

		// Display Text
		Label aboutLabel = new Label(aboutContent);
		aboutLabel.setWrapText(true);

		Label contributorsLabel = new Label(contributorsContent);
		contributorsLabel.setWrapText(true);
		contributorsLabel.setMaxWidth(400);
		grid.add(contributorsLabel, 0, 1);

		Label thirdPartyLabel = new Label(thirdPartyContent);
		thirdPartyLabel.setWrapText(true);

		//Links for text content
		List<Hyperlink> links = new ArrayList<>();

		Hyperlink scadinspect = new Hyperlink("https://github.com/ibogicevic/SCADinspect");
		links.add(scadinspect);

		Hyperlink flaticon = new Hyperlink("http://www.flaticon.com/authors/dave-gandy");
		links.add(flaticon);

		Hyperlink creativeCommons = new Hyperlink("http://creativecommons.org/licenses/by/3.0/");
		links.add(creativeCommons);

		for(final Hyperlink hyperlink : links) {
			hyperlink.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent t) {
					Main.getInstance().getHostServices().showDocument(hyperlink.getText());
				}
			});
		}

		VBox upperBox = new VBox();
		upperBox.getChildren().add(aboutLabel);
		upperBox.getChildren().add(scadinspect);
		grid.add(upperBox, 1,0);

		VBox lowerBox = new VBox();
		lowerBox.getChildren().add(thirdPartyLabel);
		lowerBox.getChildren().add(flaticon);
		lowerBox.getChildren().add(creativeCommons);
		grid.add(lowerBox, 1,2);

		dialog.getDialogPane().setContent(grid);
		dialog.showAndWait();
		Main.getInstance().greyStack.toBack();
		Main.getInstance().greyStack.setVisible(false);
	}

	public static void openDialog() {
		AboutDialog aboutDialog = new AboutDialog();
		aboutDialog.open();
	}
}
