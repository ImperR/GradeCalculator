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

  public Semester() {
    moduleList = new ArrayList<>();
  }

  public void addModules(List<Module> modules) {
    clearModuleList();
    moduleList.addAll(modules);
  }

  public List<Module> getModuleList() {
    return Collections.unmodifiableList(moduleList);
  }

  public void clearModuleList() {
    moduleList.clear();
  }
}
