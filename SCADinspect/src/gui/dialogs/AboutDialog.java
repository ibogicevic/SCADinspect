/*******************************************************************************
 * SCADinspect – https://github.com/ibogicevic/SCADinspect
 * Copyright (C) 2017 Ivan Bogicevic and others
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
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
import util.ResourceLoader;
import gui.Main;

import java.util.ArrayList;
import java.util.List;

public class AboutDialog {

	private final static Dialog<Boolean> dialog = new Dialog<>();

	// Text content
	private final String headerText = "SCADinspect - Version 0.1";

	private final String descriptionText = "Javadoc-like module documentation for your OpenSCAD-Files";
	
	private final String licenseText = "© 2017 Licensed under GNU General Public License v3.0\n";

	// list of contributors
	private final String contributorsContent =
			"by Ivan Bogicevic \n"
					+ "Contributors: "
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

		// prepare links
		List<Hyperlink> links = new ArrayList<>();
		Hyperlink githubLink = new Hyperlink("https://github.com/ibogicevic/SCADinspect");
		links.add(githubLink);
		Hyperlink flaticonLink = new Hyperlink("http://www.flaticon.com/authors/dave-gandy");
		links.add(flaticonLink);
		Hyperlink licenseLink = new Hyperlink("http://creativecommons.org/licenses/by/3.0/");
		links.add(licenseLink);
		for(final Hyperlink hyperlink : links) {
			hyperlink.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent t) {
					Main.getInstance().getHostServices().showDocument(hyperlink.getText());
				}
			});
		}

		// init dialog
		dialog.setTitle("About");
		dialog.setHeaderText(headerText);

		// pane
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);

		// logo
		ImageView logo = ResourceLoader.loadIcon("logo2");
		logo.setPreserveRatio(true);
		logo.setFitHeight(25);
		StackPane stackPane = new StackPane(logo);
		stackPane.setAlignment(Pos.BASELINE_LEFT);
		grid.add(stackPane, 0, 0);

		// project description
		Label descriptionLabel = new Label(descriptionText);
		descriptionLabel.setWrapText(true);
		grid.add(descriptionLabel, 0, 1);
		
		// link to project
		grid.add(githubLink, 0, 2);
		
		// license
		Label licenseLabel = new Label(licenseText);
		grid.add(licenseLabel, 0, 3);
		grid.add(licenseLink, 0, 4);
		
		// contributors
		Label contributorsLabel = new Label(contributorsContent);
		contributorsLabel.setWrapText(true);
		contributorsLabel.setMaxWidth(400);
		grid.add(contributorsLabel, 0, 5);

		// third party content
		Label thirdPartyLabel = new Label(thirdPartyContent);
		thirdPartyLabel.setWrapText(true);
		grid.add(thirdPartyLabel, 0, 6);
		grid.add(flaticonLink, 0, 7);

		// add ok button
		ButtonType okButton = new ButtonType("OK", ButtonData.CANCEL_CLOSE);
		dialog.getDialogPane().getButtonTypes().addAll(okButton);

		// show dialog		
		dialog.getDialogPane().setContent(grid);
		dialog.showAndWait();
	}

	public static void openDialog() {
		AboutDialog aboutDialog = new AboutDialog();
		aboutDialog.open();
	}
}
