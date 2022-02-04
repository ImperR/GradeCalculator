package ch.imper.calculator.calculator;

import ch.imper.calculator.uni.Module;
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

  public List<Double> calculateAverage(List<Module> modules) {
    List<Double> averageGrades = new ArrayList<>();
    List<Double> grades = getAllGrades(modules);
    List<Double> group1Grades = getGroup1Grades(modules);
    List<Double> group2Grades = getGroup2Grades(modules);
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

  private List<Double> getAllGrades(List<Module> modules) {
    List<Double> grades = new ArrayList<>();
    for (Module module : modules) {
      grades.add(module.getGrade());
    }
    return grades;
  }

  private List<Double> getGroup1Grades(List<Module> modules) {
    List<Double> grades = new ArrayList<>();
    for (Module module : modules) {
      String group = module.getGroup();
      if (group.equals("IT1") || group.equals("IT3") || group.equals("IT5")) {
        grades.add(module.getGrade());
      }
    }
    return grades;
  }

  private List<Double> getGroup2Grades(List<Module> modules) {
    List<Double> grades = new ArrayList<>();
    for (Module module : modules) {
      String group = module.getGroup();
      if (group.equals("IT2") || group.equals("IT4") || group.equals("IT6")) {
        grades.add(module.getGrade());
      }
    }
    return grades;
  }
}
