package org.jspare.unit.ext.junit;

import org.jspare.core.Environment;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

/**
 * Created by paulo.ferreira on 21/06/2017.
 */
public class JspareUnitRunner extends BlockJUnit4ClassRunner {

  private final Class<?> testClass;

  /**
   * Constructs a new {@code ParentRunner} that will run {@code @TestClass}
   *
   * @param testClass
   */
  public JspareUnitRunner(Class<?> testClass) throws InitializationError {
    super(testClass);
    this.testClass = testClass;
    loadEnvironment();
  }

  private void loadEnvironment() {
    Environment.create();
  }
}
