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
package gui.dialogs;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import util.ResourceLoader;
import gui.MainFrame;

import java.util.ArrayList;
import java.util.List;

public class AboutDialog extends InfoDialog {

	// Text content
	private static final String HEADERTEXT = "SCADinspect v0.1";

	private static final String DESCRIPTIONTEXT = "Javadoc-like module documentation for your OpenSCAD-Files";
	
	private static final String LICENSETEXT = "© 2017 Licensed under GNU General Public License v3.0\n";

	// list of contributors
	private static final String CONTRIBUTORSTEXT =
			"by Ivan Bogicevic \n\n"
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
					+ "and many others.";

	private static final String THIRDPARTYTEXT =
			"Third Party Artefacts\n" +
					"SCADinspect uses Icons from the \"Font Awesome\"-Package by Dave Gandy released under the CC BY 3.0 Licence\n";
	
	@Override
	public void init() {
		// prepare links
		List<Hyperlink> links = new ArrayList<>();
		Hyperlink githubLink = new Hyperlink("https://github.com/ibogicevic/SCADinspect");
		links.add(githubLink);
		Hyperlink flaticonLink = new Hyperlink("http://www.flaticon.com/authors/dave-gandy");
		links.add(flaticonLink);
		Hyperlink licenseLink = new Hyperlink("https://www.gnu.org/licenses/gpl-3.0.en.html");
		links.add(licenseLink);
		for (final Hyperlink hyperlink : links) {
			hyperlink.setBorder(Border.EMPTY);
			hyperlink.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent t) {
					MainFrame.getInstance().getHostServices().showDocument(hyperlink.getText());
				}
			});
		}

		// init dialog
		this.setTitle("About");
		this.setHeaderText(HEADERTEXT);

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
		Label descriptionLabel = new Label(DESCRIPTIONTEXT);
		descriptionLabel.setWrapText(true);
		grid.add(descriptionLabel, 0, 1);
		
		// link to project
		grid.add(githubLink, 0, 2);
		
		// license
		Label licenseLabel = new Label(LICENSETEXT);
		grid.add(licenseLabel, 0, 3);
		grid.add(licenseLink, 0, 4);
		
		// contributors
		Label contributorsLabel = new Label(CONTRIBUTORSTEXT);
		contributorsLabel.setWrapText(true);
		contributorsLabel.setMaxWidth(400);
		grid.add(contributorsLabel, 0, 5);

		// third party content
		Label thirdPartyLabel = new Label(THIRDPARTYTEXT);
		thirdPartyLabel.setWrapText(true);
		grid.add(thirdPartyLabel, 0, 6);
		grid.add(flaticonLink, 0, 7);

		// add ok button
		ButtonType okButton = new ButtonType("OK", ButtonData.CANCEL_CLOSE);
		this.getDialogPane().getButtonTypes().add(okButton);

		// show dialog		
		this.getDialogPane().setContent(grid);
	}
	
}
