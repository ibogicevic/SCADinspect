package scadinspect.gui;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Startup JavaFX frame
 *
 * @author ivan
 */
public class Main extends Application {

    /**
     * Name of the application *
     */
    public static final String APPNAME = "SCADinspect";

    /**
     * Location of the resource files *
     */
    public static final String RESOURCES_DIR = "/resources/";

    /**
     * Ratio between window height and screen height *
     */
    private static final double WINDOW_HEIGHT = 0.25;

    // singleton pattern
    private static Main instance;

    public static Main getInstance() {
        return instance;
    }

    // gui areas
    public ToolbarArea toolbarArea = new ToolbarArea();
    //public ExplorerArea explorerArea = new ExplorerArea();
    public TabArea tabArea = new TabArea();
    // public InspectorArea inspectorArea = new InspectorArea();
    // public MessagesArea messagesArea = new MessagesArea();
    public StatusArea statusArea = new StatusArea();
    public BottomArea bottomArea = new BottomArea();

    /**
     * root path to current open project, null if no project open
     */
    public String currentProject = null;

    // remember stage for subwindows
    private BorderPane mainPane;
    private StackPane mainStack;
    private BorderPane greyPane;
    private Stage primaryStage;

    public Stage getPrimaryStage() {
        return this.primaryStage;
    }

    @Override
    /**
     * Application startup function
     */
    public void start(Stage primaryStage) {
        // remember singleton instance (instantiated by javafx)
        Main.instance = this;

        // remember stage for subwindows
        this.primaryStage = primaryStage;

        // Documentation and Issues Tabulators
        BorderPane tabPane = new BorderPane();
        tabPane.setCenter(tabArea);

        // Status and BottomArea
        BorderPane bottomPane = new BorderPane();
        bottomPane.setCenter(bottomArea);
        bottomPane.setBottom(statusArea);

        // add all areas
        BorderPane mainPane = new BorderPane();
        mainPane.setTop(toolbarArea);
        mainPane.setCenter(tabPane);
        mainPane.setBottom(bottomPane);

        // main stack
        mainStack = new StackPane();
        greyPane = new BorderPane();
        greyPane.setStyle(
                "-fx-background-color: rgba(105, 105, 105, 0.9);"
        );
        mainStack.getChildren().addAll(greyPane, mainPane);

        // show main pane
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Scene scene = new Scene(mainStack, screenBounds.getWidth(), WINDOW_HEIGHT * screenBounds.getHeight(), true);
        primaryStage.setTitle(APPNAME);
        primaryStage.setScene(scene);
        primaryStage.setY(0.7 * screenBounds.getHeight());
        primaryStage.setX(0);
        primaryStage.show();

        // load default workspace
        //ProjectHandling.openProject("");
    }

    /**
     * Check whether a project file or project folder is open
     *
     * @return true if a project is open, else false
     */
    public boolean isProjectOpen() {
        return (currentProject != null);
    }

    public void modalToFront(Boolean var) {
        if (var == true){
            greyPane.toFront();
        } else {
            greyPane.toBack();
        }
    }

    /**
     * Main control loop, gives control to JavaFX
     *
     * @param args unused
     */
    public static void main(String[] args) {
        launch(args);
    }
}
