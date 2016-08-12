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
package org.jspare.core.serializer;

import static org.jspare.core.container.Environment.my;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * The Class SerializerTest.
 *
 * @author pflima
 * @since 05/10/2015
 */
public class JsonSerializerTest {

	/**
	 * Checks if is valid json test.
	 */
	@Test
	public void isValidJsonTest() {

		Json json = my(Json.class);

		String validJson = "{ \"name\" : \"jspare\"}", invalidJson = "name = jspare";

		assertTrue(json.isValidJson(validJson));
		assertTrue(!json.isValidJson(invalidJson));
	}
}