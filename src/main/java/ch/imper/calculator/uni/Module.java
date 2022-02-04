package ch.imper.calculator.uni;

import java.util.ArrayList;
import java.util.List;

/**
 * //ToDo Description of what this class does.
 *
 * @author Imper
 * @version 1.0
 */
public class Module {
  private  String name;
  private  int credits;
  private  String group;
  private  double grade;

  public Module(String name, int credits, String group, double grade) {
    this.name = name;
    this.credits = credits;
    this.group = group;
    this.grade = grade;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setCredits(int credits) {
    this.credits = credits;
  }

  public void setGroup(String group) {
    this.group = group;
  }

  public void setGrade(double grade) {
    this.grade = grade;
  }

  public String getName() {
    return name;
  }

  public int getCredits() {
    return credits;
  }

  public String getGroup() {
    return group;
  }

  public double getGrade() {
    return grade;
  }
}
