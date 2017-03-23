package scadinspect.control;

import java.io.IOException;
import java.util.logging.*;

/**
 * Created by nik on 23.03.17.
 */
public class MyLogger {
    public static void setup() throws IOException {
        // get and configure global logger
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        logger.setLevel(Level.INFO);

        // suppress console output
        Logger rootLogger = Logger.getLogger("");
        Handler[] handlers = rootLogger.getHandlers();
        if (handlers[0] instanceof ConsoleHandler) {
            rootLogger.removeHandler(handlers[0]);
        }

        Handler logAll = new FileHandler("logAll.txt");
        logger.addHandler(logAll);
    }
}
