package scadinspect.control;

import java.io.File;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * Created by nik, eric on 23.03.17.
 */

// TODO: Write Javadoc comments!!
public class LogHandler extends Logger {

  private final long MAX_FILE_SIZE = 50000;     // Max file size in bytes -> 50.000 characters
  private final String logFileNameBase = "log_";
  private int logFileCount;
  private static Handler fileHandler;

  private LogHandler() throws IOException, BackingStoreException {
    super(Logger.GLOBAL_LOGGER_NAME, null);
    init();
  }

  public static Logger getLogger() throws IOException, BackingStoreException {
    return new LogHandler();
  }

  private void init() {

    // Read and set log level from user preferences
    Preferences userPrefs = Preferences.userRoot().node("DHBW.SCADInspect.Settings");
    Level logLevel = null;

    Integer set_level =  userPrefs.getInt("LOG_LEVEL",0);
    logLevel = Level.parse(set_level.toString());

    this.setLevel(logLevel);


    // set number of log files
    logFileCount = getNumberOfLogFiles(new File("."));

    // increase number of log files if most current is too large
    if (lastLogTooBig()) {
      logFileCount++;
    }
    // suppress console output
    Logger rootLogger = Logger.getLogger("");
    Handler[] handlers = rootLogger.getHandlers();
    if (handlers[0] instanceof ConsoleHandler) {
      rootLogger.removeHandler(handlers[0]);
    }
      /**
       * If log level is set to NONE (=0), no logfile should be created, else create handler pointing to logfile
       */
      if(!(set_level == 0)){
        // setup file output
        fileHandler = null;
        try {
          fileHandler = new FileHandler(logFileNameBase + logFileCount + ".log", true);
        } catch (IOException e) {
          this.warning("Could not get file handler.");
        }
        fileHandler.setFormatter(new SimpleFormatter());


          this.addHandler(fileHandler);
    }

  }

  private int getNumberOfLogFiles(File folder) {
    int numberOfLogs = 0;

    for (final File fileEntry : folder.listFiles()) {
      String fileName = fileEntry.getName();

      if (fileName.startsWith("log_")) {
        String str1 = (fileName.split("_")[1]).split("\\.")[0];
        int currentNumber = Integer.parseInt(str1);

        if (currentNumber > numberOfLogs) {
          numberOfLogs = currentNumber;
        }
      }
    }
    return numberOfLogs;
  }

    /**
     * Method destroying the filehandler when main application is closed
     */
    public static void shutdown() {
    if(fileHandler!=null)
            fileHandler.close();

    }

  private boolean lastLogTooBig() {
    long size = new File(logFileNameBase + logFileCount + ".log").length();
    return size > MAX_FILE_SIZE;
  }
}
