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
 * The {@link ComponentResolver}
 * <p>Resolves an implementation when annotated from annotation {@link Component}.</p>
 * <p>The expected behavior for this revolver is to fetch the implementations that follow the following convention:</p>
 * <ul>
 * <li>The implementation class must be in the same interface package with same class name and use the suffix <b>Impl</b>. For e.g: Interface: foo.Component Implementation: foo.ComponentImpl </li>
 * <li>Or the implementation class must be in impl package after original package. For e.g: Interface: foo.Component Implementation: foo.impl.ComponentImpl.</li>
 * </ul>
 *
 * @author <a href="https://pflima92.github.io/">Paulo Lima</a>
 */
public class ComponentResolver implements ImplementationResolver {

  private static final String SUFIX_DEFAULT_IMPL = "Impl";
  private static final String PCK_DEFAULT_IMPL = "%s.impl.%sImpl";

  @Override
  public Class<?> supply(Class<?> classInterface) {
    // Ignore non interfaces without component module
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
