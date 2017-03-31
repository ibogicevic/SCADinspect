package scadinspect.gui;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;


import java.util.logging.Level;

/**
 * Area for general status messages and the progress bar (especially for 'refresh' progress).
 * Can currently be activated by clicking the "Refresh" button in the toolbar.
 * (You might need to "Open" a file first to activate the "Refresh" button.)
 * @author David Maier
 * @author Lisa Milius
 */

public class StatusArea extends BorderPane {

  private static StatusArea instance = null;
  private static Label textMessage;
  private static ProgressBar progressBar = null;
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

  /**
   * Function for simulating progress over time, using a thread.
   */
  public void simulateProgress(){
    textMessage.setText("Refreshing...");
    progressBar.setVisible(true);

    progress = 0;

    Thread t = new Thread(new ProgressThread());

    t.start();
  }

  /**
   * Thread for progress simulation.
   */
  private class ProgressThread implements Runnable {

    public void run() {

      for (int i = 0; i < 100; i++){
        progress += 0.01;
        progressBar.setProgress(progress);
        try {
          Thread.sleep(50);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }

      progressBar.setVisible(false);
      //textMessage.setVisible(false);

      Platform.runLater(() -> {
          textMessage.setText("Done refreshing!");
      });

    }
  }

}