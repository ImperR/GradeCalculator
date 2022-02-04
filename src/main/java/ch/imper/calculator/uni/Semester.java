package ch.imper.calculator.uni;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class represents a semester from a study year.
 *
 * @author Imper
 * @version 1.0
 */
public class Semester {
  private final List<Module> moduleList;
  private int semesterNr;

  public Semester(int semesterNr) {
    moduleList = new ArrayList<>();
    this.semesterNr = semesterNr;
  }

  public void addModules(List<Module> modules) {
    clearModuleList();
    moduleList.addAll(modules);
  }

  public List<Module> getModuleList() {
    return Collections.unmodifiableList(moduleList);
  }

  public List<String> getLinesForSaving() {
    List<String> lines = new ArrayList<>();

    return lines;
  }

  public int getSemesterNr() {
    return semesterNr;
  }

  public void setSemesterNr(int semesterNr) {
    this.semesterNr = semesterNr;
  }

  public void clearModuleList() {
    moduleList.clear();
  }
}
