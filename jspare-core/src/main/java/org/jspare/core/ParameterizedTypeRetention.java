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

import java.lang.reflect.Type;

/**
 * The Interface ParameterizedTypeRetention. <br>
 * <p>
 * Makes the retention of a parameterized type in a class, this interface should
 * be implemented when you have the need to retain the data on the type
 * parameterized generic and use the inversion of control at the same time.
 *
 * @author <a href="https://pflima92.github.io/">Paulo Lima</a>
 */
public interface ParameterizedTypeRetention {

  /**
   * Sets the types.
   *
   * @param types the new types
   */
  void setTypes(Type[] types);
}
