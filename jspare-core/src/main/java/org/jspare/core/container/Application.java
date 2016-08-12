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

@Slf4j
public abstract class Application {

	private static final List<Builder> builders = new ArrayList<>();

	public Application builder(Builder builder) {

		builders.add(builder);
		return this;
	}

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

	protected void initialize() {
	}

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

		log.info("Starting Application");
		initialize();

		mySupport();

		log.info("Loading Builders");
		buildAll();

		load();
	}
}