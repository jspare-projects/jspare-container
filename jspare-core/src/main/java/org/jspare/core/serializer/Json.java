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

@Component
public interface Json {

	<T> T fromJSON(Object jsonObject, Class<T> clazz) throws SerializationException;

	<T> T fromJSON(Object jsonObject, Type type) throws SerializationException;

	<T> T fromJSON(String json, Class<T> clazz) throws SerializationException;

	<T> T fromJSON(String json, Type type) throws SerializationException;

	boolean isValidJson(String json);

	Json registryJsonConverter(Object converter) throws SerializationException;

	String toJSON(Object instance) throws SerializationException;
}
