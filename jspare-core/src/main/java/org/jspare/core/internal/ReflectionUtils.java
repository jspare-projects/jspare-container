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

import lombok.experimental.UtilityClass;
import org.apache.commons.lang.StringUtils;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.inject.Qualifier;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by paulo.ferreira on 09/05/2017.
 */
@UtilityClass
public class ReflectionUtils {

  public String getQualifier(AnnotatedElement field) {
    String name = StringUtils.EMPTY;

    if (field.isAnnotationPresent(Named.class)) {
      name = field.getAnnotation(Named.class).value();
    } else {

      for (Annotation a : field.getAnnotations()) {

        if (a.annotationType().isAnnotationPresent(Qualifier.class)) {
          name = a.annotationType().getSimpleName();
        }
      }
    }
    return name;
  }

  public List<Method> getPostConstructMethods(Class<?> type) {
    // With PostConstruct annotation
    // Non Statis
    // Without parameters
    return Arrays.asList(type.getDeclaredMethods()).stream().filter(m -> m.getModifiers() != Modifier.STATIC && m.isAnnotationPresent(PostConstruct.class) && m.getParameterCount() == 0).collect(Collectors.toList());
  }

  public List<Class<?>> collecInterfaces(Class<?> clazz) {
    List<Class<?>> interfaces = new ArrayList<>();
    interfaces.addAll(Arrays.asList(clazz.getInterfaces()));

    if (clazz.getSuperclass() != null) {

      interfaces.addAll(Arrays.asList(clazz.getSuperclass().getInterfaces()));
    }
    return interfaces;
  }

  public List<Method> getMethodsWithAnnotation(Class<?> clazz, Class<? extends Annotation> ann) {
    return Arrays.asList(clazz.getDeclaredMethods()).stream().filter(m -> m.isAnnotationPresent(ann)).collect(Collectors.toList());
  }

  public <T> T getAnnotation(AnnotatedElement element, Class<T> ann) {
    return (T) element.getAnnotation((Class<? extends Annotation>) ann);
  }
}
