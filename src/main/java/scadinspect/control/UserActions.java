package scadinspect.control;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

/**
 * Created by nik on 10.03.17.
 */
public class UserActions {
    static public void setup() throws IOException {
        // get the global logger to configure it
        Logger logger = Logger.getLogger(UserActions.class.getName());

        // suppress the logging output to the console
        Logger rootLogger = Logger.getLogger("");
        Handler [] handlers = rootLogger.getHandlers();
        if (handlers[0] instanceof ConsoleHandler) {
            rootLogger.removeHandler(handlers[0]);
        }
    }
}
