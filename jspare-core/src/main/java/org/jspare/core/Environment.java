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

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.apache.commons.lang.StringUtils;
import org.jspare.core.exception.EnvironmentException;
import org.jspare.core.exception.Errors;
import org.jspare.core.internal.Bind;

import java.util.Objects;

/**
 * A way to control the context of your application.
 * <p>
 * A environment for the whole framework of the container control.
 * Direct access to the application context by a single point.
 * </p>
 *
 * @author paulo.ferreira
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Environment {

  protected static ApplicationContext context;

  public static ApplicationContext create(ApplicationContext contextGraph) {
    context = contextGraph;
    return context;
  }

  /**
   * Create new instance of a ApplicationContext.
   * <ul>
   * <li>Create ApplicationContext with default configurations</li>
   * <li>Load default Injectors from classpath</li>
   * </ul>
   */
  public static ApplicationContext create() {
    ApplicationContext ctx = create(ApplicationContext.create());

    String ignoreInjectors = System.getProperty(Keys.IGNORE_AUTO_INJECTORS, Boolean.FALSE.toString());
    if (!Boolean.TRUE.toString().equals(ignoreInjectors)) {
      new FastClasspathScanner(StringUtils.EMPTY).matchClassesImplementing(InjectorAdapter.class, c -> {

        try {
          InjectorAdapter injector = c.newInstance();
          ctx.addInjector(injector);
        } catch (Exception e) {
          // ignore invalid injector
        }
      }).scan();
    }
    return ctx;
  }

  /**
   * Returns an instance of {@code type}.
   * <p>
   * A instance will be recovered
   * which is registered in the environment, noting that the scope and other
   * states should be set to register a new component in the environment when
   * performing this method will be recovered an implementation that is
   * already registered in the environment, if the component has not yet been
   * registered, the method should register a common implementation, this one
   * is is available and provide a memory reference.
   *
   * @param clazz
   * @param <T>
   * @return Returns an instance of {@code type}.
   */
  public static <T> T my(Class<T> clazz) {
    return my(clazz, StringUtils.EMPTY);
  }

  /**
   * Returns an instance of {@code type}.
   * <p>
   * A instance will be recovered
   * which is registered in the environment, noting that the scope and other
   * states should be set to register a new component in the environment when
   * performing this method will be recovered an implementation that is
   * already registered in the environment, if the component has not yet been
   * registered, the method should register a common implementation, this one
   * is is available and provide a memory reference.
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

  public static void loadModule(Class<? extends Module> clazz) {
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

  public static boolean isLoaded() {
    return !Objects.isNull(context);
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
