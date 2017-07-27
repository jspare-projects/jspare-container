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
import org.apache.commons.lang.StringUtils;
import org.jspare.core.ImplementationResolver;
import org.jspare.core.exception.EnvironmentException;
import org.jspare.core.exception.Errors;

import javax.inject.Named;
import javax.inject.Qualifier;
import javax.inject.Singleton;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author <a href="https://pflima92.github.io/">Paulo Lima</a>
 */
@RequiredArgsConstructor
public class InternalBinder {

  private final List<ImplementationResolver> IMPLEMENTATION_PROVIDERS;

  public <T> Bind<T> createBind(Key key) {

    Bind bind = Bind
      .bind(key.getType())
      .name(key.getName());

    lookupBind(bind);

    return bind;
  }

  public <T> Bind<T> createBind(Bind<T> bind) {
    lookupBind(bind);
    return bind;
  }

  private void lookupBind(Bind bind) {
    if (Objects.isNull(bind.to())) {
      InternalImplementationFinder finder = new InternalImplementationFinder(IMPLEMENTATION_PROVIDERS);
      bind.to(finder.find(bind.from(), bind.name()));
    }

    if(Objects.isNull(bind.to())){

      throw new EnvironmentException(Errors.NO_CMPT_REGISTERED.arguments(bind.from().getName()));
    }


    if (StringUtils.isEmpty(bind.name())) {

      qualify(bind);
    }
    bind.singleton(isSingleton(bind));
  }

  private boolean isSingleton(Bind<?> bind) {
    return bind.singleton() || isSingleton(bind.to()) || isSingleton(bind.from());
  }

  private boolean isSingleton(Class<?> clazz) {
    return clazz.isAnnotationPresent(Singleton.class) ||
      Arrays.asList(clazz.getAnnotations()).stream().anyMatch(a -> a.annotationType().isAnnotationPresent(Singleton.class));
  }

  private void qualify(final Bind<?> bind) {

    if(Objects.isNull(bind.to())){
      return;
    }

    if (bind.to().isAnnotationPresent(Named.class)) {
      bind.name(bind.to().getAnnotation(Named.class).value());
      return;
    }
    Arrays.asList(bind.to().getAnnotations()).stream().forEach(a -> {

      // With qualifier qualify with module name
      if (a.annotationType().isAnnotationPresent(Qualifier.class)) {
        bind.name(a.annotationType().getSimpleName());
      }
    });
  }

}
