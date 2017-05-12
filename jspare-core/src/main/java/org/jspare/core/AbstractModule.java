package org.jspare.core;

import org.jspare.core.internal.Bind;
import org.jspare.core.internal.ReflectionUtils;

import javax.inject.Inject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * Created by paulo.ferreira on 10/05/2017.
 */
public abstract class AbstractModule extends MySupport implements Module {

  protected final ApplicationContext context;

  public AbstractModule() {
    this.context = Environment.getContext();
    defaultLoad();
  }

  @Override
  public void load() {
  }

  protected void defaultLoad() {
    for (Method method : getClass().getDeclaredMethods()) {

      if (method.isAnnotationPresent(Provides.class)) {
        provides(method);
      }
    }
  }

  private void provides(Method method) {
    Object instance = null;
    Object[] parameters = new Object[method.getParameterCount()];
    int count = 0;
    for (Parameter param : method.getParameters()) {
      Object paramValue = null;
      if (param.isAnnotationPresent(Inject.class)) {

        Class<?> paramType = param.getType();
        String paramQualifier = ReflectionUtils.getQualifier(param);
        paramValue = Environment.my(paramType, paramQualifier);
      }
      parameters[count] = paramValue;
      count++;
    }

    try {
      method.setAccessible(true);
      instance = method.invoke(this, parameters);
    } catch (IllegalAccessException | InvocationTargetException e) {
      // Ignore method
      e.printStackTrace();
    }

    if (method.getReturnType() != null) {
      String name = method.getAnnotation(Provides.class).value();
      Class<?> type = method.getReturnType();
      context.registry(Bind.bind(type).name(name), instance);
    }
  }
}
