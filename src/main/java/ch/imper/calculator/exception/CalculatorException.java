package ch.imper.calculator.exception;

/**
 * Signalr that a specific action has been conducted when not possible.
 *
 * @author Imper
 * @version 1.0
 */
public class CalculatorException extends Exception {

  /**
   * Creates a ChatProtocolException with a throw message.
   *
   * @param message the detail message
   */
  public CalculatorException(String message) {
    super(message);
  }
}
