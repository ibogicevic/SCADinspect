package scadinspect.control;

import java.io.File;
import java.io.IOException;
import java.util.logging.*;

/**
 * Created by nik on 23.03.17.
 */
public class MyLogger {
    public Logger logger;

    public static final long FILE_SIZE = 1000;
    private int countLogFiles=0;


    public MyLogger() throws IOException {
        // get and configure global logger
        this.logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        logger.setLevel(Level.INFO);

        //get Number of newest logfile
        countLogFiles=getNumberOfLogFiles(new File("."));

        String logFile="logAll_"+countLogFiles+".txt";
        // suppress console output
        Logger rootLogger = Logger.getLogger("");
        Handler[] handlers = rootLogger.getHandlers();
        if (handlers[0] instanceof ConsoleHandler) {
            rootLogger.removeHandler(handlers[0]);
        }

        // setup file output
        Handler logAll = new FileHandler(logFile, true);
        long size=new File(logFile).length();

        //check if file is already too large, then create new one and increase counter
        if(size>=FILE_SIZE){
            countLogFiles=countLogFiles+1;
            String fileName="logAll_"+countLogFiles+".txt";
            logAll=new FileHandler(fileName,true);
        }

        logger.addHandler(logAll);
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
