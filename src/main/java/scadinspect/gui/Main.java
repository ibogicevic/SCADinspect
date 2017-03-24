package scadinspect.gui;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Startup JavaFX frame
 * @author ivan
 */
public class Main extends Application {

	/** Name of the application **/
	public final static String APPNAME = "SCADinspect";

	/** Location of the resource files **/
	public final static String RESOURCES_DIR = "/resourcesCol/";

	/** Ratio between window height and screen height **/
	private final static double WINDOW_HEIGHT = 0.25;

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

		// Documentation and Issues Tabulators
		BorderPane tabPane = new BorderPane();
		tabPane.setCenter(tabArea);

		// add all areas
		BorderPane mainPane = new BorderPane();
		mainPane.setTop(toolbarArea);
		mainPane.setCenter(tabPane);
		mainPane.setBottom(statusArea);

		// show main pane
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		Scene scene = new Scene(mainPane, screenBounds.getWidth(), WINDOW_HEIGHT*screenBounds.getHeight(), true);
		primaryStage.setTitle(APPNAME);
		primaryStage.setScene(scene);
		primaryStage.setY(0.7* screenBounds.getHeight());
		primaryStage.setX(0);
		primaryStage.show();

		// load default workspace
		//ProjectHandling.openProject("");
	}

	/**
	 * Check whether a project file or project folder is open
	 * @return true if a project is open, else false
	 */
	public boolean isProjectOpen() {
		return (currentProject != null);
	}

	/**
	 * Main control loop, gives control to JavaFX
	 * @param args unused
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
