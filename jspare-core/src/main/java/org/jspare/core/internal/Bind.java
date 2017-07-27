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

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang.StringUtils;

/**
 * @author <a href="https://pflima92.github.io/">Paulo Lima</a>
 */

@Data
@Accessors(fluent = true)
public class Bind<T> {
  private Class<T> from;
  private String name = StringUtils.EMPTY;
  private Class<?> to;
  private boolean singleton;

  public static <T> Bind<T> bind(Class<T> clazz) {
    return new Bind<T>().from(clazz);
  }

  public Key bindKey() {
    return new Key(from, name);
  }
}
