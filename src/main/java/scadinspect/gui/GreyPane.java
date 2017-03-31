package scadinspect.gui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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
                "-fx-background-color: rgba(105, 105, 105, 0.67);"
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
            tabArea.setStyle(
                    "-fx-background-color: rgba(105, 105, 105, 0.0);"
            );

            //initiate navigation bar
            Pane rightSeparator = new Pane();
            Pane leftSeparator = new Pane();
            Pane bottomSeparator = new Pane();
            HBox.setHgrow(rightSeparator, Priority.ALWAYS);
            HBox.setHgrow(leftSeparator, Priority.ALWAYS);
            HBox.setHgrow(bottomSeparator, Priority.ALWAYS);
            HBox navBar = new HBox(leftSeparator, prev, exit,  next, rightSeparator);

            //initiate Center

            BorderPane centerPane = new BorderPane();
            this.setCenter(centerPane);
            //initiate bottomArea; transparency
            bottomArea.setStyle(
                    "-fx-background-color: rgba(105, 105, 105, 0.0);"
            );

            // initiate Message Area
            //this.setCenter(messageLabel);
            messageLabel.setMaxWidth(400.0);
            messageLabel.setWrapText(true);
            messageLabel.setTextAlignment(TextAlignment.CENTER);
            messageLabel.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/ComicSans.ttf"), 20));
            messageLabel.setTextFill(Color.WHITE);
            //centerPane.setTop(tabArea);
            centerPane.setCenter(messageLabel);
            centerPane.setBottom(navBar);


            //initiate separator panes



            //initiate BottomPane
            bottomPane = new BorderPane();
            HBox bottomBar = new HBox(bottomSeparator, exit);
            bottomPane.setCenter(bottomArea);
            bottomPane.setBottom(bottomBar);
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
                messageLabel.setText("Press this button to open an new ScadFile or use Dropdown to open a folder.");
                break;
            }
            case 1: {
                toolbarArea.switchButtons(1);
                prev.setVisible(true);
                messageLabel.setText("Press this button to access the settings. For example you can enable auto refresh or set the logging level.");
                bottomArea.switchButtons(0);
                break;
            }
            case 2: {
                toolbarArea.switchButtons(2);
                messageLabel.setText("Press this button to refresh the files.");
                bottomArea.switchButtons(1);
                break;
            }
            case 3: {
                toolbarArea.switchButtons(3);
                messageLabel.setText("Press this button to close your current work.");
                bottomArea.switchButtons(2);
                break;
            }
            case 4: {
                messageLabel.setText("Press the button to close the current file..");
                bottomArea.switchButtons(3);
                break;
            }
        }
    }
}
