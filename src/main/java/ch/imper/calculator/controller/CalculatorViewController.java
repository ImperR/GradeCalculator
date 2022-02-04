package ch.imper.calculator.controller;

import ch.imper.calculator.exception.CalculatorException;
import ch.imper.calculator.module.Module;
import ch.imper.calculator.calculator.Calculator;
import ch.imper.calculator.helper.FileHelper;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class controls the UI input from the user.
 *
 * @author Imper
 * @version 1.0
 */
public class CalculatorViewController {
  public static final String SPLIT_REGEX = ";";
  private static final String IT = "IT";
  private static final int AMOUNT_OF_CREDITS = 30;
  private final FileChooser fileChooser;
  private final List<HBox> moduleList;
//  private final List<Module> modules;
  private final Calculator calculator;
  private int modulePerSemester;

  @FXML
  private VBox moduleBox;
  @FXML
  private ComboBox<Integer> semesterBox;
  @FXML
  private Label gradeAverage;
  @FXML
  private Label group1AverageGrade;
  @FXML
  private Label group2AverageGrade;
  @FXML
  private Label group1AverageLabel;
  @FXML
  private Label group2AverageLabel;


  public CalculatorViewController() {
    moduleList = new ArrayList<>();
//    modules = new ArrayList<>();
    calculator = new Calculator();
    fileChooser = new FileChooser();
  }

  @FXML
  void initialize() {
    initializeSemesterBox();
    addModuleBox();
  }

  private void initializeSemesterBox() {
    semesterBox.getItems().clear();
    for (int i = 1; i <= 6; i++) {
      semesterBox.getItems().add(i);
    }
    semesterBox.setValue(1);
    modulePerSemester = 8;
  }

  private void addModuleBox() {
    moduleBox.getChildren().clear();
    moduleList.clear();
//    modules.clear();
    int moduleNumber = 1;
    moduleBox.getChildren().add(new Separator());
    while (moduleNumber <= modulePerSemester) {
      HBox hBox = new HBox(10);
      hBox.setPadding(new Insets(5, 0, 0, 0));
      hBox.getChildren().add(addTextField());
      hBox.getChildren().add(addCreditBox());
      hBox.getChildren().add(addModuleGroupBox());
      hBox.getChildren().add(addGradeBox());
      moduleList.add(hBox);
//      modules.add(new Module(addTextField().getText(),
//          addCreditBox().getValue(),
//          addModuleGroupBox().getValue(),
//          addGradeBox().getValue()));
      moduleNumber++;
    }
//    addModulesToBox();
    moduleBox.getChildren().addAll(moduleList);
    Separator separator = new Separator();
    separator.setPadding(new Insets(5, 0, 0, 0));
    moduleBox.getChildren().add(separator);
  }

//  private void addModulesToBox() {
//    for (Module module : modules) {
//      HBox hBox = new HBox(10);
//      hBox.setPadding(new Insets(5,0,0,0));
//      hBox.getChildren().add(addTextField());
//      hBox.getChildren().add(addCreditBox());
//      hBox.getChildren().add(addModuleGroupBox());
//      hBox.getChildren().add(addGradeBox());
//      moduleBox.getChildren().add(hBox);
//    }
//  }

  private TextField addTextField() {
    TextField textField = new TextField();
    textField.setPromptText("Modulname");
    textField.setPrefWidth(270);
    return textField;
  }

  private ComboBox<Integer> addCreditBox() {
    ComboBox<Integer> creditBox = new ComboBox<>();
    creditBox.setPrefWidth(60);
    creditBox.getItems().add(2);
    creditBox.getItems().add(4);
    creditBox.getItems().add(6);
    creditBox.getItems().add(12);
    creditBox.setValue(4);
    return creditBox;
  }

  private ComboBox<String> addModuleGroupBox() {
    ComboBox<String> moduleGroupBox = new ComboBox<>();
    moduleGroupBox.setPrefWidth(60);
    setGroupForSemester(moduleGroupBox);
    return moduleGroupBox;
  }

