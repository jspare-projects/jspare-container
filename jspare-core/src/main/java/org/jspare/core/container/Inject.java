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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jspare.core.container.Injector;

/**
 * The Interface Component.
 *
 * @author pflima
 * @since 05/10/2015
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface Inject {

	/**
	 * The Class NotDefined.
	 *
	 * @author pflima
	 * @since 30/03/2016
	 */
	class NotDefined {

		/**
		 * Instantiates a new not defined.
		 */
		private NotDefined() {

		}
	}

	/**
	 * Value.
	 *
	 * @return the class
	 */
	Class<?> value() default NotDefined.class;

	@SuppressWarnings("rawtypes")
	Class<? extends Injector> injector() default Injector.DefaultInjection.class;
}