package ch.imper.calculator.uni;

/**
 * This class represents one modul in a semester.
 *
 * @author Imper
 * @version 1.0
 */
public class Module {
  private final String name;
  private final int credits;
  private final String group;
  private double grade;

  public Module(String[] values) {
    name = values[0].trim();
    credits = Integer.parseInt(values[1].trim());
    group = values[2].trim();
    grade = Double.parseDouble(values[3].trim());
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
