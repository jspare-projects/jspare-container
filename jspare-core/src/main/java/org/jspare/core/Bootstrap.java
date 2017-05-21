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
 * The Class Bootstrap.
 */
public class Bootstrap {

  @SneakyThrows
  @SuppressWarnings("unchecked")
  public static void main(String[] args) {

    if (args.length < 1) {

      throw new RuntimeException("Cannot start application, no class specified for initialization");
    }

    String className = args[0];
    Class<?> clazz = Class.forName(className);

    if (!clazz.isAssignableFrom(Runner.class)) {

      throw new RuntimeException("The class informed cannot be initialized by container, make sure that implemens Runner interface.");
    }

    Runner.run((Class<? extends Runner>) clazz);
  }
}
