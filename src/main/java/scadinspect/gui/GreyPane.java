package scadinspect.gui;

import javafx.scene.control.Hyperlink;
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
    private Integer step = -1;
    private BottomArea bottomArea= new BottomArea();
    private Hyperlink checkers = new Hyperlink("Open checker docs");
    private Hyperlink prev = new Hyperlink("back");
    private Hyperlink next = new Hyperlink("next");
    private Hyperlink cornerExit = new Hyperlink("Exit Tour");
    private Label messageLabel = new Label();
    private HBox navBar;
    private Label stepLabel = new Label();

    public GreyPane(Boolean isTutorial) {
        //set transparency and colour for the pane
        this.setVisible(false);
        this.setStyle(
                "-fx-background-color: rgba(105, 105, 105, 0.67);"
        );

        // if isTutorial true, display help-modal
        if (isTutorial) {

            //initiate ToolbarArea; transparency
            toolbarArea = new ToolbarArea();
            toolbarArea.setStyle(
                    "-fx-background-color: rgba(105, 105, 105, 0.0);"
            );
            this.setTop(toolbarArea);

            //initiate TabArea TODO: include tabArea in help modal
            TabArea tabArea = new TabArea();
            tabArea.setStyle(
                    "-fx-background-color: rgba(105, 105, 105, 0.0);"
            );


            //modify Hyperlinks
            checkers.setTextFill(Color.WHITE);
            prev.setTextFill(Color.WHITE);
            next.setTextFill(Color.WHITE);
            checkers.setFont(new Font(messageLabel.getFont().getStyle(),17));
            prev.setFont(new Font(messageLabel.getFont().getStyle(),20));
            next.setFont(new Font(messageLabel.getFont().getStyle(),20));
            checkers.setStyle("-fx-underline: true;");
            next.setStyle("-fx-underline: true;");
            prev.setStyle("-fx-underline: true;");
            cornerExit.setStyle("-fx-underline: true;");
            cornerExit.setTextFill(Color.WHITE);
            cornerExit.setFont(new Font(messageLabel.getFont().getStyle(),17));

            //step counter
            stepLabel.setTextAlignment(TextAlignment.CENTER);
            stepLabel.setFont(new Font(messageLabel.getFont().getStyle(),20));
            stepLabel.setTextFill(Color.WHITE);

            //initiate navigation bar
            Pane rightSeparator = new Pane();
            Pane leftSeparator = new Pane();
            Pane bottomSeparator = new Pane();
            HBox.setHgrow(rightSeparator, Priority.ALWAYS);
            HBox.setHgrow(leftSeparator, Priority.ALWAYS);
            HBox.setHgrow(bottomSeparator, Priority.ALWAYS);
            navBar = new HBox(leftSeparator, prev, stepLabel, next, rightSeparator);

            //initiate Center
            BorderPane centerPane = new BorderPane();
            this.setCenter(centerPane);

            //initiate bottomArea; transparency
            bottomArea.setStyle(
                    "-fx-background-color: rgba(105, 105, 105, 0.0);"
            );

            // initiate Message Area
            messageLabel.setMaxWidth(400.0);
            messageLabel.setWrapText(true);
            messageLabel.setTextAlignment(TextAlignment.CENTER);
            messageLabel.setFont(new Font(messageLabel.getFont().getStyle(),20));
            messageLabel.setFont(new Font(messageLabel.getFont().getStyle(),20));
            messageLabel.setTextFill(Color.WHITE);
            centerPane.setCenter(messageLabel);
            centerPane.setBottom(navBar);

            //initiate BottomPane
            bottomPane = new BorderPane();
            BorderPane statusArea = new StatusArea();
            bottomArea.setStyle(
                    "-fx-background-color: rgba(105, 105, 105, 0.0);"
            );
            HBox statusHBox = new HBox(bottomSeparator,checkers, cornerExit);
            statusArea.getChildren().add(statusHBox);
            bottomPane.setCenter(bottomArea);
            bottomPane.setBottom(statusHBox);
            this.setBottom(bottomPane);

            // set button actions
            checkers.setOnAction(e -> {
                exitTour();
                CheckersDialog.openDialog();
            });
            prev.setOnAction(e -> {
                step  -= 1; //decrease step counter
                this.switchTour(step);
            });
            next.setOnAction(e -> {
                step += 1; //increase step counter
                this.switchTour(step);
            });
            cornerExit.setOnAction(e -> {exitTour();});

        }


    }

    private void exitTour() {
        step = -1; // reset counter
        this.modalToFront(false);
        Main.getInstance().greyStack.toBack();
        Main.getInstance().greyStack.setVisible(false);
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
            case -1: {
                toolbarArea.switchButtons(-1);
                bottomArea.switchButtons(0);
                prev.setVisible(false);
                next.setVisible(true);
                stepLabel.setText(step+2 + " of 6");
                messageLabel.setText("Welcome to the QuickTour!\nPlease use the buttons below to navigate " +
                        "through the tour. You can leave the tour in step 6. " +
                        "You can also view the checkers documentation.");
                break;
            }
            case 0: {
                toolbarArea.switchButtons(0);
                bottomArea.switchButtons(0);
                prev.setVisible(true);
                messageLabel.setText("Press \"Open file\" to open an new ScadFile or choose \"Open folder\" from the dropdown menu to open a folder.");
                stepLabel.setText(step+2 + " of 6");
                break;
            }
            case 1: {
                toolbarArea.switchButtons(1);
                prev.setVisible(true);
                messageLabel.setText("Press \"Settings\" to access the settings. For example you can enable auto refresh or set the logging level.");
                bottomArea.switchButtons(0);
                stepLabel.setText(step+2 + " of 6");
                break;
            }
            case 2: {
                toolbarArea.switchButtons(2);
                messageLabel.setText("Press \"Refresh\" to refresh the files.");
                bottomArea.switchButtons(1);
                stepLabel.setText(step+2 + " of 6");
                break;
            }
            case 3: {
                toolbarArea.switchButtons(3);
                messageLabel.setText("Press \"Export\" to export your current work.");
                bottomArea.switchButtons(2);
                next.setVisible(true);
                stepLabel.setText(step+2 + " of 6");
                break;
            }
            case 4: {
                messageLabel.setText("Press \"Close\" to close the current file.");
                bottomArea.switchButtons(3);
                next.setVisible(false);
                stepLabel.setText(step+2 + " of 6");
                break;
            }
        }
    }
}
