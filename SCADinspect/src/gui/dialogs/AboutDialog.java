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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import util.ResourceLoader;
import gui.MainFrame;

import java.util.ArrayList;
import java.util.List;

public class AboutDialog extends InfoDialog {

	// Text content
	private static final String HEADERTEXT = "SCADinspect v0.1 – Javadoc-like module documentation for your OpenSCAD-Files";

	private static final String LICENSETEXT = "© 2017 Licensed under GNU General Public License v3.0\nby Ivan Bogicevic";

	// list of contributors
	private static final String CONTRIBUTORSTEXT = "Contributors";
	
	private static final String CONTRIBUTORSLISTTEXT =
			""
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

	private static final String THIRDPARTYHEADERTEXT =
			"Third Party Artefacts";
	
	private static final String THIRDPARTYTEXT =
			"SCADinspect uses Icons from the \"Font Awesome\"-Package by Dave Gandy released under the CC BY 3.0 Licence\n";
	
	@Override
	public void init() {
		int lineNo = 0;

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
		grid.add(stackPane, 0, lineNo);

		// license
		Label licenseLabel = new Label(LICENSETEXT);
		grid.add(licenseLabel, 1, lineNo++);
		
		// contributors
		Label contributorsLabel = new Label(CONTRIBUTORSTEXT);
		Label contributorsListLabel = new Label(CONTRIBUTORSLISTTEXT);
		contributorsListLabel.setWrapText(true);
		contributorsListLabel.setMaxWidth(400);
		grid.add(contributorsLabel, 0, lineNo);
		grid.add(contributorsListLabel, 1, lineNo++);

		// third party content
		Label thirdPartyHeaderLabel = new Label(THIRDPARTYHEADERTEXT);
		Label thirdPartyLabel = new Label(THIRDPARTYTEXT);
		thirdPartyLabel.setWrapText(true);
		thirdPartyLabel.setMaxWidth(400);
		grid.add(thirdPartyHeaderLabel, 0, lineNo);
		grid.add(thirdPartyLabel, 1, lineNo++);

		// links
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
		// link to project
		Label githubLabel = new Label("Github-Project: ");
		grid.add(githubLabel, 0, lineNo);
		grid.add(githubLink, 1, lineNo++);
		// link to license
		Label licenseLinkLabel = new Label("Licence: ");
		grid.add(licenseLinkLabel, 0, lineNo);
		grid.add(licenseLink, 1, lineNo++);
		// link to third party content
		Label thirdPartyLinkLabel = new Label("Third Party Content: ");
		grid.add(thirdPartyLinkLabel, 0, lineNo);
		grid.add(flaticonLink, 1, lineNo++);
		
		// add ok button
		ButtonType okButton = new ButtonType("OK", ButtonData.CANCEL_CLOSE);
		this.getDialogPane().getButtonTypes().add(okButton);

		// show dialog		
		this.getDialogPane().setContent(grid);
	}
	
}
