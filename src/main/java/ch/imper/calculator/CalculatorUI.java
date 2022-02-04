package ch.imper.calculator;

import ch.imper.calculator.logger.CalculatorLogger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class starts the UI.
 *
 * @author Imper
 * @version 1.0
 */
public class CalculatorUI extends Application {

  @Override
  public void start(Stage primaryStage) {
    CalculatorLogger.createLog("client.log");
    primaryStage.setTitle("Notenrechner");
    primaryStage.getIcons().add(new Image(String.valueOf(getClass().getResource("images/Calculator-icon.png"))));
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("view/CalculatorView.fxml"));
      primaryStage.setScene(new Scene(loader.load()));
      primaryStage.show();
    } catch (IOException e) {
      CalculatorLogger.error(e.getMessage());
    }
  }
}
