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

import java.lang.reflect.Type;

import org.jspare.core.container.Component;
import org.jspare.core.exception.SerializationException;

/**
 * The Interface Json.
 */
@Component
public interface Json {

	/**
	 * From JSON.
	 *
	 * @param <T>
	 *            the generic type
	 * @param jsonObject
	 *            the json object
	 * @param clazz
	 *            the clazz
	 * @return the t
	 * @throws SerializationException
	 *             the serialization exception
	 */
	<T> T fromJSON(Object jsonObject, Class<T> clazz) throws SerializationException;

	/**
	 * From JSON.
	 *
	 * @param <T>
	 *            the generic type
	 * @param jsonObject
	 *            the json object
	 * @param type
	 *            the type
	 * @return the t
	 * @throws SerializationException
	 *             the serialization exception
	 */
	<T> T fromJSON(Object jsonObject, Type type) throws SerializationException;

	/**
	 * From JSON.
	 *
	 * @param <T>
	 *            the generic type
	 * @param json
	 *            the json
	 * @param clazz
	 *            the clazz
	 * @return the t
	 * @throws SerializationException
	 *             the serialization exception
	 */
	<T> T fromJSON(String json, Class<T> clazz) throws SerializationException;

	/**
	 * From JSON.
	 *
	 * @param <T>
	 *            the generic type
	 * @param json
	 *            the json
	 * @param type
	 *            the type
	 * @return the t
	 * @throws SerializationException
	 *             the serialization exception
	 */
	<T> T fromJSON(String json, Type type) throws SerializationException;

	/**
	 * Checks if is valid json.
	 *
	 * @param json
	 *            the json
	 * @return true, if is valid json
	 */
	boolean isValidJson(String json);

	/**
	 * Registry json converter.
	 *
	 * @param converter
	 *            the converter
	 * @return the json
	 * @throws SerializationException
	 *             the serialization exception
	 */
	Json registryJsonConverter(Object converter) throws SerializationException;

	/**
	 * To JSON.
	 *
	 * @param instance
	 *            the instance
	 * @return the string
	 * @throws SerializationException
	 *             the serialization exception
	 */
	String toJSON(Object instance) throws SerializationException;
}
