package ch.imper.calculator.controller;

import ch.imper.calculator.calculator.Calculator;
import ch.imper.calculator.exception.CalculatorException;
import ch.imper.calculator.helper.FileHelper;
import ch.imper.calculator.uni.Module;
import ch.imper.calculator.uni.Semester;
import ch.imper.calculator.uni.StudyYear;
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
//  private static final String IT = "IT";
  private static final int AMOUNT_OF_CREDITS = 30;
  private final FileChooser fileChooser;
  private final List<HBox> moduleList;
  //  private final List<Module> modules;
  private final Calculator calculator;
  private StudyYear year;
  private int modulePerSemester;

  @FXML
  private Label gradeAverage;
  @FXML
  private Label gradeAverageSem1;
  @FXML
  private Label gradeAverageSem2;
  @FXML
  private Label group1Average;
  @FXML
  private Label group1AverageLabel;
  @FXML
  private Label group1AverageLabel1;
  @FXML
  private Label group1AverageLabel2;
  @FXML
  private Label group1AverageSem1;
  @FXML
  private Label group1AverageSem2;
  @FXML
  private Label group2Average;
  @FXML
  private Label group2AverageLabel;
  @FXML
  private Label group2AverageLabel1;
  @FXML
  private Label group2AverageLabel2;
  @FXML
  private Label group2AverageSem1;
  @FXML
  private Label group2AverageSem2;
  @FXML
  private VBox semesterBox1;
  @FXML
  private VBox semesterBox2;
  @FXML
  private Label semester1;
  @FXML
  private Label semester2;
  @FXML
  private ComboBox<String> studyCourseBox;
  @FXML
  private ComboBox<Integer> studyYearBox;


  public CalculatorViewController() {
    moduleList = new ArrayList<>();
//    modules = new ArrayList<>();
    calculator = new Calculator();
    fileChooser = new FileChooser();
  }

  @FXML
  void initialize() throws CalculatorException {
    initializeStudyCourseBox();
    initializeStudyYearBox();
    setStudyTemplate();
//    addModuleBox();
  }

  private void initializeStudyCourseBox() {
    studyCourseBox.getItems().clear();
    studyCourseBox.getItems().addAll(StudyYear.getAllCourses());
    studyCourseBox.setValue(StudyYear.StudyCourse.IT.getAcronym());
  }

  private void initializeStudyYearBox() {
    studyYearBox.getItems().clear();
    for (int i = 1; i <= 3; i++) {
      studyYearBox.getItems().add(i);
    }
    studyYearBox.setValue(1);
  }

  @FXML
  void setStudyTemplate() throws CalculatorException {
    setSemesterBox(studyYearBox.getValue());
  }

  private void setSemesterBox(int studyYear) throws CalculatorException {
    int semester = studyYear * 2;
    clearSemesterBoxes();
    createStudyYear(semester, StudyYear.StudyCourse.IT);
    addTemplateValuesToUI();
  }

  public void createStudyYear(int semester, StudyYear.StudyCourse course) {
    Semester semester1 = new Semester(semester-1);
    Semester semester2 = new Semester(semester);
    semester1.addModules(FileHelper.loadTemplates(semester-1));
    semester2.addModules(FileHelper.loadTemplates(semester));
    year = new StudyYear(course, semester1, semester2);
    changeAverageGroupLabel(semester);
  }

  private void changeAverageGroupLabel(int semester) {
    group1AverageLabel.setText(year.getCourse().getAcronym() + (semester-1));
    group2AverageLabel.setText(year.getCourse().getAcronym() + semester);
    group1AverageLabel1.setText(year.getCourse().getAcronym() + (semester-1));
    group2AverageLabel1.setText(year.getCourse().getAcronym() + semester);
    group1AverageLabel2.setText(year.getCourse().getAcronym() + (semester-1));
    group2AverageLabel2.setText(year.getCourse().getAcronym() + semester);
  }

  private void addTemplateValuesToUI(/*List<String> lines, int semester*/) throws CalculatorException {
    clearSemesterBoxes();
    addLineSeparatorToSemesterBoxes(0);
    semesterBox1.getChildren().addAll(createModuleBoxes(year.getSemester1()));
    semesterBox2.getChildren().addAll(createModuleBoxes(year.getSemester2()));
    addLineSeparatorToSemesterBoxes(5);
  }

  private List<HBox> createModuleBoxes(Semester semester) {
    List<HBox> modules = new ArrayList<>();
    HBox hBox;
    for (Module module : semester.getModuleList()) {
      hBox = new HBox(10);
      hBox.setPadding(new Insets(5,0,0,0));
      hBox.getChildren().add(addModuleName(module.getName()));
      hBox.getChildren().add(addCreditsLabel(module.getCredits()));
      hBox.getChildren().add(addModuleGroupLabel(module.getGroup()));
      hBox.getChildren().add(addGradeBox(module.getGrade()));
      modules.add(hBox);
    }
    return modules;
  }

  private void addLineSeparatorToSemesterBoxes(int padding) {
    Separator sep1 = new Separator();
    Separator sep2 = new Separator();
    sep1.setPadding(new Insets(padding, 0, 0, 0));
    sep2.setPadding(new Insets(padding, 0, 0, 0));
    semesterBox1.getChildren().add(sep1);
    semesterBox2.getChildren().add(sep2);
  }

  private void addModuleBox() {
    semesterBox1.getChildren().clear();
    moduleList.clear();
//    modules.clear();
    int moduleNumber = 1;
    semesterBox1.getChildren().add(new Separator());
    while (moduleNumber <= modulePerSemester) {
      HBox hBox = new HBox(10);
      hBox.setPadding(new Insets(5, 0, 0, 0));
      hBox.getChildren().add(addModuleName("Modulname"));
      hBox.getChildren().add(addCreditsLabel(4));
      hBox.getChildren().add(addModuleGroupLabel("IT1"));
      hBox.getChildren().add(addGradeBox(4.0));
      moduleList.add(hBox);
//      modules.add(new Module(addTextField().getText(),
//          addCreditBox().getValue(),
//          addModuleGroupBox().getValue(),
//          addGradeBox().getValue()));
      moduleNumber++;
    }
//    addModulesToBox();
    semesterBox1.getChildren().addAll(moduleList);
    Separator separator = new Separator();
    separator.setPadding(new Insets(5, 0, 0, 0));
    semesterBox1.getChildren().add(separator);
  }

  private void clearSemesterBoxes() {
    semesterBox1.getChildren().clear();
    semesterBox2.getChildren().clear();
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

  private Label addModuleName(String name) {
    Label modulNameLabel = new Label(name);
    modulNameLabel.setPrefWidth(270);
    modulNameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14");
    return modulNameLabel;
  }

  private Label addCreditsLabel(int credits) {
    Label creditsLabel = new Label(String.valueOf(credits));
    creditsLabel.setPrefWidth(60);
    creditsLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-alignment: center ");
    return creditsLabel;
  }

  private Label addModuleGroupLabel(String group) {
    Label groupLabel = new Label(group);
    groupLabel.setPrefWidth(60);
    groupLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-alignment: center ");
    return groupLabel;
  }

  private void setGroupForSemester(ComboBox<String> moduleGroupBox) {
    int nr = 1;
//    if (semesterBox.getValue() <= 2) {
//      moduleGroupBox.getItems().add(IT + nr);
//      moduleGroupBox.getItems().add(IT + (nr + 1));
//      group1AverageLabel.setText("  " + IT + nr);
//      group2AverageLabel.setText("  " + IT + (nr + 1));
//    } else if (semesterBox.getValue() == 3 || semesterBox.getValue() == 4) {
//      nr = 3;
//      moduleGroupBox.getItems().add(IT + nr);
//      moduleGroupBox.getItems().add(IT + (nr + 1));
//      group1AverageLabel.setText("  " + IT + nr);
//      group2AverageLabel.setText("  " + IT + (nr + 1));
//    } else {
//      nr = 5;
//      moduleGroupBox.getItems().add(IT + nr);
//      moduleGroupBox.getItems().add(IT + (nr + 1));
//      group1AverageLabel.setText("  " + IT + nr);
//      group2AverageLabel.setText("  " + IT + (nr + 1));
//    }
  }

  private ComboBox<Double> addGradeBox(double grade) {
    ComboBox<Double> gradeBox = new ComboBox<>();
    gradeBox.setPrefWidth(60);
    for (double d = 1.0; d <= 6; d += 0.5) {
      gradeBox.getItems().add(d);
    }
    gradeBox.setValue(grade);
    return gradeBox;
  }
//
//  @FXML
//  void setMaxModules() {
//    switch (semesterBox.getValue()) {
//      case 1:
//      case 2:
//      case 3:
//      case 5:
//        modulePerSemester = 8;
//        break;
//      case 4:
//        modulePerSemester = 9;
//        break;
//      case 6:
//        modulePerSemester = 6;
//        break;
//    }
//    addModuleBox();
//  }

  @FXML
  void calculateGrades() throws CalculatorException {
//    if (areCreditsOkay()) {
//      List<Double> gradeList = calculator.calculateAverage(moduleList);
//      if (gradeList.size() != 3) {
//        throw new CalculatorException(String.format("There have to be 3 Grades but it is %d", gradeList.size()));
//      }
//      gradeAverage.setText(String.format("%.2f", gradeList.get(0)));
//      gradeAverage.setStyle("-fx-text-fill: black");
//      group1Average.setText(String.format("%.2f", gradeList.get(1)));
//      group2Average.setText(String.format("%.2f", gradeList.get(2)));
//    } else {
//      gradeAverage.setText("Anzahl Credits stimmen nicht!");
//      gradeAverage.setStyle("-fx-text-fill: red");
//    }
  }

  private boolean areCreditsOkay() {
//    int credits = 0;
//    for (HBox hBox : moduleList) {
//      ComboBox<Integer> creditBox = (ComboBox<Integer>) hBox.getChildren().get(1);
//      credits += creditBox.getValue();
//    }
//    return credits == AMOUNT_OF_CREDITS;
    return true;
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
//    semesterBox.setValue(1);
//    List<String> lines = FileHelper.loadTemplate(1);
//    List<Module> modules = FileHelper.loadTemplates(1);
//    addTemplateValues(lines);
//    addTemplateValuess(modules);
  }

  @FXML
  void loadSecondSemester() throws CalculatorException {
//    semesterBox.setValue(2);
//    List<String> lines = FileHelper.loadTemplate(2);
//    List<Module> modules = FileHelper.loadTemplates(2);
//    addTemplateValues(lines);
//    addTemplateValuess(modules);
  }

  @FXML
  void loadThirdSemester() throws CalculatorException {
//    semesterBox.setValue(3);
//    List<String> lines = FileHelper.loadTemplate(3);
//    List<Module> modules = FileHelper.loadTemplates(3);
//    addTemplateValues(lines);
//    addTemplateValuess(modules);
  }

  @FXML
  void loadFourthSemester() throws CalculatorException {
//    semesterBox.setValue(4);
//    List<String> lines = FileHelper.loadTemplate(4);
//    List<Module> modules = FileHelper.loadTemplates(4);
//    addTemplateValues(lines);
//    addTemplateValuess(modules);
  }

  @FXML
  void loadFifthSemester() throws CalculatorException {
//    semesterBox.setValue(5);
//    List<String> lines = FileHelper.loadTemplate(5);
//    List<Module> modules = FileHelper.loadTemplates(5);
//    addTemplateValues(lines);
//    addTemplateValuess(modules);
  }

  @FXML
  void loadSixthSemester() throws CalculatorException {
//    semesterBox.setValue(6);
//    List<String> lines = FileHelper.loadTemplate(6);
//    List<Module> modules = FileHelper.loadTemplates(6);
//    addTemplateValues(lines);
//    addTemplateValuess(modules);
  }

  private void addTemplateValues(List<String> lines) throws CalculatorException {
//    if (lines.size() != moduleList.size()) {
//      throw new CalculatorException("Not correct amount of Modules for Semester " + semesterBox.getValue() + "!");
//    }
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

  @FXML
  void resetGrades1() {
    addModuleBox();
  }

  @FXML
  void resetGrades2() {
    addModuleBox();
  }
}