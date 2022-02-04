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

  /**
   * Creates a ChatProtocolException with a throw message and cause.
   *
   * @param message the detail message
   * @param cause   the cause why the exception was thrown
   */
  public CalculatorException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Creates a ChatProtocolException with a throw cause.
   *
   * @param cause the cause why the exception was thrown
   */
  public CalculatorException(Throwable cause) {
    super(cause);
  }
}
