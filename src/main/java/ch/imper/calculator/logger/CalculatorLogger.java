package ch.imper.calculator.logger;

import java.io.File;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * This class is responsible for all the input and output data in terms of the system.
 *
 * @author Imper
 * @version 1.0
 */
public class CalculatorLogger {
  private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

  private CalculatorLogger() {
  }

  /**
   * In this method the logs will be created and are ready to put messages in the log file and in the console.
   *
   * @param logName name of the log file
   */
  public static void createLog(String logName) {
    LogManager.getLogManager().reset();
    LOGGER.setLevel(Level.ALL);

    ConsoleHandler consoleHandler = new ConsoleHandler();
    LOGGER.addHandler(consoleHandler);

    try {
      File folder = new File("logs");
      if (!folder.exists()) {
        folder.mkdir();
      }
      FileHandler fileHandler = new FileHandler("logs/" + logName);
      LOGGER.addHandler(fileHandler);
    } catch (IOException e) {
      error(e.getMessage());
    }
  }

  /**
   * Adds an error message to the logger.
   *
   * @param message to add
   */
  public static void error(String message) {
    LOGGER.severe(message);
  }
}
