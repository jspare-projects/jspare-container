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

import lombok.RequiredArgsConstructor;
import org.jspare.core.Environment;
import org.jspare.core.InjectorAdapter;
import org.jspare.core.MembersInjector;

import javax.inject.Inject;
import java.lang.reflect.*;
import java.util.Map;

/**
 * @author <a href="https://pflima92.github.io/">Paulo Lima</a>
 */
@RequiredArgsConstructor
public final class MembersInjectorImpl implements MembersInjector {

  private final Map<Class<? extends InjectorAdapter>, InjectorAdapter> INJECTORS;

  @Override
  public void inject(Object instance) {
    Class<?> clazz = instance.getClass();
    inject(clazz, instance);
  }

  private void inject(Class<?> type, Object instance) {
    // Recursive call to resolve superclass
    if (type.getSuperclass() != null) {

      inject(type.getSuperclass(), instance);
    }

    for (Field field : type.getDeclaredFields()) {

      //are annotated with @Inject.
      //are not final.
      //may have any otherwise valid name.
      if (!field.isAnnotationPresent(Inject.class) || field.getModifiers() == Modifier.FINAL) {

        INJECTORS.values().forEach(injector -> {
          if (injector.isInjectable(field)) {
            injector.inject(instance, field);
          }
        });
        continue;
      }

      Class<?> fieldType = field.getType();
      String name = ReflectionUtils.getQualifier(field);

      try {
        field.setAccessible(true);
        field.set(instance, Environment.my(fieldType, name));
      } catch (IllegalAccessException e) {
        // Nulling field
        e.printStackTrace();
      }
    }

    for (Method method : type.getDeclaredMethods()) {

      //are annotated with @Inject.
      //are not abstract.
      //do not declare type parameters of their own.
      if (!method.isAnnotationPresent(Inject.class) || method.getModifiers() == Modifier.ABSTRACT) {
        continue;
      }

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
        method.invoke(instance, parameters);
      } catch (IllegalAccessException | InvocationTargetException e) {

        // PrintStack error
        e.printStackTrace();
      }
    }
  }
}
