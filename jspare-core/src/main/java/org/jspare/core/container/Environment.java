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

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.jspare.core.commons.Definitions;
import org.jspare.core.config.CommonsConfig;
import org.jspare.core.container.ContainerUtils;
import org.jspare.core.container.Key;
import org.jspare.core.container.Qualifier;
import org.jspare.core.exception.EnvironmentException;

import lombok.AllArgsConstructor;
import lombok.Synchronized;

/**
 * The Class Environment.
 *
 * @author pflima
 * @since 05/10/2015
 */
public abstract class Environment {

	@AllArgsConstructor
	public static enum Type {

		DES, HOM, PRD;
	}

	/** The Constant componentKeys. */
	private static final Map<Key, Class<?>> componentKeys = new ConcurrentHashMap<>();

	/** The Constant instances. */
	private static final Map<Class<?>, Object> instances = new ConcurrentHashMap<>();

	/** The Constant CONFIG. */
	public static final CommonsConfig CONFIG = my(CommonsConfig.class);

	/**
	 * Factory.
	 *
	 * @param <T>
	 *            the generic type
	 * @param clazz
	 *            the clazz
	 * @return the t
	 */
	public static <T> T factory(Class<T> clazz) {

		return factory(clazz, StringUtils.EMPTY);
	}

	/**
	 * Factory.
	 *
	 * @param <T>
	 *            the generic type
	 * @param clazz
	 *            the clazz
	 * @param qualifier
	 *            the qualifier
	 * @return the t
	 */
	public static <T> T factory(Class<T> clazz, String qualifier) {

		Class<T> clazzImpl = retrieveClazzImpl(clazz, qualifier);

		try {

			return ContainerUtils.instatiate(clazzImpl);

		} catch (Exception e) {

			throw new EnvironmentException(e);
		}
	}

	/**
	 * Return {@link Type} setted on environment
	 *
	 * @return Type, the environment type
	 */
	public static Type getEnv() {

		String env = System.getProperty(Definitions.ENVIRONMENT_KEY);
		if (StringUtils.isEmpty(env)) {

			env = CONFIG.get(Definitions.ENVIRONMENT_KEY, Type.DES.toString());
		}
		return Type.valueOf(env);
	}

	/**
	 * Validate type of system
	 *
	 * @param type
	 * @return
	 */
	public static boolean isType(Type type) {

		return type.equals(getEnv());
	}

	/**
	 * My.
	 *
	 * @param <T>
	 *            the generic type
	 * @param clazz
	 *            the clazz
	 * @return the t
	 */
	public static <T> T my(Class<T> clazz) {
		return my(clazz, Qualifier.EMPTY);
	}

	/**
	 * My.
	 *
	 * @param <T>
	 *            the generic type
	 * @param clazz
	 *            the clazz
	 * @param qualifier
	 *            the qualifier
	 * @return the t
	 */
	@SuppressWarnings("unchecked")
	public static <T> T my(Class<T> clazz, String qualifier) {

		Class<T> clazzImpl = retrieveClazzImpl(clazz, qualifier);

		if (!instances.containsKey(clazzImpl)) {

			try {

				T instance = ContainerUtils.instatiate(clazzImpl);

				if (ContainerUtils.isAvailableForStoreInstantiate(clazzImpl)) {
					instances.put(clazzImpl, instance);
				}

				return instance;

			} catch (Exception e) {
				throw new EnvironmentException(e);
			}
		}

		return (T) instances.get(clazzImpl);
	}

	/**
	 * Registry component.
	 *
	 * @param clazzImpl
	 *            the clazz impl
	 */
	@Synchronized
	public static void registryComponent(Class<?> clazzImpl) {

		Optional<Class<?>> optionalClazzInterface = ContainerUtils.findComponentInterface(clazzImpl);

		if (!optionalClazzInterface.isPresent() || !ContainerUtils.isAvailableForRegister(clazzImpl)) {
			throw new EnvironmentException(
					String.format("None interface with annotation @Component founded for [%s]", clazzImpl.getName()));
		}

		Class<?> clazzInterface = optionalClazzInterface.get();

		String qualifierName = clazzImpl.isAnnotationPresent(Qualifier.class) ? clazzImpl.getAnnotation(Qualifier.class).value()
				: Qualifier.EMPTY;

		Key key = new Key(clazzInterface, qualifierName);

		componentKeys.put(key, clazzImpl);
	}

	/**
	 * Registry component.
	 *
	 * @param component
	 *            the component
	 */
	@Synchronized
	public static void registryComponent(Object component) {

		registryComponent(component.getClass());

		if (ContainerUtils.isAvailableForStoreInstantiate(component.getClass())) {
			instances.put(component.getClass(), component);
		}
	}

	/**
	 * Release the container; <br>
	 * Note: Carefull with this method, all components will be cleared.
	 *
	 */
	public static void release() {

		componentKeys.clear();
		instances.clear();
	}

	/**
	 * Scan and registry components.
	 *
	 * @param componentsPackage
	 *            the components package
	 * @throws EnvironmentException
	 *             the environment exception
	 */
	public static void scanAndRegistryComponents(List<String> componentsPackage) throws EnvironmentException {
		componentsPackage.forEach(ContainerUtils::performComponentScanner);
	}

	/**
	 * Retrieve clazz impl.
	 *
	 * @param <T>
	 *            the generic type
	 * @param clazz
	 *            the clazz
	 * @param qualifier
	 *            the qualifier
	 * @return the class
	 */
	@SuppressWarnings("unchecked")
	private static <T> Class<T> retrieveClazzImpl(Class<T> clazz, String qualifier) {
		Key key = new Key(clazz, qualifier);

		Class<T> clazzImpl = null;

		if (!componentKeys.containsKey(key)) {

			if (!StringUtils.isEmpty(qualifier)) {
				throw new EnvironmentException(
						String.format("None implementation registered for class %s with Qualifier [%s]", clazz.getSimpleName(), qualifier));
			}

			clazzImpl = (Class<T>) ContainerUtils.findClazzImpl(clazz);
			registryComponent(clazzImpl);

		} else {

			clazzImpl = (Class<T>) componentKeys.get(key);
		}
		return clazzImpl;
	}
}