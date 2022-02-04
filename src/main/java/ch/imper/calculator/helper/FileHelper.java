package ch.imper.calculator.helper;

import ch.imper.calculator.CalculatorUI;
import ch.imper.calculator.controller.CalculatorViewController;
import ch.imper.calculator.uni.Module;
import ch.imper.calculator.logger.CalculatorLogger;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * This class loads the templates from the files for the UI.
 *
 * @author Imper
 * @version 1.0
 */
public class FileHelper {
  private static final String TEMPLATE_LOCATION = "templates/";

  public static List<String> loadTemplate(String location, int year) {
    List<String> lines = new ArrayList<>();
    File template = new File(Objects.requireNonNull(CalculatorUI.class.getResource(TEMPLATE_LOCATION + location + "/Year" + year + ".txt")).getPath());
    try {
      Scanner scanner = new Scanner(template, StandardCharsets.ISO_8859_1);
      while (scanner.hasNextLine()) {
        lines.add(scanner.nextLine());
      }
    } catch (IOException e) {
      CalculatorLogger.error(e.getMessage());
    }
    return lines;
  }

  public static List<String> loadFile(String filePath) {
    List<String> lines = new ArrayList<>();
    File template = new File(Objects.requireNonNull(filePath));
    try {
      Scanner scanner = new Scanner(template, StandardCharsets.ISO_8859_1);
      while (scanner.hasNextLine()) {
        lines.add(scanner.nextLine());
      }
    } catch (IOException e) {
      CalculatorLogger.error(e.getMessage());
    }
    return lines;
  }

  public static List<String> loadFile(File file) {
    List<String> lines = new ArrayList<>();
    try {
      Scanner scanner = new Scanner(file, StandardCharsets.ISO_8859_1);
      while (scanner.hasNextLine()) {
        lines.add(scanner.nextLine());
      }
    } catch (IOException e) {
      CalculatorLogger.error(e.getMessage());
    }
    return lines;
  }

  public static List<String> getLinesFromList(List<HBox> moduleList) {
    List<String> lines = new ArrayList<>();
    String name;
    int credits;
    String group;
    double grade;
    for (HBox hBox : moduleList) {
      name = ((TextField) hBox.getChildren().get(0)).getText();
      credits = ((ComboBox<Integer>) hBox.getChildren().get(1)).getValue();
      group = ((ComboBox<String>) hBox.getChildren().get(2)).getValue();
      grade = ((ComboBox<Double>) hBox.getChildren().get(3)).getValue();
      lines.add(String.format("%s; %d; %s; %.1f;", name, credits, group, grade));
    }
    return lines;
  }

  public static boolean saveFile(File file, List<String> lines) throws IOException {
    file.createNewFile();
    PrintWriter pw = new PrintWriter(new FileOutputStream(file));
    for (String line : lines) {
      pw.println(line);
    }
    pw.close();
    return true;
  }
}
