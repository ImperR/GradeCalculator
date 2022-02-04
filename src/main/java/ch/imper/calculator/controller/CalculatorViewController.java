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
  private static final int AMOUNT_OF_CREDITS = 30;
  private final FileChooser fileChooser;
  private final Calculator calculator;
  private StudyYear year;

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
    calculator = new Calculator();
    fileChooser = new FileChooser();
  }

  @FXML
  void initialize() throws CalculatorException {
    initializeStudyCourseBox();
    initializeStudyYearBox();
    setStudyTemplate();
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
    setSemesterBoxes(studyYearBox.getValue(), getStudyCourse(), null);
  }

  private StudyYear.StudyCourse getStudyCourse() throws CalculatorException {
    switch (studyCourseBox.getValue()) {
      case "AV":
        return StudyYear.StudyCourse.AV;
      case "DS":
        return StudyYear.StudyCourse.DS;
      case "ET":
        return StudyYear.StudyCourse.ET;
      case "EU":
        return StudyYear.StudyCourse.EU;
      case "IT":
        return StudyYear.StudyCourse.IT;
      case "MT":
        return StudyYear.StudyCourse.MT;
      case "ST":
        return StudyYear.StudyCourse.ST;
      case "VS":
        return StudyYear.StudyCourse.VS;
      case "WI":
        return StudyYear.StudyCourse.WI;
      default:
        throw new CalculatorException(String.format("No known course existing for '%s'", studyCourseBox.getValue()));
    }
  }

  private void setSemesterBoxes(int year, StudyYear.StudyCourse course, List<String> linesOfYear) throws CalculatorException {
    clearSemesterBoxes();
    createStudyYear(year, course, linesOfYear);
    addTemplateValuesToUI();
  }

  private void createStudyYear(int year, StudyYear.StudyCourse course, List<String> linesOfYear) throws CalculatorException {
    if (linesOfYear == null) {
      linesOfYear = FileHelper.loadTemplate(course.getAcronym(), year);
    }
    int semester = year*2;
    Semester semester1 = new Semester();
    Semester semester2 = new Semester();
    semester1.addModules(getSemester(true, linesOfYear));
    semester2.addModules(getSemester(false, linesOfYear));
    this.year = new StudyYear(course, semester1, semester2);
    changeLabels(semester);
  }

  private List<Module> getSemester(boolean isFirst, List<String> linesOfYear) throws CalculatorException {
    List<Module> modules = new ArrayList<>();
    for (String line : linesOfYear) {
      if (isFirst && !line.isBlank()) {
          String[] values = line.split(SPLIT_REGEX);
          if (values.length != 4) {
            throw new CalculatorException("Template File not correct");
          }
          modules.add(new Module(values));
      }
      if (line.isBlank()) {
        isFirst = !isFirst;
      }
    }
    return modules;
  }

  private void changeLabels(int semester) {
    semester1.setText(String.format("Semester %d:", semester-1));
    semester2.setText(String.format("Semester %d:", semester));
    group1AverageLabel.setText(year.getCourse().getAcronym() + (semester-1));
    group2AverageLabel.setText(year.getCourse().getAcronym() + semester);
    group1AverageLabel1.setText(year.getCourse().getAcronym() + (semester-1));
    group2AverageLabel1.setText(year.getCourse().getAcronym() + semester);
    group1AverageLabel2.setText(year.getCourse().getAcronym() + (semester-1));
    group2AverageLabel2.setText(year.getCourse().getAcronym() + semester);
  }

  private void addTemplateValuesToUI() {
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
      hBox.getChildren().add(addGradeBox(module));
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

  private void clearSemesterBoxes() {
    semesterBox1.getChildren().clear();
    semesterBox2.getChildren().clear();
  }

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

  private ComboBox<Double> addGradeBox(Module module) {
    ComboBox<Double> gradeBox = new ComboBox<>();
    gradeBox.setPrefWidth(60);
    gradeBox.valueProperty().addListener((observable, oldValue, newValue) -> module.setGrade(newValue));
    for (double d = 6.0; d >= 1; d -= 0.5) {
      gradeBox.getItems().add(d);
    }
    gradeBox.setValue(module.getGrade());
    return gradeBox;
  }

  @FXML
  void calculateGrades() {
    if (areCreditsOkay()) {
      List<Double> averageGradesList = calculator.calculateAverage(year.getAllModules());
      List<Double> averageSem1List = calculator.calculateAverage(year.getSemester1().getModuleList());
      List<Double> averageSem2List = calculator.calculateAverage(year.getSemester2().getModuleList());
      showGradesOnUI(averageGradesList, averageSem1List, averageSem2List);
    } else {
      gradeAverage.setText("Anzahl der Credits stimmen nicht!");
      gradeAverage.setStyle("-fx-text-fill: red");
    }
  }

  private void showGradesOnUI(List<Double> averageGradesList, List<Double> averageSem1List, List<Double> averageSem2List) {
    gradeAverage.setStyle("-fx-text-fill: black");
    gradeAverage.setText(String.format("%.2f", averageGradesList.get(0)));
    group1Average.setText(String.format("%.2f", averageGradesList.get(1)));
    group2Average.setText(String.format("%.2f", averageGradesList.get(2)));

    gradeAverageSem1.setText(String.format("%.2f", averageSem1List.get(0)));
    group1AverageSem1.setText(String.format("%.2f", averageSem1List.get(1)));
    group2AverageSem1.setText(String.format("%.2f", averageSem1List.get(2)));

    gradeAverageSem2.setText(String.format("%.2f", averageSem2List.get(0)));
    group1AverageSem2.setText(String.format("%.2f", averageSem2List.get(1)));
    group2AverageSem2.setText(String.format("%.2f", averageSem2List.get(2)));
  }

  private boolean areCreditsOkay() {
    int credits = 0;
    for (Module module : year.getSemester1().getModuleList()) {
      credits += module.getCredits();
    }
    for (Module module : year.getSemester2().getModuleList()) {
      credits += module.getCredits();
    }
    return credits % AMOUNT_OF_CREDITS == 0;
  }

  @FXML
  void saveGrades() throws IOException {
    List<String> lines = FileHelper.getLinesFromList(year.getSemester1().getModuleList());
    lines.add("");
    lines.addAll(FileHelper.getLinesFromList(year.getSemester2().getModuleList()));
    for (String line : lines) {
      System.out.println(line);
    }
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
    String group = lines.get(2).split(SPLIT_REGEX)[2].trim();
    setSemesterBoxes(getYear(group), getCourse(group), lines);
  }

  private int getYear(String group) {
    int year = (Integer.parseInt(group.substring(group.length()-1))+1) / 2;
    studyYearBox.setValue(year);
    return year;
  }

  private StudyYear.StudyCourse getCourse(String group) {
    String pattern = group.substring(0, group.length()-1);
    studyCourseBox.setValue(pattern);
    return year.findCourse(pattern);
  }

  @FXML
  void closeApp() {
    Platform.exit();
  }

  @FXML
  void resetGradesSem1() {
    for (int i = 1; i < semesterBox1.getChildren().size() - 1; i++) {
      HBox hBox = (HBox) semesterBox1.getChildren().get(i);
      ComboBox<Double> grades = (ComboBox<Double>) hBox.getChildren().get(3);
      grades.setValue(4.0);
    }
  }

  @FXML
  void resetGradesSem2() {
    for (int i = 1; i < semesterBox2.getChildren().size() - 1; i++) {
      HBox hBox = (HBox) semesterBox2.getChildren().get(i);
      ComboBox<Double> grades = (ComboBox<Double>) hBox.getChildren().get(3);
      grades.setValue(4.0);
    }
  }
}