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

import lombok.NonNull;
import lombok.Synchronized;
import org.apache.commons.lang.StringUtils;
import org.jspare.core.*;
import org.jspare.core.exception.EnvironmentException;
import org.jspare.core.exception.Errors;
import org.jspare.core.resolver.ComponentResolver;

import javax.inject.Provider;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by paulo.ferreira on 10/05/2017.
 */
public class ApplicationContextImpl implements ApplicationContext {

  /**
   * The Constant Resource initial capacity.
   */
  private static final int RIC = 10;

  /**
   * The Constant Resource create factor.
   */
  private static final float RLF = 0.85f;

  private final Map<Class<? extends InjectorAdapter>, InjectorAdapter> INJECTORS = new HashMap<>();
  private final Map<Key, Bind> BINDERS = new ConcurrentHashMap<>(RIC, RLF);
  private final Map<Key, Object> HOLDER = new ConcurrentHashMap<>(RIC, RLF);
  private final List<ImplementationResolver> IMPLEMENTATION_PROVIDERS = new ArrayList<>();
  private final List<Class<?>> LOADED_MODULES = new ArrayList<>();

  private final InternalBinder BINDER;

  public ApplicationContextImpl() {

    IMPLEMENTATION_PROVIDERS.add(new ComponentResolver());
    BINDER = new InternalBinder(IMPLEMENTATION_PROVIDERS);
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
  @Override
  public <T> T getInstance(Class<T> clazz) {

    return getInstance(clazz, StringUtils.EMPTY);
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
  @Override
  public <T> T getInstance(Class<T> clazz, String qualifier) {

    Bind<T> bind = retrieveBind(clazz, qualifier);
    Key key = bind.bindKey();
    if (!HOLDER.containsKey(key)) {

      T instance = instantiate(bind);

      if (bind.singleton()) {

        registry(bind, instance);
      }
      return instance;
    }
    //noinspection unchecked
    return (T) HOLDER.get(key);
  }

  private <T> T instantiate(Bind<T> bind) {
    Provider<T> provider = new InstanceFactory<>(this, bind);
    return provider.get();
  }

  @Override
  public <T> T provide(Class<T> clazz) {

    return provide(clazz, StringUtils.EMPTY);
  }

  @Override
  public <T> T provide(Class<T> clazz, String qualifier) {

    Bind<T> bind = retrieveBind(clazz, qualifier);
    return instantiate(bind);
  }

  @Synchronized
  @Override
  public ApplicationContext registry(@NonNull Bind bind) {
    bind = BINDER.createBind(bind);
    BINDERS.put(bind.bindKey(), bind);
    return this;
  }

  @Synchronized
  @Override
  public ApplicationContext registry(@NonNull Bind bind, @NonNull Object instance) {
    // Registry ClassImpl on InternalBinder
    //noinspection unchecked
    bind.to(instance.getClass());

    registry(bind);

    // Registry Holder
    HOLDER.put(bind.bindKey(), instance);
    return this;
  }


  @Override
  public ApplicationContext addImplementationProvider(ImplementationResolver provider) {
    IMPLEMENTATION_PROVIDERS.add(provider);
    return this;
  }

  @Override
  public ApplicationContext addInjector(InjectorAdapter injector) {
    INJECTORS.put(injector.getClass(), injector);
    return this;
  }

  @Override
  public ApplicationContext loadModule(Class<? extends Module> clazz) {

    // If Modules is loaded
    if (LOADED_MODULES.contains(clazz)) {
      return this;
    }

    try {

      Module module = clazz.newInstance();
      module.load();
      LOADED_MODULES.add(clazz);
      return this;
    } catch (InstantiationException | IllegalAccessException e) {
      throw new EnvironmentException(Errors.FAILED_INSTANTIATION);
    }
  }

  @Override
  public ApplicationContext loadModule(Module module) {
    // If Modules is loaded
    if (LOADED_MODULES.contains(module.getClass())) {
      return this;
    }
    module.load();
    LOADED_MODULES.add(module.getClass());
    return this;
  }

  @Override
  public void inject(Object instance) {

    MembersInjector membersInject = new MembersInjectorImpl(INJECTORS);
    membersInject.inject(instance);
  }

  /**
   * Release the container; <br>
   * Note: Carefull with this method, all components will be cleared.
   */
  @Override
  public ApplicationContext release() {

    BINDERS.clear();
    HOLDER.clear();
    LOADED_MODULES.clear();
    return this;
  }

  private <T> Bind<T> retrieveBind(Class<T> clazz, String name) {

    Key key = new Key(clazz, name);
    // Find Bind on Container
    Bind bind = BINDERS.get(key);

    if (Objects.isNull(bind)) {

      // Create Bind with magic of reflection
      bind = BINDER.createBind(key);
      BINDERS.put(key, bind);
    }
    return bind;
  }
}
