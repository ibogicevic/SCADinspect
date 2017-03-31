package scadinspect.gui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

import javafx.scene.text.TextAlignment;

/**
 * Created by maikbaumgartner on 30.03.17.
 * This class supports the application by a grey pane and a help modal
 */
public class GreyPane extends BorderPane{

    private ToolbarArea toolbarArea = new ToolbarArea();
    private BorderPane bottomPane = new BorderPane();
    private Integer step = 0;
    private BottomArea bottomArea= new BottomArea();
    private Button prev = new Button("BACK!");
    private Button next = new Button("GOT IT!");
    private Button exit = new Button("Exit");
    private Label messageLabel = new Label();

    public GreyPane(Boolean isTutorial) {
        //set transparency and colour for the pane
        this.setVisible(false);
        this.setStyle(
                "-fx-background-color: rgba(105, 105, 105, 0.9);"
        );

        // if isTutorial true, display help-modal
        if (isTutorial == true) {

            //initiate ToolbarArea; transparency
            toolbarArea = new ToolbarArea();
            toolbarArea.setStyle(
                    "-fx-background-color: rgba(105, 105, 105, 0.0);"
            );
            this.setTop(toolbarArea);

            //initiate TabArea
            TabArea tabArea = new TabArea();

            //initiate bottomArea; transparency
            bottomArea.setStyle(
                    "-fx-background-color: rgba(105, 105, 105, 0.0);"
            );

            // initiate Message Area
            this.setCenter(messageLabel);
            messageLabel.setMaxWidth(200.0);
            messageLabel.setWrapText(true);
            messageLabel.setTextAlignment(TextAlignment.CENTER);
            messageLabel.setStyle(
                    "-fx-background-color: rgba(365, 365, 365, 0.9);"
            );

            //initiate BottomPane
            bottomPane = new BorderPane();
            Pane separatorPane = new Pane();
            HBox.setHgrow(separatorPane, Priority.ALWAYS);
            HBox switchBox = new HBox(prev, next, separatorPane, exit);

            bottomPane.setCenter(bottomArea);
            bottomPane.setBottom(switchBox);
            this.setBottom(bottomPane);

            // set button actions
            prev.setOnAction(e -> {
                System.out.print("BACK!");
                step  -= 1; //decrease step counter
                this.switchTour(step);
            });
            next.setOnAction(e -> {
                System.out.print("GOT IT!");
                step += 1; //increase step counter
                this.switchTour(step);
            });
            exit.setOnAction(e -> {
                step = 0; // reset counter
                this.modalToFront(false);
                Main.getInstance().greyStack.toBack();
                Main.getInstance().greyStack.setVisible(false);
            });

        }


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

    public void switchTour(Integer step) {

        //switch Step Area

        switch (step){
            case 0: {
                toolbarArea.switchButtons(0);
                bottomArea.switchButtons(0);
                prev.setVisible(false);
                messageLabel.setText("Press this button to open an new ScadFile");
                break;
            }
            case 1: {
                toolbarArea.switchButtons(1);
                prev.setVisible(true);
                messageLabel.setText("Press this button to open a new Folder");
                break;
            }
            case 2: {
                toolbarArea.switchButtons(2);
                bottomArea.switchButtons(0);
                messageLabel.setText("Press this button to access the settings. For example you can enable auto refresh or set the logging level.");
                break;
            }
            case 3: {
                toolbarArea.switchButtons(3);
                bottomArea.switchButtons(1);
                messageLabel.setText("Press this button to refresh your files.");
                break;
            }
            case 4: {
                bottomArea.switchButtons(2);
                messageLabel.setText("Press this button to export your current work.");
                break;
            }
            case 5:{
                bottomArea.switchButtons(3);
                messageLabel.setText("Press the button to close the current file.");
                break;
            }
        }
    }
}
