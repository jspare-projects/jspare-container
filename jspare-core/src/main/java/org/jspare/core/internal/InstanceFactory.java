/*
 * Copyright 2017 JSpare.org.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.jspare.core.internal;

import lombok.extern.java.Log;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.jspare.core.ApplicationContext;
import org.jspare.core.Factory;
import org.jspare.core.ParameterizedTypeRetention;
import org.jspare.core.exception.EnvironmentException;
import org.jspare.core.exception.Errors;

import javax.inject.Inject;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;


/**
 * Created by paulo.ferreira on 08/05/2017.
 */
@Log
public class InstanceFactory<T> implements Factory<T> {
  private final ApplicationContext context;
  private final Bind<T> bind;

  public InstanceFactory(ApplicationContext context, Bind<T> bind) {
    this.context = context;
    this.bind = bind;
  }

  public T get() {

    List<Constructor> constructors = allowedConstructors(bind.to());
    if (constructors.isEmpty()) {

      throw new EnvironmentException(Errors.FAILED_INSTANTIATION);
    }

    if (constructors.size() > 1) {

      if (log.isLoggable(Level.WARNING)) {
        log.warning(String.format("More than one valid constructor founded for class %s", bind.to()));
      }
    }

    Constructor candidate = constructors.get(0);
    try {

      T instance = instantiate(candidate);
      context.inject(instance);
      return instance;
    } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {

      e.printStackTrace();

      if (log.isLoggable(Level.SEVERE)) {

        log.severe(ExceptionUtils.getFullStackTrace(e));
      }
      // XXX Check this return
      return null;
    }
  }

  private T instantiate(Constructor constructor) throws IllegalAccessException, InvocationTargetException, InstantiationException {

    boolean injectAllParameters = constructor.isAnnotationPresent(Inject.class);

    Object[] parameters = new Object[constructor.getParameterCount()];
    int count = 0;
    for (Parameter param : constructor.getParameters()) {
      Object paramValue = null;
      if (injectAllParameters || param.isAnnotationPresent(Inject.class)) {

        Class<?> type = param.getType();
        String name = ReflectionUtils.getQualifier(param);
        paramValue = context.getInstance(type, name);
      }
      parameters[count] = paramValue;
      count++;
    }

    T instance = (T) constructor.newInstance(parameters);

    Class<T> type = constructor.getDeclaringClass();
    if (ReflectionUtils.collecInterfaces(type).contains(ParameterizedTypeRetention.class)) {

      Type[] types = ((ParameterizedType) type.getGenericSuperclass()).getActualTypeArguments();
      ((ParameterizedTypeRetention) instance).setTypes(types);
    }
    return instance;
  }

  private List<Constructor> allowedConstructors(Class<?> clazz) {
    return Arrays.asList(clazz.getDeclaredConstructors()).stream().filter(c -> c.getModifiers() == Modifier.PUBLIC).collect(Collectors.toList());
  }

}
