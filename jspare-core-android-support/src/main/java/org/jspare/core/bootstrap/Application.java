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

import static org.jspare.core.container.Environment.registryResource;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.jspare.core.container.ContainerUtils;
import org.jspare.core.container.Context;

import lombok.Getter;
import lombok.SneakyThrows;
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
 * <li>setup</li>
 * <li>mySupport</li>
 * <li>build all builders</li>
 * <li>start</li>
 * </ul>
 */
@Slf4j
public abstract class Application {

	/** The Constant builders. */
	private static final List<Builder> builders = new ArrayList<>();

	/**
	 * The Create method is responsible for instantiate new Application by
	 * Reflection.
	 *
	 * @param bootstrapClazz
	 *            the bootstrap clazz
	 * @return the application
	 */
	@SneakyThrows
	public static Application create(Class<? extends Application> bootstrapClazz) {

		Application instance = bootstrapClazz.newInstance();
		return instance;
	}

	/**
	 * The Run method is responsible for invoking the application, the
	 * application life cycle depends on the call of this method.
	 *
	 * @param bootstrapClazz the bootstrap clazz
	 */
	@SneakyThrows
	public static void run(Class<? extends Application> bootstrapClazz) {

		Application instance = create(bootstrapClazz);
		instance.run();
	}

	@Getter
	private Context context;

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
	 * The Run method is responsible for invoking the application, the
	 * application life cycle depends on the call of this method.
	 */
	public void run() {

		long start = System.currentTimeMillis();

		log.info("Starting Application");

		context = new Context();
		registryResource(context);

		setup();

		mySupport();

		log.info("Loading Builders");
		buildAll();

		long end = System.currentTimeMillis();

		log.info("Application loaded at {} ms", end - start);

		start();
	}

	/**
	 * Starts the application after the entire application lifecycle is loaded,
	 * at this point the application is ready to load all the components and
	 * resources.
	 */
	public abstract void start();

	/**
	 * The My support. After initializing the components through the setup
	 * method, this method is invoked to resolve any dependency injection in the
	 * application class.
	 */
	protected void mySupport() {

		ContainerUtils.processInjection(this);
	}

	/**
	 * The setup method, configures the application, it is the first method
	 * invoked when starting the application initialization stream, this method
	 * must be overwritten when there is a need to dictate new behaviors for the
	 * components of an application.
	 */
	protected void setup() {
	}

	/**
	 * Builds the all.
	 */
	private void buildAll() {

		builders.forEach(new Consumer<Builder>() {
			@Override
			public void accept(Builder b) {
				b.build();
			}
		});
	}
}