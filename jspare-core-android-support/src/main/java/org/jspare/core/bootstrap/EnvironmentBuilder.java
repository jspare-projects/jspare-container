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
package org.jspare.core.bootstrap;

import static org.jspare.core.container.Environment.scanAndRegistryComponents;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import org.jspare.core.container.Environment;
import org.jspare.core.container.InjectorStrategy;
import org.jspare.core.exception.EnvironmentException;
import org.jspare.core.exception.Errors;

import lombok.extern.slf4j.Slf4j;

/** The Constant log. */
@Slf4j
/**
 *
 * Builder your Environment with this implementation of Builder.
 *
 */
public class EnvironmentBuilder implements Builder {

	/**
	 * Creates the.
	 *
	 * @return the application builder
	 */
	public static EnvironmentBuilder create() {

		return new EnvironmentBuilder();
	}

	/** The bundles. */
	private List<Class<? extends Bundle>> bundles;

	/** The clazz components. */
	private List<Class<?>> clazzComponents;

	/** The declared components. */
	private List<String> declaredComponents;
	
	/**
	 * Instantiates a new application builder.
	 */
	public EnvironmentBuilder() {

		bundles = new ArrayList<>();
		clazzComponents = new ArrayList<>();
		declaredComponents = new ArrayList<>();
	}

	/**
	 * Adds the bundle.
	 *
	 * @param bundleClazz
	 *            the bundle clazz
	 * @return the application builder
	 */
	public EnvironmentBuilder addBundle(Class<? extends Bundle> bundleClazz) {

		bundles.add(bundleClazz);
		return this;
	}
	
	public EnvironmentBuilder addInjector(Class<? extends Annotation> annClazz, InjectorStrategy injector) {

		Environment.registryInjector(annClazz, injector);
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.core.container.Builder#build()
	 */
	@Override
	public void build() {
		
		log.info("Loading declared components");
		scanAndRegistryComponents(declaredComponents);

		log.info("Loading declared clazz components");
		for (Class<?> clazz : clazzComponents) {
			Environment.registryComponent(clazz);
		}

		log.info("Loading bundles");
		for (Class<? extends Bundle> clazz : bundles) {
			
			log.info("Loading bundle [{}]", clazz.getName());
			try {
				Bundle instance = clazz.newInstance();
				instance.registryComponents();
			} catch (InstantiationException | IllegalAccessException e) {

				throw new EnvironmentException(Errors.FAILED_INSTANTIATION.throwable(e));
			}
		}
	}

	/**
	 * Registry component.
	 *
	 * @param clazz
	 *            the clazz
	 * @return the application builder
	 */
	public EnvironmentBuilder registryComponent(Class<?> clazz) {

		clazzComponents.add(clazz);
		return this;
	}

	/**
	 * Registry component.
	 *
	 * @param clazz
	 *            the clazz
	 * @return the application builder
	 */
	public EnvironmentBuilder registryComponent(String clazz) {

		declaredComponents.add(clazz);
		return this;
	}

	/**
	 * Scan one class or package for add to environment of container.
	 *
	 * @param package2scan
	 *            the package2scan
	 * @return the application builder
	 */
	public EnvironmentBuilder scan(String package2scan) {

		String packageFormated = package2scan.endsWith(".*") ? package2scan : String.format("%s.*", package2scan);
		declaredComponents.add(packageFormated);
		return this;
	}
}