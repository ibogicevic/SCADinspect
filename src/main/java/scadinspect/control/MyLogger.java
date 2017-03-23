package scadinspect.control;

import java.io.IOException;
import java.util.logging.*;

/**
 * Created by nik on 23.03.17.
 */
public class MyLogger {
    public Logger logger;

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

        // setup file output
        Handler logAll = new FileHandler("logAll.txt", true);
        logger.addHandler(logAll);
    }
}
