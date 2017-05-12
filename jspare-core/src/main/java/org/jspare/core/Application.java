/*
 * Copyright 2016 JSpare.org.
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
 * The Class Application.
 * <p>
 * <br>
 * <p>
 * Class used to perform the bootstrapping an application using the framework
 * <p>
 * The life cycle used when start are called following methods of the class:
 * <ul>
 * <li>setup</li>
 * <li>mySupport</li>
 * <li>build all builders</li>
 * <li>start</li>
 * </ul>
 */
public abstract class Application implements Runner {

  /**
   * The Create method is responsible for instantiate new Application by
   * Reflection.
   *
   * @param bootstrapClazz the bootstrap clazz
   * @return the application
   */
  @SneakyThrows
  public static Runner create(Class<? extends Runner> bootstrapClazz) {
    return bootstrapClazz.newInstance();
  }

  /**
   * The Run method is responsible for invoking the application, the
   * application life cycle depends on the call of this method.
   *
   * @param bootstrapClazz the bootstrap clazz
   */
  @SneakyThrows
  public static void run(Class<? extends Runner> bootstrapClazz) {

    Runner instance = create(bootstrapClazz);
    instance.run();
  }

  /**
   * The Run method is responsible for invoking the application, the
   * application life cycle depends on the call of this method.
   */
  public void run() {

    Environment.create();

    long start = System.currentTimeMillis();

    setup();

    mySupport();

    long end = System.currentTimeMillis();

    start();
  }

  /**
   * Starts the application after the entire application lifecycle is loaded,
   * at this point the application is ready to create all the components and
   * resources.
   */
  public abstract void start();
}
