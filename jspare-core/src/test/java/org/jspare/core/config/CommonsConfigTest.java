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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collections;

import org.junit.Assert;
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

		CommonsConfig config = CONFIG;
		assertEnv(config);
	}
	
	@Test
	public void simplePutTest() {

		CONFIG.put("test", "true");
		assertTrue(Boolean.valueOf(CONFIG.get("test")));
	}
	
	@Test
	public void simplePutAllTest() {

		CONFIG.putAll(Collections.singletonMap("test", "true"), true);
		assertTrue(Boolean.valueOf(CONFIG.get("test")));
	}

	@Test
	public void overrideTest() {

		CONFIG.put("env", "PRD", true);
		assertEquals("PRD", CONFIG.get("env"));
	}
	
	@Test
	public void failLoadFileTest(){

		CONFIG.clear();
		CONFIG.loadFile("invalidfile");
		Assert.assertTrue(CONFIG.values().size() == 0);
	}

	private void assertEnv(CommonsConfig config) {
		String env = config.get("env");
		assertEquals("DEV", env);
	}
}
