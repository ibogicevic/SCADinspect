package scadinspect.gui;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import scadinspect.control.MyLogger;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    /**
     * Pre-configured logger that outputs to  *
     */
    public static Logger logger = null;

    // singleton pattern
    private static Main instance;

    public static Main getInstance() {
        return instance;
    }


  // gui areas
	public ToolbarArea toolbarArea = new ToolbarArea();
	//public ExplorerArea explorerArea = new ExplorerArea();
	public EditorArea editorArea = new EditorArea();;
	public InspectorArea inspectorArea = new InspectorArea();
	public MessagesArea messagesArea = new MessagesArea();
	public StatusArea statusArea = new StatusArea();

	/** root path to current open project, null if no project open*/
	public String currentProject = null;
	
	// remember stage for subwindows
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
		
		// lr2SplitPane
		SplitPane lr2SplitPane = new SplitPane();
		lr2SplitPane.getItems().addAll(editorArea, inspectorArea);
		lr2SplitPane.setDividerPositions(0.8f, 0.2f);
		
		// tdSplitPane
		SplitPane tdSplitPane = new SplitPane();
		tdSplitPane.setOrientation(Orientation.VERTICAL);
		tdSplitPane.getItems().addAll(lr2SplitPane, messagesArea);
		tdSplitPane.setDividerPositions(0.9f, 0.1f);
		
		// lrSplitPane
		SplitPane lrSplitPane = new SplitPane();
		//lrSplitPane.getItems().addAll(explorerArea, tdSplitPane);
		lrSplitPane.setDividerPositions(0.2f, 0.8f);
		
		// add all areas
		BorderPane mainPane = new BorderPane();
		mainPane.setTop(toolbarArea);
		mainPane.setCenter(lrSplitPane);
		mainPane.setBottom(statusArea);

		// show main pane
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		Scene scene = new Scene(mainPane, screenBounds.getWidth(), WINDOW_HEIGHT*screenBounds.getHeight(), true);
		primaryStage.setTitle(APPNAME);
		primaryStage.setScene(scene);
		primaryStage.setY((0.5-WINDOW_HEIGHT)*screenBounds.getHeight());
		primaryStage.show();
		
	}

	/**
	 * Check whether a project file or project folder is open
	 * @return true if a project is open, else false
	 */
	public boolean isProjectOpen() {
		return (currentProject != null);
	}
	
	public void setCurrentProject(String currentProject){
		this.currentProject=currentProject;
	}
	
	/**
	 * Main control loop, gives control to JavaFX
	 * @param args unused
	 */
	public static void main(String[] args) {
            launch(args);
	}
}