  private void setGroupForSemester(ComboBox<String> moduleGroupBox) {
    int nr = 1;
    if (semesterBox.getValue() <= 2) {
      moduleGroupBox.getItems().add(IT + nr);
      moduleGroupBox.getItems().add(IT + (nr + 1));
      group1AverageLabel.setText("  " + IT + nr);
      group2AverageLabel.setText("  " + IT + (nr + 1));
    } else if (semesterBox.getValue() == 3 || semesterBox.getValue() == 4) {
      nr = 3;
      moduleGroupBox.getItems().add(IT + nr);
      moduleGroupBox.getItems().add(IT + (nr + 1));
      group1AverageLabel.setText("  " + IT + nr);
      group2AverageLabel.setText("  " + IT + (nr + 1));
    } else {
      nr = 5;
      moduleGroupBox.getItems().add(IT + nr);
      moduleGroupBox.getItems().add(IT + (nr + 1));
      group1AverageLabel.setText("  " + IT + nr);
      group2AverageLabel.setText("  " + IT + (nr + 1));
    }
  }

  private ComboBox<Double> addGradeBox() {
    ComboBox<Double> gradeBox = new ComboBox<>();
    gradeBox.setPrefWidth(60);
    for (double d = 1.0; d <= 6; d += 0.5) {
      gradeBox.getItems().add(d);
    }
    gradeBox.setValue(4.0);
    return gradeBox;
  }

  @FXML
  void setMaxModules() {
    switch (semesterBox.getValue()) {
      case 1:
      case 2:
      case 3:
      case 5:
        modulePerSemester = 8;
        break;
      case 4:
        modulePerSemester = 9;
        break;
      case 6:
        modulePerSemester = 6;
        break;
    }
    addModuleBox();
  }

  @FXML
  void calculateGrades() throws CalculatorException {
    if (creditsOkay()) {
      List<Double> gradeList = calculator.calculateAverage(moduleList);
      if (gradeList.size() != 3) {
        throw new CalculatorException(String.format("There have to be 3 Grades but it is %d", gradeList.size()));
      }
      gradeAverage.setText(String.format("%.2f", gradeList.get(0)));
      gradeAverage.setStyle("-fx-text-fill: black");
      group1AverageGrade.setText(String.format("%.2f", gradeList.get(1)));
      group2AverageGrade.setText(String.format("%.2f", gradeList.get(2)));
    } else {
      gradeAverage.setText("Anzahl Credits stimmen nicht! (Sollten 30 sein)");
      gradeAverage.setStyle("-fx-text-fill: red");
    }
  }

  private boolean creditsOkay() {
    int credits = 0;
    for (HBox hBox : moduleList) {
      ComboBox<Integer> creditBox = (ComboBox<Integer>) hBox.getChildren().get(1);
      credits += creditBox.getValue();
    }
    return credits == AMOUNT_OF_CREDITS;
  }

  @FXML
  void reset() {
    addModuleBox();
  }

  @FXML
  void saveGrades() throws IOException {
    List<String> lines = FileHelper.getLinesFromList(moduleList);
    fileChooser.setTitle("Speichern");
    File file = fileChooser.showSaveDialog(null);
    if (!FileHelper.saveFile(file, lines)) {
      gradeAverage.setText("File konnte nicht gespeichert werden");
      gradeAverage.setStyle("-fx-text-fill: red");
    }
  }

  @FXML
  void loadOwnGrades() throws CalculatorException {
    fileChooser.setInitialDirectory(new File("C:"));
    fileChooser.setTitle("Laden");
    File file = fileChooser.showOpenDialog(null);
    List<String> lines = FileHelper.loadFile(file);
    modulePerSemester = lines.size();
    addModuleBox();
    addTemplateValues(lines);
  }

