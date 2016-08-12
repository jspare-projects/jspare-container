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

import java.io.Serializable;

import org.junit.Assert;
import org.junit.Test;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The Class SerializerTest.
 *
 * @author pflima
 * @since 05/10/2015
 */
public class Base64SerializerTest {

	/**
	 * Serialization test.
	 *
	 * @throws Throwable
	 *             the throwable
	 */
	@Test
	public void serializationTest() throws Throwable {

		Base64 base64 = my(Base64.class);

		ToSerialize bean1 = new ToSerialize("A", "B");
		String data = base64.toBase64(bean1);
		ToSerialize bean2 = base64.fromBase64(data);

		Assert.assertEquals(bean1, bean2);
	}
}

@Data
@AllArgsConstructor
class ToSerialize implements Serializable {

	private static final long serialVersionUID = 1L;

	private transient String a01;
	private final String a02;
}