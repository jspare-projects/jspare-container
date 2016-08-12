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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;

import org.jspare.core.exception.SerializationException;

import lombok.Cleanup;

public class Base64Impl implements org.jspare.core.serializer.Base64 {

	@Override
	@SuppressWarnings("unchecked")
	public <T> T fromBase64(String data) throws SerializationException {

		try {

			@Cleanup
			ByteArrayInputStream bin = new ByteArrayInputStream(Base64.getDecoder().decode(data));
			@Cleanup
			ObjectInputStream in = new ObjectInputStream(bin);
			return (T) in.readObject();

		} catch (IOException | ClassNotFoundException e) {

			throw new SerializationException(e);
		}
	}

	@Override
	public String toBase64(byte[] bytes) throws SerializationException {

		return Base64.getEncoder().encodeToString(bytes);
	}

	@Override
	public String toBase64(Object instance) throws SerializationException {

		try {
			@Cleanup
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			@Cleanup
			ObjectOutputStream out = new ObjectOutputStream(bout);
			out.writeObject(instance);
			return Base64.getEncoder().encodeToString(bout.toByteArray());

		} catch (IOException e) {

			throw new SerializationException(e);
		}
	}

}
