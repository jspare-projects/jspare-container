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

import java.util.ArrayList;
import java.util.List;

import org.jspare.core.exception.InfraException;

import lombok.extern.slf4j.Slf4j;

/**
 * The Class Application.
 *
 * <br>
 *
 * Class used to perform the bootstrapping an application using the framework
 *
 * The life cycle used when start are called following methods of the class:
 * <ul>
 * <li>initialize</li>
 * <li>mySupport</li>
 * <li>load</li>
 * </ul>
 */
@Slf4j
public abstract class Application {

	/** The Constant builders. */
	private static final List<Builder> builders = new ArrayList<>();

	/**
	 * Builder.
	 *
	 * @param builder
	 *            the builder
	 * @return the application
	 */
	public Application builder(Builder builder) {

		builders.add(builder);
		return this;
	}

	/**
	 * Builds the all.
	 */
	private void buildAll() {

		builders.forEach(b -> b.build());
	}

	/**
	 * Exit.
	 *
	 * @param status
	 *            the status
	 */
	protected void exit(int status) {

		log.info("Exit from bootstrap.");
		System.exit(status);
	}

	/**
	 * Initialize.
	 */
	protected void initialize() {
	}

	/**
	 * Load.
	 */
	protected void load() {
	}

	/**
	 * My support.
	 */
	protected void mySupport() {

		ContainerUtils.processInjection(this.getClass(), this);
	}

	/**
	 * Start.
	 *
	 * @throws InfraException
	 *             the infra exception
	 */
	protected void start() throws InfraException {

		long start = System.currentTimeMillis();

		log.info("Starting Application");
		initialize();

		mySupport();

		log.info("Loading Builders");
		buildAll();

		load();

		log.debug("Application started at {} ms", System.currentTimeMillis() - start);
	}
}