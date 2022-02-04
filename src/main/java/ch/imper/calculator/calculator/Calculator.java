package ch.imper.calculator.calculator;

import ch.imper.calculator.uni.Module;

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
    List<Module> group1Modules = getGroup1Modules(modules);
    List<Module> group2Modules = getGroup2Modules(modules);
    averageGrades.add(calculateAverageGrades(modules));
    averageGrades.add(calculateAverageGrades(group1Modules));
    averageGrades.add(calculateAverageGrades(group2Modules));

    return averageGrades;
  }

  private Double calculateAverageGrades(List<Module> modules) {
    double gradePoints = 0.0;
    int credits = 0;
    for (Module module : modules) {
      gradePoints += module.getGrade() * module.getCredits();
      credits += module.getCredits();
    }
    return gradePoints / credits;
  }

  private List<Module> getGroup1Modules(List<Module> modules) {
    List<Module> modulGroupList = new ArrayList<>();
    for (Module module : modules) {
      String group = module.getGroup();
      if (group.equals("IT1") || group.equals("IT3") || group.equals("IT5")) {
        modulGroupList.add(module);
      }
    }
    return modulGroupList;
  }

  private List<Module> getGroup2Modules(List<Module> modules) {
    List<Module> modulGroupList = new ArrayList<>();
    for (Module module : modules) {
      String group = module.getGroup();
      if (group.equals("IT2") || group.equals("IT4") || group.equals("IT6")) {
        modulGroupList.add(module);
      }
    }
    return modulGroupList;
  }
}
