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
import org.jspare.core.ApplicationContext;
import org.jspare.core.ImplementationResolver;
import org.jspare.core.InjectorAdapter;
import org.jspare.core.MembersInjector;
import org.jspare.core.resolver.ComponentResolver;
import org.jspare.core.resolver.ImplementedByResolver;

import javax.inject.Provider;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="https://pflima92.github.io/">Paulo Lima</a>
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

  private final InternalBinder BINDER;

  public ApplicationContextImpl() {
    IMPLEMENTATION_PROVIDERS.add(new ComponentResolver());
    IMPLEMENTATION_PROVIDERS.add(new ImplementedByResolver());
    BINDER = new InternalBinder(IMPLEMENTATION_PROVIDERS);
  }

  @Override
  public <T> T getInstance(Class<T> clazz) {
    return getInstance(clazz, StringUtils.EMPTY);
  }

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
  public void inject(Object instance) {
    MembersInjector membersInject = new MembersInjectorImpl(INJECTORS);
    membersInject.inject(instance);
  }

  @Override
  public ApplicationContext release() {
    BINDERS.clear();
    HOLDER.clear();
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
