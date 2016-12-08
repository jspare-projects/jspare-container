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

import java.lang.reflect.Field;

/**
 * The Interface Injector.
 *
 * It is responsible for defining the way of instantiation of a component used
 * by the annotation injection.
 *
 */
public interface InjectorStrategy {

	/**
	 * Inject method.
	 * 
	 * It is called when container invoke the process of injection of control.
	 *
	 * @param result
	 *            the result
	 * @param field
	 *            the field
	 */
	void inject(Object result, Field field);
}