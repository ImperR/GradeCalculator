package ch.imper.calculator.calculator;

import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

/**
 * This class calculates the average of the grades.
 *
 * @author Imper
 * @version 1.0
 */
public class Calculator {

  public List<Double> calculateAverage(List<HBox> hBoxList) {
    List<Double> averageGrades = new ArrayList<>();
    List<Double> grades = getAllGrades(hBoxList);
    List<Double> group1Grades = getGroup1Grades(hBoxList);
    List<Double> group2Grades = getGroup2Grades(hBoxList);
    averageGrades.add(calculateAverageGrade(grades));
    averageGrades.add(calculateAverageGrade(group1Grades));
    averageGrades.add(calculateAverageGrade(group2Grades));
   return averageGrades;
  }

  private Double calculateAverageGrade(List<Double> grades) {
    double average = 0.0;
    for (Double grade : grades) {
      average += grade;
    }
    return average / grades.size();
  }

  private List<Double> getAllGrades(List<HBox> hBoxList) {
    List<Double> grades = new ArrayList<>();
    for (HBox hBox : hBoxList) {
      ComboBox<Double> grade = (ComboBox<Double>) hBox.getChildren().get(3);
      grades.add(grade.getValue());
    }
    return grades;
  }

  private List<Double> getGroup1Grades(List<HBox> hBoxList) {
    List<Double> grades = new ArrayList<>();
    for (HBox hBox : hBoxList) {
      ComboBox<String> groupBox = (ComboBox<String>) hBox.getChildren().get(2);
      String group = groupBox.getValue();
      if (group.equals("IT1") || group.equals("IT3") || group.equals("IT5")) {
        ComboBox<Double> grade = (ComboBox<Double>) hBox.getChildren().get(3);
        grades.add(grade.getValue());
      }
    }
    return grades;
  }

  private List<Double> getGroup2Grades(List<HBox> hBoxList) {
    List<Double> grades = new ArrayList<>();
    for (HBox hBox : hBoxList) {
      ComboBox<String> groupBox = (ComboBox<String>) hBox.getChildren().get(2);
      String group = groupBox.getValue();
      if (group.equals("IT2") || group.equals("IT4") || group.equals("IT6")) {
        ComboBox<Double> grade = (ComboBox<Double>) hBox.getChildren().get(3);
        grades.add(grade.getValue());
      }
    }
    return grades;
  }
}
