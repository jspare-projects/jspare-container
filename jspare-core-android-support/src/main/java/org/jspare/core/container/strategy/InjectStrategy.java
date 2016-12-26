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
package org.jspare.core.container.strategy;

import static org.jspare.core.container.Environment.factory;
import static org.jspare.core.container.Environment.my;

import java.lang.reflect.Field;

import org.jspare.core.annotation.Inject;
import org.jspare.core.annotation.Qualifier;
import org.jspare.core.container.InjectorStrategy;
import org.jspare.core.exception.EnvironmentException;
import org.jspare.core.exception.Errors;

/**
 * The Class InjectStrategy.
 *  
 *  Strategy responsible to provide dependency injection by {@link Inject } annotation.
 * 
 */
public class InjectStrategy implements InjectorStrategy {

	/* (non-Javadoc)
	 * @see org.jspare.core.container.InjectorStrategy#inject(java.lang.Object, java.lang.reflect.Field)
	 */
	@Override
	public void inject(Object result, Field field) {

		try {

			Inject inject = field.getAnnotation(Inject.class);

			String qualifier = Qualifier.EMPTY;
			if (field.isAnnotationPresent(Qualifier.class)) {
				qualifier = field.getAnnotation(Qualifier.class).value();
			}
			field.setAccessible(true);

			if (inject.factory()) {

				field.set(result, factory(Class.forName(field.getType().getName()), qualifier));
			} else {

				field.set(result, my(Class.forName(field.getType().getName()), qualifier));
			}
		} catch (IllegalArgumentException | IllegalAccessException | ClassNotFoundException e) {

			throw new EnvironmentException(Errors.INVALID_INJECTION.throwable(e));
		}
	}
}