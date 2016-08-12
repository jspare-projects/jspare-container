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

import static org.jspare.core.container.Environment.factory;
import static org.jspare.core.container.Environment.my;
import static org.jspare.core.container.Environment.registryComponent;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

import org.jspare.core.exception.EnvironmentException;
import org.jspare.core.scanner.ComponentScanner;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ContainerUtils {

	/** The Constant SUFIX_DEFAULT_IMPL. */
	private static final String SUFIX_DEFAULT_IMPL = "Impl";

	/**
	 * Process injection.
	 *
	 * @param clazz
	 *            the clazz
	 * @param result
	 *            the result
	 */
	public static void processInjection(Class<?> clazz, Object result) {
		try {

			for (Field field : clazz.getDeclaredFields()) {
				if (field.isAnnotationPresent(Inject.class)) {
					if (!field.getType().isInterface()) {
						throw new EnvironmentException(
								"none interface with annotation @Component founded (" + field.getType().getName() + ")");
					}

					String qualifier = Qualifier.EMPTY;
					if (field.isAnnotationPresent(Qualifier.class)) {
						qualifier = field.getAnnotation(Qualifier.class).value();
					}
					field.setAccessible(true);

					if (field.isAnnotationPresent(Factory.class)) {

						field.set(result, factory(Class.forName(field.getType().getName()), qualifier));
					} else {

						field.set(result, my(Class.forName(field.getType().getName()), qualifier));
					}
				}
			}

			for (Method method : clazz.getDeclaredMethods()) {
				if (method.isAnnotationPresent(After.class)) {

					method.setAccessible(true);
					method.invoke(result);
				}

			}

		} catch (StackOverflowError | IllegalArgumentException | IllegalAccessException | ClassNotFoundException
				| InvocationTargetException e) {

			throw new EnvironmentException("Invalid component injection founded.");

		}
	}

	/**
	 * Find clazz impl.
	 *
	 * @param clazz
	 *            the clazz
	 * @return the class
	 */
	protected static Class<?> findClazzImpl(Class<?> clazz) {

		Class<?> clazzImpl;
		try {

			clazzImpl = Class.forName(clazz.getName().concat(SUFIX_DEFAULT_IMPL));

		} catch (ClassNotFoundException e) {

			throw new EnvironmentException(String.format(
					"%s don't have default implementation class. Provide default implementation or registry one.", clazz.getName()), e);
		}

		return clazzImpl;
	}

	/**
	 * Find component interface.
	 *
	 * @param clazz
	 *            the clazz
	 * @return the class
	 */
	protected static Optional<Class<?>> findComponentInterface(Class<?> clazz) {
		Class<?>[] interfaces = clazz.getInterfaces();
		for (Class<?> candidate : interfaces) {
			if (candidate.getAnnotation(Component.class) != null) {
				return Optional.of(candidate);
			}
		}

		return Optional.empty();
	}

	/**
	 * Instatiate.
	 *
	 * @param <T>
	 *            the generic type
	 * @param clazz
	 *            the clazz
	 * @return the t
	 * @throws InstantiationException
	 *             the instantiation exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 */
	protected static <T> T instatiate(Class<T> clazz) throws InstantiationException, IllegalAccessException {

		T result = clazz.newInstance();

		ContainerUtils.processInjection(clazz, result);

		return result;
	}

	/**
	 * Checks if is available for register.
	 *
	 * @param clazzImpl
	 *            the clazz impl
	 * @return true, if is available for register
	 */
	protected static boolean isAvailableForRegister(Class<?> clazzImpl) {

		Optional<Class<?>> optionalClazzInterface = findComponentInterface(clazzImpl);

		if (!optionalClazzInterface.isPresent() || !optionalClazzInterface.get().isAnnotationPresent(Component.class)) {

			return false;
		}

		return true;
	}

	/**
	 * Checks if is available for store instantiate.
	 *
	 * @param clazzImpl
	 *            the clazz impl
	 * @return true, if is available for store instantiate
	 */
	protected static boolean isAvailableForStoreInstantiate(Class<?> clazzImpl) {

		Optional<Class<?>> optionalClazzInterface = findComponentInterface(clazzImpl);

		if (!optionalClazzInterface.isPresent() || !isAvailableForRegister(clazzImpl)) {

			return false;
		}

		Component component = optionalClazzInterface.get().getAnnotation(Component.class);

		return !component.scope().equals(Scope.FACTORY);
	}

	/**
	 * Perform component scanner.
	 *
	 * @param componentScanner
	 *            the component scanner
	 * @throws EnvironmentException
	 *             the environment exception
	 */
	protected static void performComponentScanner(String componentScanner) throws EnvironmentException {
		// Validate if component contain all scan quote.
		if (!componentScanner.endsWith(ComponentScanner.ALL_SCAN_QUOTE)) {
			try {

				Class<?> clazz = Class.forName(componentScanner);
				registryComponent(clazz);

				return;

			} catch (ClassNotFoundException e) {

				throw new EnvironmentException(e);
			}
		}

		my(ComponentScanner.class).scanAndExecute(componentScanner, (clazzName) -> {
			try {

				Class<?> clazz = Class.forName((String) clazzName[0]);

				if (isAvailableForRegister(clazz)) {
					registryComponent(clazz);
				}

			} catch (Exception e) {

				log.error(e.getMessage(), e);
			}
			return Void.TYPE;
		});

	}
}
