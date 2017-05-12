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
package org.jspare.core;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.apache.commons.lang.StringUtils;
import org.jspare.core.exception.EnvironmentException;
import org.jspare.core.exception.Errors;
import org.jspare.core.internal.Bind;

import java.util.Objects;

/**
 * The Class Environment.
 * <p>
 * Responsible for the whole framework of the container control. The environment
 * is responsible for the retention and display of framework components. Through
 * this class has access to static methods that ensure secure access to
 * instances of components registered in the environment.
 *
 * @author pflima
 * @since 05/10/2015
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Environment {

  protected static ApplicationContext context;

  public static ApplicationContext create(ApplicationContext contextGraph) {
    context = contextGraph;
    return context;
  }

  /**
   * Setup environment.
   */
  public static ApplicationContext create() {
    return create(ApplicationContext.create());
  }

  /**
   * My.
   * <p>
   * This method is responsible for retrieving the implementation of a
   * component of the environment, the instance of the class will be recovered
   * which is registered in the environment, noting that the scope and other
   * states should be set to register a new component in the environment when
   * performing this method will be recovered an implementation that is
   * already registered in the environment, if the component has not yet been
   * registered, the method should register a common implementation, this one
   * is is available and provide a memory reference.
   *
   * @param <T>   the generic type
   * @param clazz the clazz
   * @return the t
   */
  public static <T> T my(Class<T> clazz) {
    return my(clazz, StringUtils.EMPTY);
  }

  /**
   * My.
   * <p>
   * This method is responsible for retrieving the implementation of a
   * component of the environment, the instance of the class will be recovered
   * which is registered in the environment, noting that the scope and other
   * states should be set to register a new component in the environment when
   * performing this method will be recovered an implementation that is
   * already registered in the environment, if the component has not yet been
   * registered, the method should register a common implementation, this one
   * is is available and provide a memory reference.
   * <p>
   * Receive the qualifier for qualify the injection, note: the implementation
   * class need be annotated with {@link javax.inject.Named}
   *
   * @param <T>       the generic type
   * @param clazz     the clazz
   * @param qualifier the qualifier
   * @return the t
   */
  public static <T> T my(Class<T> clazz, String qualifier) {

    return getContext().getInstance(clazz, qualifier);
  }

  public static <T> T provide(Class<T> clazz) {

    return provide(clazz, StringUtils.EMPTY);
  }

  public static <T> T provide(Class<T> clazz, String qualifier) {
    return getContext().provide(clazz, qualifier);
  }


  public static void registry(@NonNull Bind bind) {
    getContext().registry(bind);
  }

  public static void registry(@NonNull Bind bind, @NonNull Object instance) {
    getContext().registry(bind, instance);
  }

  public static void loadModule(Class<? extends AbstractModule> clazz) {
    getContext().loadModule(clazz);
  }

  public static void inject(Object instance) {
    getContext().inject(instance);
  }

  public static ApplicationContext getContext() {
    if (Objects.isNull(context)) {

      throw new EnvironmentException(Errors.CONTEXT_NOT_CREATED);
    }
    synchronized (context) {
      return context;
    }
  }

  public static void release() {
    getContext().release();
  }

  public static void destroy() {
    if (context != null) {
      synchronized (context) {
        context = null;
      }
    }
  }
}
