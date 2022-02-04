package ch.imper.calculator;


import ch.imper.calculator.controller.CalculatorViewController;
import javafx.application.Application;

/**
 * This class starts the whole application
 *
 * @author Imper
 * @version 1.0
 */
public class CalculatorApp {
  public static void main(String[] args) {
    Application.launch(CalculatorUI.class, args);
  }
}
