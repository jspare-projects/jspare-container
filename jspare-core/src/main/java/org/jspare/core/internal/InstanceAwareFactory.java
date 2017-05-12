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

import org.jspare.core.Factory;
import org.jspare.core.InjectorAdapter;

/**
 * Created by paulo.ferreira on 11/05/2017.
 */
public class InstanceAwareFactory<T> implements Factory<T> {

  private final InjectorAdapter injector;
  private final Class<T> clazz;

  InstanceAwareFactory(InjectorAdapter injector, Class<T> clazz) {
    this.injector = injector;
    this.clazz = clazz;
  }

  @Override
  public T get() {
    // TODO
    return (T) injector.get();
  }
}
