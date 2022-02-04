package ch.imper.calculator.uni;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a study year with its two semesters and the study course.
 *
 * @author Imper
 * @version 1.0
 */
public class StudyYear {
  private StudyCourse course;
  private Semester semester1;
  private Semester semester2;

  public StudyYear(StudyCourse course, Semester semester1, Semester semester2) {
    this.course = course;
    this.semester1 = semester1;
    this.semester2 = semester2;
  }

  public StudyCourse getCourse() {
    return course;
  }

  public Semester getSemester1() {
    return semester1;
  }

  public Semester getSemester2() {
    return semester2;
  }

  public List<Module> getAllModules() {
    List<Module> modules = new ArrayList<>();
    modules.addAll(semester1.getModuleList());
    modules.addAll(semester2.getModuleList());
    return modules;
  }

  public void setSemester1(Semester semester1) {
    this.semester1 = semester1;
  }

  public void setSemester2(Semester semester2) {
    this.semester2 = semester2;
  }

  public void setCourse(StudyCourse course) {
    this.course = course;
  }


  public static List<String> getAllCourses() {
    List<String> courses = new ArrayList<>();
    for (StudyCourse value : StudyCourse.values()) {
      courses.add(value.getAcronym());
    }
    return courses;
  }

  public enum StudyCourse {
    AV("AV"),
    DS("DS"),
    ET("ET"),
    EU("EU"),
    IT("IT"),
    MT("MT"),
    ST("ST"),
    VS("VS"),
    WI("WI");

    private final String acronym;

    StudyCourse(String acronym) {
      this.acronym = acronym;
    }

    public String getAcronym() {
      return acronym;
    }
  }
}
