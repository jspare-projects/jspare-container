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

import lombok.RequiredArgsConstructor;
import org.jspare.core.ImplementationResolver;
import org.jspare.core.exception.EnvironmentException;
import org.jspare.core.exception.Errors;

import java.util.List;
import java.util.Objects;

/**
 *  @author <a href="https://pflima92.github.io/">Paulo Lima</a>
 */
@RequiredArgsConstructor
public class InternalImplementationFinder {

  private final List<ImplementationResolver> IMPLEMENTATION_PROVIDERS;

  public Class<?> find(Class<?> clazz, String name) {

    // Return self if is non interface
    if (!clazz.isInterface()) {
      return clazz;
    }

    Class<?> candidate = null;
    for (ImplementationResolver ip : IMPLEMENTATION_PROVIDERS) {
      candidate = ip.supply(clazz);
      if (Objects.nonNull(candidate)) break;
    }

    if (candidate != null && !ReflectionUtils.getQualifier(candidate).equals(name)) {

      throw new EnvironmentException(Errors.NO_QUALIFIER_REGISTERED);
    }
    return candidate;
  }
}
