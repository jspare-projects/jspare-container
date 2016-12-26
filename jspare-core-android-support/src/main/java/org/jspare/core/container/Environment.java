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

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.jspare.core.annotation.Inject;
import org.jspare.core.annotation.Qualifier;
import org.jspare.core.container.strategy.InjectStrategy;
import org.jspare.core.exception.EnvironmentException;
import org.jspare.core.exception.Errors;

import lombok.Synchronized;

/**
 * The Class Environment.
 *
 * Responsible for the whole framework of the container control. The environment
 * is responsible for the retention and display of framework components. Through
 * this class has access to static methods that ensure secure access to
 * instances of components registered in the environment.
 *
 * @author pflima
 * @since 05/10/2015
 */
public abstract class Environment {

	/** The Constant RES_INITIAL_CAPACITY. */
	private static final int RES_INITIAL_CAPACITY = 10;

	/** The Constant RES_LOAD_FACTOR. */
	private static final float RES_LOAD_FACTOR = 0.85f;

	/** The Constant INJECTORS_INITIAL_CAPACITY. */
	private static final int INJECTORS_INITIAL_CAPACITY = 8;

	/** The Constant INJECTORS_LOAD_FACTOR. */
	private static final float INJECTORS_LOAD_FACTOR = 0.5f;

	/** The Constant INJECTORS. */
	protected static final Map<Class<? extends Annotation>, InjectorStrategy> INJECTORS = new ConcurrentHashMap<>(
			INJECTORS_INITIAL_CAPACITY, INJECTORS_LOAD_FACTOR);;

	/** The Constant componentKeys. */
	private static final Map<ComponentKey, Class<?>> KEY_2_IMPL = new ConcurrentHashMap<>(RES_INITIAL_CAPACITY, RES_LOAD_FACTOR);

	/** The Constant instances. */
	private static final Map<Class<?>, Object> IMPL_2_INSTANCE = new ConcurrentHashMap<>(RES_INITIAL_CAPACITY, RES_LOAD_FACTOR);

	static {

		// Initialize Injectors of Environment
		registryInjector(Inject.class, new InjectStrategy());
	}

	/**
	 * The Factory method
	 *
	 * Is responsible for retrieving a new instance of a system component or
	 * resource, processing and injecting dependency into a class retrieved
	 * through this method obeys the injection cycle normally.
	 *
	 * @param <T>
	 *            the generic type
	 * @param clazz
	 *            the clazz
	 * @return the t
	 */
	public static <T> T factory(Class<T> clazz) {

		return factory(clazz, Qualifier.EMPTY);
	}

	/**
	 * The Factory Method
	 * 
	 * Is responsible for retrieving a new instance of a system component or
	 * resource, processing and injecting dependency into a class retrieved
	 * through this method obeys the injection cycle normally. <br>
	 * When passing a qualifier the class registered as qualified will be the
	 * one instantiated.
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

		if (ContainerUtils.isValidResource(clazz)) {

			return ContainerUtils.instatiate(clazz);
		} else {

			Class<T> clazzImpl = retrieveClazzImpl(clazz, qualifier);
			return ContainerUtils.instatiate(clazzImpl);
		}
	}

	/**
	 * My.
	 *
	 * This method is responsible for retrieving the implementation of a
	 * component of the environment, the instance of the class will be recovered
	 * which is registered in the environment, noting that the scope and other
	 * states should be set to register a new component in the environment when
	 * performing this method will be recovered an implementation that is
	 * already registered in the environment, if the component has not yet been
	 * registered, the method should register a common implementation, this one
	 * is is available and provide a memory reference.
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
	 * This method is responsible for retrieving the implementation of a
	 * component of the environment, the instance of the class will be recovered
	 * which is registered in the environment, noting that the scope and other
	 * states should be set to register a new component in the environment when
	 * performing this method will be recovered an implementation that is
	 * already registered in the environment, if the component has not yet been
	 * registered, the method should register a common implementation, this one
	 * is is available and provide a memory reference.
	 *
	 * Receive the qualifier for qualify the injection, note: the implementation
	 * class need be annotated with {@link Qualifier}
	 *
	 * @param <T>
	 *            the generic type
	 * @param clazz
	 *            the clazz
	 * @param qualifier
	 *            the qualifier
	 * @return the t
	 */
	public static <T> T my(Class<T> clazz, String qualifier) {

		if (ContainerUtils.isValidResource(clazz)) {

			return transformResource(clazz);
		}

		return transformComponent(clazz, qualifier);
	}

