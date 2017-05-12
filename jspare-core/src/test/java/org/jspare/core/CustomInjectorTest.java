package org.jspare.core;

import org.jspare.core.internal.Bind;
import org.junit.Assert;
import org.junit.Test;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by paulo.ferreira on 10/05/2017.
 */
public class CustomInjectorTest {

  @Test
  public void customInjectorTest() {

    Environment.create();
    Environment.registry(Bind.bind(Calc.class).to(CalcImpl.class));

    Calc calc = Environment.my(Calc.class);
    int a = 1, b = 1;
    int result = a + b;
    Assert.assertEquals(result, calc.sum(a, b));
  }

  @Component
  public interface Calc {

    int sum(int a, int b);
  }

  public class CalcImpl implements Calc {

    @Inject
    @Singleton
    private Executor executor;

    public int sum(int a, int b) {
      return executor.sum(a, b);
    }
  }

  public class Executor {

    public int sum(int a, int b) {
      return a + b;
    }
  }
}
