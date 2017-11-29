package gui.areas;

import gui.Main;
import gui.Resources;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * Area for general status messages and the progress bar (especially for 'refresh' progress). Can
 * currently be activated by clicking the "Refresh" button in the toolbar. (You might need to "Open"
 * a file first to activate the "Refresh" button.)
 */

public class BottomArea extends BorderPane {


	private final Label textMessage;
	private final ProgressBar progressBar;
	private float progress;

	/** Constructor */
	public BottomArea() {
		

		HBox leftElements = new HBox();

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

	/**
	 * Disable buttons when no project is open
	 * @param value true if buttons shall be disabled (no open project)
	 */
	public void disableButtons(boolean value) {
	}

}
