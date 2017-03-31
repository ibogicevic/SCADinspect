package scadinspect.gui;

import javafx.scene.layout.BorderPane;

/**
 * Created by maikbaumgartner on 30.03.17.
 */
public class GreyPane extends BorderPane{

    public GreyPane(Boolean isTutorial) {
        System.out.print("test");
        this.setVisible(false);
        this.setStyle(
                "-fx-background-color: rgba(105, 105, 105, 0.9);"
        );

        // if isTutorial true, display with help


    }
    public void modalToFront(Boolean var) {
        if (var == true){
            this.setVisible(var);
            this.toFront();
        } else {
            this.setVisible(var);
            this.toBack();
        }
    }
}
