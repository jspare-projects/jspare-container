package org.jspare.core;

import lombok.Getter;
import org.junit.Assert;
import org.junit.Test;

import javax.inject.Singleton;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by paulo.ferreira on 10/05/2017.
 */
public class CustomInjectorAdapterTest {

  @Test
  public void customInjectorAwareTest() {

    ApplicationContext ctx = Environment.create();
    ctx.loadModule(new CalcModule());

    Calc calc = ctx.provide(CalcInjector.class, Calc.class);

    int a = 1, b = 1;
    int result = a + b;
    Assert.assertEquals(result, calc.sum(a, b));
  }

  @Test
  public void customInjectorFieldTest() {

    ApplicationContext ctx = Environment.create();
    ctx.loadModule(new CalcModule());

    CalcExecutor executor = ctx.getInstance(CalcExecutor.class);
    Assert.assertNotNull(executor.getCalc());

    int a = 1, b = 1;
    int result = a + b;
    Assert.assertEquals(result, executor.getCalc().sum(a, b));
  }

  public interface Calc {
    int sum(int a, int b);
  }

  @Target(ElementType.FIELD)
  @Retention(RetentionPolicy.RUNTIME)
  public @interface CalcImpl {
  }

  @Singleton
  public class CalcExecutor {

    @CalcImpl
    @Getter
    private Calc calc;
  }

  public class CalcModule extends AbstractModule {

    public CalcModule() {
      super();
    }

    @Override
    public void load() {
      context.addInjector(new CalcInjector());
    }
  }

  public class CalcInjector implements InjectorAdapter {

    @Override
    public boolean isInjectable(Field field) {
      return field.isAnnotationPresent(CalcImpl.class);
    }

    @Override
    public Object get() {
      return Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{Calc.class}, (proxy, method, args) -> {
        int result = 0;
        if (method.getName().equals("sum")) {
          Object[] params = new Object[method.getParameterCount()];
          for (Object obj : args) {
            result += (int) obj;
          }
        }
        return result;
      });
    }
  }
}
