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
  private final StudyCourse course;
  private final Semester semester1;
  private final Semester semester2;

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

  public StudyCourse findCourse(String pattern) {
    for (StudyCourse value : StudyCourse.values()) {
      if (value.acronym.equals(pattern)) {
        return value;
      }
    }
    return StudyCourse.IT;
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
