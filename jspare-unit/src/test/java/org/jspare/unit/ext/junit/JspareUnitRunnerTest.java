package org.jspare.unit.ext.junit;

import org.jspare.core.Environment;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * A JUnit runner for run over Jspare Container
 *
 * Note : a runner is needed because when a rule statement is evaluated, it will run the before/test/after
 *       method and then test method is executed even if there are pending Async objects in the before
 *       method. The runner gives this necessary fine grained control.
 *
 * @author paulo.ferreira
 */
@RunWith(JspareUnitRunner.class)
public class JspareUnitRunnerTest {

  @Test
  public void testEnvironment() {
    Assert.assertTrue(Environment.isLoaded());
  }
}
