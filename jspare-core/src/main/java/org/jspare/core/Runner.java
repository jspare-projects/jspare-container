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

import lombok.SneakyThrows;

/**
 * The Interface Core.
 */
public interface Runner {

  /**
   * The Create method is responsible for instantiate new Application by
   * Reflection.
   *
   * @param bootstrapClazz the bootstrap clazz
   * @return the application
   */
  @SneakyThrows
  static Runner create(Class<? extends Runner> bootstrapClazz) {
    return bootstrapClazz.newInstance();
  }

  /**
   * The Run method is responsible for invoking the application, the
   * application life cycle depends on the call of this method.
   *
   * @param bootstrapClazz the bootstrap clazz
   */
  @SneakyThrows
  static void run(Class<? extends Runner> bootstrapClazz) {

    Runner instance = create(bootstrapClazz);
    instance.run();
  }

  /**
   * The Run method is responsible for invoking the application, the
   * application life cycle depends on the call of this method.
   */
  @SneakyThrows
  default void run() {

    setup();

    mySupport();

    start();
  }

  /**
   * Starts the application after the entire application lifecycle is loaded,
   * at this point the application is ready to create all the components and
   * resources.
   *
   * @throws Exception the exception
   */
  void start() throws Exception;

  /**
   * The My support. After initializing the components through the setup
   * method, this method is invoked to supply any dependency injection in the
   * application class.
   */
  default void mySupport() {
    Environment.inject(this);
  }

  /**
   * The setup method, configures the application, it is the first method
   * invoked when starting the application initialization stream, this method
   * must be overwritten when there is a need to dictate new behaviors for the
   * components of an application.
   */
  default void setup() {
  }
}
