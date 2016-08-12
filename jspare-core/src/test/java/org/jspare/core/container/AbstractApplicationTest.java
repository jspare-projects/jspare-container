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

import org.jspare.core.exception.InfraException;
import org.junit.Before;

public abstract class AbstractApplicationTest {

	class BootstrapUnitTest extends Application {

		@Override
		protected void initialize() {

			builder(toLoad());
		}

		@Override
		protected void load() {

		}
	}

	@Before
	public void setup() throws InfraException {

		// Simulate static main invocation
		new BootstrapUnitTest().start();
	}

	protected abstract EnvironmentBuilder toLoad();
}