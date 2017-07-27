package org.jspare.unit.ext.junit;

import org.jspare.core.Environment;
import org.jspare.unit.mock.MockerUtils;
import org.junit.internal.runners.model.ReflectiveCallable;
import org.junit.internal.runners.statements.Fail;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;
import org.junit.runners.model.Statement;

/**
 * Created by paulo.ferreira on 21/06/2017.
 */
public class JspareUnitRunner extends BlockJUnit4ClassRunner {

  private final Class<?> testClass;

  public JspareUnitRunner(Class<?> testClass, RunnerBuilder builder) throws InitializationError {
    super(testClass);
    this.testClass = testClass;
    prepare();
  }

  private void prepare() {
    Environment.create();
    MockerUtils.initialize(this.testClass);
  }

  /**
   * Augment the default JUnit behavior
   * <p>Furthermore, support for timeouts has been moved down the execution
   * chain in order to include execution of {@link org.junit.Before @Before}
   * and {@link org.junit.After @After} methods within the timed execution.
   * Note that this differs from the default JUnit behavior of executing
   * {@code @Before} and {@code @After} methods in the main thread while
   * executing the actual test method in a separate thread. Thus, the net
   * effect is that {@code @Before} and {@code @After} methods will be
   * executed in the same thread as the test method. As a consequence,
   * JUnit-specified timeouts will work fine in combination with Spring
   * transactions. However, JUnit-specific timeouts still differ from
   * Spring-specific timeouts in that the former execute in a separate
   * thread while the latter simply execute in the main thread (like regular
   * tests).
   *
   * @see #methodInvoker(FrameworkMethod, Object)
   * @see #possiblyExpectingExceptions(FrameworkMethod, Object, Statement)
   * @see #withBefores(FrameworkMethod, Object, Statement)
   * @see #withAfters(FrameworkMethod, Object, Statement)
   * @see #withPotentialTimeout(FrameworkMethod, Object, Statement)
   */
  @Override
  protected Statement methodBlock(FrameworkMethod frameworkMethod) {
    Object testInstance;
    try {
      testInstance = new ReflectiveCallable() {
        @Override
        protected Object runReflectiveCall() throws Throwable {
          return createTest();
        }
      }.run();
    } catch (Throwable ex) {
      return new Fail(ex);
    }

    Environment.inject(testInstance);

    Statement statement = methodInvoker(frameworkMethod, testInstance);
    statement = possiblyExpectingExceptions(frameworkMethod, testInstance, statement);
    statement = withBefores(frameworkMethod, testInstance, statement);
    statement = withAfters(frameworkMethod, testInstance, statement);
    statement = withPotentialTimeout(frameworkMethod, testInstance, statement);
    return statement;
  }

}
