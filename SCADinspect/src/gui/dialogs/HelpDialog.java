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

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonBar.ButtonData;

public class HelpDialog extends Dialog<Boolean> {

	public static void openDialog() {
		HelpDialog helpDialog = new HelpDialog();
		// add ok button
		ButtonType okButton = new ButtonType("OK", ButtonData.CANCEL_CLOSE);
		helpDialog.getDialogPane().getButtonTypes().add(okButton);
		// show dialog
		helpDialog.showAndWait();
	}
}
