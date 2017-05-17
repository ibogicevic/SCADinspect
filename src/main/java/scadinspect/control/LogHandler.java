package scadinspect.control;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * Created by nik, eric, lisa on 23.03.17.
 * CLass used to create new Logger Instance, initialize it and turn it off by default.
 * If Logging level has already been set, restore this information from Preferences.
 * If Log-File size exceeds 50 000 bytes, create new file
 */


public class LogHandler extends Logger {

  private static Handler fileHandler;
  // Max file size in bytes -> 50.000 characters
  private final long MAX_FILE_SIZE = 50000;
  private final String logFileNameBase = "log_";
  private int logFileCount;


  private LogHandler() throws IOException, BackingStoreException {
    super(Logger.GLOBAL_LOGGER_NAME, null);
    init();
  }

  /**
   * method called from other classes to get Handler to access Logger
   *
   * @return instance of LogHandler
   */
  public static Logger getLogger() throws IOException, BackingStoreException {
    return new LogHandler();
  }

  /**
   * Method destroying the filehandler when main application is closed
   */
  public static void shutdown() {
    if (fileHandler != null) {
      fileHandler.close();
    }

  }

  /**
   * Mehtod to initialize the LogHandler
   */
  private void init() {

    // Read and set log level from user preferences
    Preferences userPrefs = Preferences.userRoot().node("DHBW.SCADInspect.Settings");
    Level logLevel = null;

    //if not existing in preferences, default 0 = none
    Integer set_level = userPrefs.getInt("LOG_LEVEL", 0);
    logLevel = Level.parse(set_level.toString());

    this.setLevel(logLevel);

    // set number of log files, in case of creating a new file this is incremented and used for naming
    logFileCount = getNumberOfLogFiles(new File("."));

    // increase number of log files if most current is too large
    if (lastLogTooBig()) {
      logFileCount++;
    }
    // remove default default console handler from root logger to prevent it from outputting to the console
    // get the rootLogger, which is parent of all other Loggers and its handlers
    Logger rootLogger = Logger.getLogger("");
    Handler[] handlers = rootLogger.getHandlers();
    for (Handler handler : handlers) {
      //remove existing default handlers
      rootLogger.removeHandler(handler);
    }
    //If log level is set to NONE (=0), no logfile should be created, else create handler pointing to logfile

    if (set_level != 0) {
      // setup file output
      fileHandler = null;
      try {
        // filename is combination of log_ + nr of next logfile + .log
        fileHandler = new FileHandler(logFileNameBase + logFileCount + ".log", true);
      } catch (IOException e) {
        this.warning("Could not get file handler.");
      }
      fileHandler.setFormatter(new SimpleFormatter());

      // add filehandler to current logger
      this.addHandler(fileHandler);
    }

  }

  /**
   * method used to create name for new logfile, counts all existing logfiles in current folder
   *
   * @return nr of currently existing logfiles
   */
  private int getNumberOfLogFiles(File folder) {
    int numberOfLogs = 0;
    if (folder == null) {
      return 0;
    }
    for (File fileEntry : folder.listFiles()) {
      String fileName = fileEntry.getName();

      if (fileName.startsWith("log_")) {
        // extract nr of logfile from its name
        String str1 = (fileName.split("_")[1]).split("\\.")[0];
        int currentNumber = Integer.parseInt(str1);
        //search for largest number
        if (currentNumber > numberOfLogs) {
          numberOfLogs = currentNumber;
        }
      }
    }
    return numberOfLogs;
  }

  /**
   * method used to check if new logfile should be created
   *
   * @return true if last logfile reached maximum size, else false
   */
  private boolean lastLogTooBig() {
    //get file size in bytes
    long size = new File(logFileNameBase + logFileCount + ".log").length();
    return size > MAX_FILE_SIZE;
  }
}
