package org.jspare.core;

import org.jspare.core.internal.ApplicationContextImpl;
import org.jspare.core.internal.Bind;

/**
 * Created by paulo.ferreira on 10/05/2017.
 */
public interface ApplicationContext {

  static ApplicationContext create() {
    return new ApplicationContextImpl();
  }

  ApplicationContext addImplementationProvider(ImplementationResolver provider);

  ApplicationContext addInjector(InjectorAdapter injector);

  ApplicationContext loadModule(Class<? extends Module> clazz);

  ApplicationContext loadModule(Module module);

  void inject(Object instance);

  <T> T getInstance(Class<T> clazz);

  <T> T getInstance(Class<T> clazz, String named);

  <T> T provide(Class<T> clazz);

  <T> T provide(Class<T> clazz, String named);

  ApplicationContext registry(Bind bind);

  ApplicationContext registry(Bind bind, Object instance);

  ApplicationContext release();
}
