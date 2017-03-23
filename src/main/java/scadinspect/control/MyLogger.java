package scadinspect.control;

import java.io.File;
import java.io.IOException;
import java.util.logging.*;

/**
 * Created by nik on 23.03.17.
 */
public class MyLogger {
    public Logger logger;

    public static final long FILE_SIZE = 100;
    private int logFileCount = 0;
    private String logFile;


    public MyLogger() throws IOException {
        // get and configure global logger
        this.logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        logger.setLevel(Level.INFO);

        // suppress console output
        Logger rootLogger = Logger.getLogger("");
        Handler[] handlers = rootLogger.getHandlers();
        if (handlers[0] instanceof ConsoleHandler) {
            rootLogger.removeHandler(handlers[0]);
        }

        // get Number of newest logfile
        logFileCount = getNumberOfLogFiles(new File("."));
        logFile = "logAll_" + logFileCount + ".txt";

        // check if file is already too large, then create new one and increase counter
        long size = new File(logFile).length();

        if (size >= FILE_SIZE) {
            logFileCount++;
            logFile = "logAll_" + logFileCount + ".txt";
        }

        // setup file output
        Handler logAllHandler = new FileHandler(logFile, true);
        logAllHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(logAllHandler);
    }

    private int getNumberOfLogFiles(File folder){
    int nr=0;
            for (final File fileEntry : folder.listFiles()) {

               String fileName=fileEntry.getName();
               if(fileName.startsWith("logAll") && fileName.contains("_")){
                   String str1=(fileName.split("_")[1]).split("\\.")[0];
                    int number=Integer.parseInt(str1);
                    if(number>nr){
                        nr=number;
                    }
               }
            }

        return nr;
    }
}
