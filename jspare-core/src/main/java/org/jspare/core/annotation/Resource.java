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
package org.jspare.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jspare.core.container.Environment;

/**
 * The Interface Resource.
 *
 * <br>
 *
 * Is defined as a stereotype for injection and sharing within an application. A
 * Resource defines an class of implementation that is capable of being
 * manipulated and managed by the environment. <br>
 *
 * Every resource registered and mapped by the {@link Environment } can be
 * retrieved through the injection of dependencies within the application, the
 * life cycle of this component starts when it is called through the
 * environment.
 *
 * <br>
 *
 * Notes:
 *
 * <ul>
 * <li>A resource can not be exposed as an interface since it is the direct
 * implementation.</li>
 * </ul>
 *
 * @author pflima
 * @since 05/10/2015
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Resource {

}