	/**
	 * Registry component on Environment.
	 *
	 * Registering your class implementation on environment your component are
	 * holded by Environmet, and available for invertion of control.
	 *
	 * @param clazzImpl
	 *            the clazz impl
	 */
	@Synchronized
	public static void registryComponent(Class<?> clazzImpl) {

		Class<?> clazzInterface = ContainerUtils.findComponentInterface(clazzImpl);

		if (clazzInterface == null || !ContainerUtils.isAvailableForRegister(clazzImpl)) {

			throw new EnvironmentException(Errors.NO_COMPONENT_FOUNDED_CLASS.arguments(clazzImpl.getName()));
		}

		String qualifierName = clazzImpl.isAnnotationPresent(Qualifier.class) ? clazzImpl.getAnnotation(Qualifier.class).value()
				: Qualifier.EMPTY;

		ComponentKey key = new ComponentKey(clazzInterface, qualifierName);

		KEY_2_IMPL.put(key, clazzImpl);
	}

	/**
	 * Registering your intance implementation on environment your component are
	 * holded by Environmet, and available for invertion of control.
	 *
	 * @param component
	 *            the component
	 */
	@Synchronized
	public static void registryComponent(Object component) {

		registryComponent(component.getClass());

		if (ContainerUtils.isValidComponent(component.getClass())) {
			IMPL_2_INSTANCE.put(component.getClass(), component);
		}
	}

	/**
	 * Registry injector method
	 * 
	 * Method responsible for registering in the environment all the injection
	 * dependency injection strategy and inversion of the application.
	 * 
	 * It is necessary to make the identification of the dependency and pass the
	 * instance already started to the environment
	 *
	 * @param annClazz
	 *            the ann clazz
	 * @param injector
	 *            the injector
	 */
	public static void registryInjector(Class<? extends Annotation> annClazz, InjectorStrategy injector) {

		INJECTORS.put(annClazz, injector);
	}

	/**
	 * Registry resource on Environment.
	 * 
	 * Registering your class implementation on environment your component are
	 * holded by Environmet, and available for invertion of control.
	 *
	 * @param resource
	 *            the resource
	 */
	public static void registryResource(Object resource) {

		if (!ContainerUtils.isValidResource(resource.getClass())) {

			throw new EnvironmentException(Errors.INVALID_RESOURCE_CLASS.arguments(resource.getClass()));
		}

		IMPL_2_INSTANCE.put(resource.getClass(), resource);
	}

	/**
	 * Release the container; <br>
	 * Note: Carefull with this method, all components will be cleared.
	 *
	 */
	public static void release() {

		KEY_2_IMPL.clear();
		IMPL_2_INSTANCE.clear();
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
		for (String cmpt : componentsPackage) {
			ContainerUtils.performComponentScanner(cmpt);
		}
	}

	@SuppressWarnings("unchecked")
	protected static <T> T transformComponent(Class<T> clazz, String qualifier) {
		Class<T> clazzImpl = retrieveClazzImpl(clazz, qualifier);

		if (!IMPL_2_INSTANCE.containsKey(clazzImpl)) {

			T instance = ContainerUtils.instatiate(clazzImpl);

			if (ContainerUtils.isValidComponent(clazzImpl)) {
				IMPL_2_INSTANCE.put(clazzImpl, instance);
			}

			return instance;
		}

		return (T) IMPL_2_INSTANCE.get(clazzImpl);
	}

	@SuppressWarnings("unchecked")
	protected static <T> T transformResource(Class<T> clazz) {
		if (!IMPL_2_INSTANCE.containsKey(clazz)) {

			T instance = ContainerUtils.instatiate(clazz);
			IMPL_2_INSTANCE.put(clazz, instance);
			return instance;

		}
		return (T) IMPL_2_INSTANCE.get(clazz);
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

		ComponentKey key = new ComponentKey(clazz, qualifier);

		Class<T> clazzImpl = null;

		if (!KEY_2_IMPL.containsKey(key)) {

			if (StringUtils.isNotEmpty(qualifier)) {
				throw new EnvironmentException(Errors.NO_QUALIFIER_REGISTERED.arguments(clazz.getSimpleName(), qualifier));
			}

			clazzImpl = (Class<T>) ContainerUtils.findClazzImpl(clazz);
			registryComponent(clazzImpl);

		} else {

			clazzImpl = (Class<T>) KEY_2_IMPL.get(key);
		}
		return clazzImpl;
	}
}