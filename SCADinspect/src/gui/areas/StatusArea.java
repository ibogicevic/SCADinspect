package gui.areas;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;

/**
 * Area for general status messages and the progress bar (especially for 'refresh' progress). Can
 * currently be activated by clicking the "Refresh" button in the toolbar. (You might need to "Open"
 * a file first to activate the "Refresh" button.)
 */

public class StatusArea extends BorderPane {

  private final Label textMessage;
  private final ProgressBar progressBar;
  private float progress;

  /**
   * Constructor of StatusArea
   */
  public StatusArea() {
    textMessage = new Label();
    progressBar = new ProgressBar(0);
    progressBar.setVisible(false);
    setLeft(textMessage);
    setRight(progressBar);
    setPadding(new Insets(5f, 30f, 5f, 30f));
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
