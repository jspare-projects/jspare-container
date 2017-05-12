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
package org.jspare.core.resolver;

import org.jspare.core.Component;
import org.jspare.core.ImplementationResolver;
import org.jspare.core.exception.EnvironmentException;
import org.jspare.core.exception.Errors;

/**
 * Created by paulo.ferreira on 08/05/2017.
 */
public class ComponentResolver implements ImplementationResolver {

  private static final String SUFIX_DEFAULT_IMPL = "Impl";
  private static final String PCK_DEFAULT_IMPL = "%s.impl.%sImpl";

  @Override
  public Class<?> supply(Class<?> classInterface) {
    // Ignore non interfaces without component annotation
    if (!classInterface.isInterface() || !classInterface.isAnnotationPresent(Component.class)) {
      return null;
    }

    // Find implementation class by conventions
    Class<?> clazzImpl = null;
    try {

      return Class.forName(classInterface.getName().concat(SUFIX_DEFAULT_IMPL));
    } catch (ClassNotFoundException e) {
      // Next atempt
    }

    try {
      return clazzImpl = Class.forName(String.format(PCK_DEFAULT_IMPL, classInterface.getPackage().getName(), classInterface.getSimpleName()));
    } catch (ClassNotFoundException e) {

      throw new EnvironmentException(Errors.NO_CMPT_REGISTERED.arguments(classInterface.getName()).throwable(e));
    }
  }
}
