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

import org.junit.Assert;
import org.junit.Test;

/**
 * The Class SerializerTest.
 *
 * @author pflima
 * @since 05/10/2015
 */
public class ConvertSerializerTest {

	/**
	 * Convert test.
	 *
	 * @throws Throwable
	 *             the throwable
	 */
	@Test
	public void convertTest() throws Throwable {

		Serializer s = my(Serializer.class);

		long result01 = s.convert(Long.class, "12345");
		Assert.assertEquals(result01, 12345);

		double result02 = s.convert(Double.class, "123.45");
		Assert.assertEquals(result02, 123.45, 0.0001);

		boolean result03 = s.convert(Boolean.class, "true");
		Assert.assertTrue(result03);

		boolean result04 = s.convert(Boolean.class, "false");
		Assert.assertFalse(result04);
	}
}