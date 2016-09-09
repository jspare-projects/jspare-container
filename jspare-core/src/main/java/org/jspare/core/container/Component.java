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
package org.jspare.core.container;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * The Interface Component.
 *
 * <br>
 *
 * A component defines an interface that is capable of being manipulated and
 * managed by the environment. <br>
 * The behavior that exerts a component in an interface provides the usage is
 * made by inverting control between objects. Thus, when a component is declared
 * to have an implementation registered in the environment must, by default and
 * convention container, any kind suffixed <b>Impl</b> It will be assumed to
 * default implementation, provided it is in the same package structure.
 *
 * @author pflima
 * @since 05/10/2015
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Component {

	/**
	 * Scope of Component on Environment, default is Application thaht represent
	 * one Singleton.
	 *
	 * @return the scope
	 */
	Scope scope() default Scope.APPLICATION;
}