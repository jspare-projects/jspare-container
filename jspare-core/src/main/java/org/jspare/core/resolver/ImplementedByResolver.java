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

import org.jspare.core.ImplementationResolver;
import org.jspare.core.ImplementedBy;

/**
 * The {@link ImplementedByResolver}
 * <p>Resolves an implementation when annotated from annotation {@link ImplementedBy}. The defined class will be the implementation registered in the container.</p>
 *
 *  @author <a href="https://pflima92.github.io/">Paulo Lima</a>
 */
public class ImplementedByResolver implements ImplementationResolver {

  @Override
  public Class<?> supply(Class<?> classInterface) {
    // Ignore non interfaces without component module
    if (!classInterface.isInterface() || !classInterface.isAnnotationPresent(ImplementedBy.class)) {
      return null;
    }
    return classInterface.getAnnotation(ImplementedBy.class).value();
  }
}
