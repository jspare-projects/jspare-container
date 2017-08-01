package org.jspare.core;

import org.jspare.core.internal.ApplicationContextImpl;
import org.jspare.core.internal.Bind;

/**
 * The entry point to the Vertx Jspare framework.
 *
 * <p>ApplicationContext supports a model of development that draws clear boundaries between APIs,
 * Implementations of these APIs, Modules which configure these implementations, and finally
 * Applications which consist of a collection of Modules.</p>
 *
 * @author <a href="https://pflima92.github.io/">Paulo Lima</a>
 */
public interface ApplicationContext {

  static ApplicationContext create() {
    return new ApplicationContextImpl();
  }

  ApplicationContext addImplementationProvider(ImplementationResolver provider);

  ApplicationContext addInjector(InjectorAdapter injector);

  void inject(Object instance);

  <T> T getInstance(Class<T> clazz);

  <T> T getInstance(Class<T> clazz, String named);

  <T> T provide(Class<T> clazz);

  <T> T provide(Class<T> clazz, String named);

  ApplicationContext registry(Bind bind);

  ApplicationContext registry(Bind bind, Object instance);

  ApplicationContext release();
}
