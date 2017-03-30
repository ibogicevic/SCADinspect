package scadinspect.control;

import java.io.File;
import java.io.IOException;
import java.util.logging.*;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import scadinspect.gui.Main;

/**
 * Created by nik on 23.03.17.
 */
public class LogHandler {

    private final Logger logger;
    private final long MAX_FILE_SIZE = 50;
    private final String logFileNameBase = "log_";
    private int logFileCount = 0;

    public LogHandler() throws IOException {
        // get and configure global logger
        this.logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        Preferences userPrefs = Preferences.userRoot().node("DHBW.SCADInspect.Settings");
        Level logLevel = Level.INFO;
        try {
            if (userPrefs.nodeExists("LOG_LEVEL")) {
                logLevel = Level.parse(userPrefs.get("LOG_LEVEL", "INFO"));
            }
        } catch (BackingStoreException | IllegalArgumentException e) {
            Main.logger.log(Level.WARNING, "Unable to set log level", e);
        }
        logger.setLevel(logLevel);

        // suppress console output
        Logger rootLogger = Logger.getLogger("");
        Handler[] handlers = rootLogger.getHandlers();
        if (handlers[0] instanceof ConsoleHandler) {
            rootLogger.removeHandler(handlers[0]);
        }

        // set number of log files
        logFileCount = getNumberOfLogFiles(new File("."));

        // increase number of log files if most current is too large
        if (lastLogTooBig()) {
            logFileCount++;
        }

        // setup file output
        Handler fileHandler = new FileHandler(logFileNameBase + logFileCount + ".txt", true);
        fileHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(fileHandler);
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

    public Logger getLogger() {
        return logger;
    }

    public void setLogLevel(Level level) {

    }

    private boolean lastLogTooBig() {
        long size = new File(logFileNameBase + logFileCount + ".txt").length();
        return size > MAX_FILE_SIZE;
    }
}