  @FXML
  void closeApp() {
    Platform.exit();
  }

  @FXML
  void loadFirstSemester() throws CalculatorException {
    semesterBox.setValue(1);
    List<String> lines = FileHelper.loadTemplate(1);
//    List<Module> modules = FileHelper.loadTemplates(1);
    addTemplateValues(lines);
//    addTemplateValuess(modules);
  }

  @FXML
  void loadSecondSemester() throws CalculatorException {
    semesterBox.setValue(2);
    List<String> lines = FileHelper.loadTemplate(2);
//    List<Module> modules = FileHelper.loadTemplates(2);
    addTemplateValues(lines);
//    addTemplateValuess(modules);
  }

  @FXML
  void loadThirdSemester() throws CalculatorException {
    semesterBox.setValue(3);
    List<String> lines = FileHelper.loadTemplate(3);
//    List<Module> modules = FileHelper.loadTemplates(3);
    addTemplateValues(lines);
//    addTemplateValuess(modules);
  }

  @FXML
  void loadFourthSemester() throws CalculatorException {
    semesterBox.setValue(4);
    List<String> lines = FileHelper.loadTemplate(4);
//    List<Module> modules = FileHelper.loadTemplates(4);
    addTemplateValues(lines);
//    addTemplateValuess(modules);
  }

  @FXML
  void loadFifthSemester() throws CalculatorException {
    semesterBox.setValue(5);
    List<String> lines = FileHelper.loadTemplate(5);
//    List<Module> modules = FileHelper.loadTemplates(5);
    addTemplateValues(lines);
//    addTemplateValuess(modules);
  }

  @FXML
  void loadSixthSemester() throws CalculatorException {
    semesterBox.setValue(6);
    List<String> lines = FileHelper.loadTemplate(6);
//    List<Module> modules = FileHelper.loadTemplates(6);
    addTemplateValues(lines);
//    addTemplateValuess(modules);
  }

  private void addTemplateValues(List<String> lines) throws CalculatorException {
    if (lines.size() != moduleList.size()) {
      throw new CalculatorException("Not correct amount of Modules for Semester " + semesterBox.getValue() + "!");
    }
    for (int i = 0; i < moduleList.size(); i++) {
      HBox hBox = moduleList.get(i);
      String[] line = lines.get(i).split(SPLIT_REGEX);
      TextField modulName = (TextField) hBox.getChildren().get(0);
      ComboBox<Integer> credits = (ComboBox<Integer>) hBox.getChildren().get(1);
      ComboBox<String> group = (ComboBox<String>) hBox.getChildren().get(2);
      ComboBox<Double> grade = (ComboBox<Double>) hBox.getChildren().get(3);
      modulName.setText(line[0].trim());
      credits.setValue(Integer.valueOf(line[1].trim()));
      group.setValue(line[2].trim());
      grade.setValue(Double.valueOf(line[3]));
    }
  }

//  private void addTemplateValuess(List<Module> modules) throws CalculatorException {
//    if (modules.size() != moduleList.size()) {
//      throw new CalculatorException("Not correct amount of Modules for Semester " + semesterBox.getValue() + "!");
//    }
//    for (int i = 0; i < moduleList.size(); i++) {
//      HBox hBox = moduleList.get(i);
//      Module module = modules.get(i);
////      String[] line = modules.get(i).split(SPLIT_REGEX);
//      TextField modulName = (TextField) hBox.getChildren().get(0);
//      ComboBox<Integer> credits = (ComboBox<Integer>) hBox.getChildren().get(1);
//      ComboBox<String> group = (ComboBox<String>) hBox.getChildren().get(2);
//      ComboBox<Double> grade = (ComboBox<Double>) hBox.getChildren().get(3);
//      modulName.setText(module.getName());
//      credits.setValue(module.getCredits());
//      group.setValue(module.getGroup());
//      grade.setValue(module.getGrade());
//    }
//  }
}