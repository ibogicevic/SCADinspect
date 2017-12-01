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
package gui.areas;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;

/**
 * Area for general status messages and the progress bar (especially for 'refresh' progress).
 */
public class BottomArea extends BorderPane {

	private final Label textMessage;
	private final ProgressBar progressBar;
	private float progress;

	/** Constructor */
	public BottomArea() {
		textMessage = new Label();
		progressBar = new ProgressBar(0);
		progressBar.setVisible(false);
		
		setLeft(textMessage);
		setRight(progressBar);
	}

	public String getMessage() {
		return textMessage.getText();
	}

	public float getProgress() {
		return progress;
	}

	public void setProgress(float progress, boolean visible) {
		this.progress = progress;
		Platform.runLater(() -> {
			progressBar.setProgress(progress);
			progressBar.setVisible(visible);
		});
	}

	public void setMessage(String text) {
		Platform.runLater(() -> {
			textMessage.setText(text);
			textMessage.setVisible(true);
		});
	}

}
