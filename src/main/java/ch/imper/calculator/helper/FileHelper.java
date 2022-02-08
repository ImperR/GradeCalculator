package ch.imper.calculator.helper;

import ch.imper.calculator.CalculatorUI;
import ch.imper.calculator.logger.CalculatorLogger;
import ch.imper.calculator.uni.Module;

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
    File template = new File(Objects.requireNonNull(CalculatorUI.class.getResource(
        String.format("%s%s/Year%d.txt", TEMPLATE_LOCATION, location, year))).getPath());
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

  public static List<String> getLinesFromList(List<Module> moduleList) {
    List<String> lines = new ArrayList<>();
    for (Module module : moduleList) {
      lines.add(String.format("%s; %d; %s; %.1f;", module.getName(), module.getCredits(),
          module.getGroup(), module.getGrade()));
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
