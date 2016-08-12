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
package org.jspare.core.config;

import static org.jspare.core.container.Environment.CONFIG;
import static org.jspare.core.container.Environment.my;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CommonsConfigTest {

	@Test
	public void defaultValueTest() {

		String value = CONFIG.get("version", "1.0.0");
		assertEquals("1.0.0", value);
	}

	@Test
	public void loadAndGetConfiFromEnvironmentgTest() {

		assertEnv(CONFIG);
	}

	@Test
	public void loadAndGetConfigFromComponentTest() {

		CommonsConfig config = my(CommonsConfig.class);
		assertEnv(config);
	}

	@Test
	public void overrideTest() {

		CONFIG.put("env", "PRD", true);
		assertEquals("PRD", CONFIG.get("env"));
	}

	private void assertEnv(CommonsConfig config) {
		String env = config.get("env");
		assertEquals("DEV", env);
	}
}
