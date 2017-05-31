/* 
*   SCADinspect – Javadoc-like module documentaion for your SCAD-files
*   www.github
*   
*   Copyright (C) 2017 Ivan Bogicevic and others
*    
*   This program is free software: you can redistribute it and/or modify
*   it under the terms of the GNU General Public License as published by
*   the Free Software Foundation, either version 3 of the License, or
*   (at your option) any later version.
*
*   This program is distributed in the hope that it will be useful,
*   but WITHOUT ANY WARRANTY; without even the implied warranty of
*   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*   GNU General Public License for more details.
*
*   You should have received a copy of the GNU General Public License
*   along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package scadinspect.gui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import scadinspect.control.LogHandler;
import scadinspect.data.scaddoc.ScadDocuFile;
import scadinspect.gui.areas.BottomArea;
import scadinspect.gui.areas.StatusArea;
import scadinspect.gui.areas.TabArea;
import scadinspect.gui.areas.ToolbarArea;

/**
 * Startup JavaFX frame
 */
public class Main extends Application {

	/**
	 * Name of the application *
	 */
	public static final String APPNAME = "SCADinspect";

	/**
	 * Location of the resource files *
	 */
	public static Logger logger = null;

	/**
	 * Ratio between window height and screen height *
	 */
	private static final double WINDOW_HEIGHT = 0.33;

	/**
	 * Pre-configured logger that outputs to  *
	 */
	private LogHandler logHandler;


	// singleton pattern
	private static Main instance;

	public static Main getInstance() {
		return instance;
	}

	// gui areas
	public ToolbarArea toolbarArea = new ToolbarArea();
	public TabArea tabArea = new TabArea();
	public StatusArea statusArea = new StatusArea();
	public BottomArea bottomArea = new BottomArea();

	// list of open scad-files
	private List<File> fileList = new ArrayList<>();

	/**
	 * root path to current open project, null if no project open
	 */
	public String currentProject = null;

	// remember stage for subwindows
	private StackPane mainStack;
	public StackPane greyStack;
	public GreyPane helpPane;
	public GreyPane greyPane;
	private Stage primaryStage;

	public Stage getPrimaryStage() {
		return this.primaryStage;
	}
	/**
	 * Gives the list of currently open scad-files
	 * @return list of scad-files currently open
	 */
	public List<File> getFileList() {
		return fileList;
	}

	private List<ScadDocuFile> docuFiles;

	/**
	 * Gives the list of documentation data files
	 * @return list of documentation data files
	 */
	public List<ScadDocuFile> getDocuFiles() {
		return docuFiles;
	}

	/**
	 * Sets the list of documentation data files
	 */
	public void setDocuFiles(List<ScadDocuFile> docuFiles) {
		this.docuFiles = docuFiles;
	}

	@Override
	/**
	 * Application startup function
	 */
	public void start(Stage primaryStage) {
		System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tc] %4$s: %5$s%n");
		try {
			logger = LogHandler.getLogger();
		} catch (IOException|BackingStoreException e) {
			//TODO Use logger
			e.printStackTrace();
		}

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
		greyPane = new GreyPane(false);
		helpPane = new GreyPane(true);
		greyStack = new StackPane(greyPane, helpPane);
		greyStack.setVisible(false);
		mainStack.getChildren().addAll(greyStack, mainPane);

		// show main pane
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		Scene scene = new Scene(mainStack, screenBounds.getWidth(), WINDOW_HEIGHT * screenBounds.getHeight(), true);
		primaryStage.setTitle(APPNAME);
		primaryStage.setScene(scene);
		primaryStage.setY(0.7 * screenBounds.getHeight());
		primaryStage.setX(0);
		primaryStage.show();

		logger.log(Level.INFO,"successfully started");

		/**
		 * Necessary for destroying the logfilehandler before closing the application
		 */
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			logHandler.shutdown();
		}, "Shutdown-thread"));
	}

	/**
	 * Check whether a project file or project folder is open
	 *
	 * @return true if a project is open, else false
	 */
	public boolean isProjectOpen() {
		return (currentProject != null);
	}

	/**
	 * Sets the current project path
	 * @param currentProject
	 */
	public void setCurrentProject(String currentProject){
		this.currentProject=currentProject;
	}

	public LogHandler getLogHandler() {
		return logHandler;
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
