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

import org.jspare.core.exception.InfraRuntimeException;
import org.jspare.core.exception.SerializationException;
import org.jspare.core.serializer.converter.LocalDateConverter;
import org.jspare.core.serializer.converter.LocalDateTimeConverter;
import org.jspare.core.serializer.converter.LocalTimeConverter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapterFactory;

/**
 * The Class JsonImpl.
 */
public class JsonImpl implements Json {

	/** The builder. */
	private static GsonBuilder builder = new GsonBuilder();

	/**
	 * Instantiates a new json impl.
	 */
	public JsonImpl() {

		try {

			registryJsonConverter(new LocalDateConverter());
			registryJsonConverter(new LocalTimeConverter());
			registryJsonConverter(new LocalDateTimeConverter());
		} catch (SerializationException e) {

			throw new InfraRuntimeException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jspare.core.serializer.Json#fromJSON(java.lang.Object,
	 * java.lang.Class)
	 */
	@Override
	public <T> T fromJSON(Object jsonObject, Class<T> clazz) throws SerializationException {

		return getGson().fromJson((JsonElement) jsonObject, clazz);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jspare.core.serializer.Json#fromJSON(java.lang.Object,
	 * java.lang.reflect.Type)
	 */
	@Override
	public <T> T fromJSON(Object jsonObject, Type type) throws SerializationException {

		return getGson().fromJson((JsonElement) jsonObject, type);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jspare.core.serializer.Json#fromJSON(java.lang.String,
	 * java.lang.Class)
	 */
	@Override
	public <T> T fromJSON(String json, Class<T> clazz) throws SerializationException {

		return getGson().fromJson(json, clazz);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jspare.core.serializer.Json#fromJSON(java.lang.String,
	 * java.lang.reflect.Type)
	 */
	@Override
	public <T> T fromJSON(String json, Type type) throws SerializationException {

		return getGson().fromJson(json, type);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jspare.core.serializer.Json#isValidJson(java.lang.String)
	 */
	@Override
	public boolean isValidJson(String json) {

		try {

			getGson().fromJson(json, Object.class);
			return true;
		} catch (JsonSyntaxException e) {

			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jspare.core.serializer.Json#registryJsonConverter(java.lang.Object)
	 */
	@Override
	public Json registryJsonConverter(Object converter) throws SerializationException {

		if (converter instanceof JsonConverter) {
			JsonConverter jsonConverter = (JsonConverter) converter;
			builder.registerTypeAdapter(jsonConverter.getType(), jsonConverter.getAdapter());
			return this;
		}
		if (converter instanceof TypeAdapterFactory) {

			builder.registerTypeAdapterFactory((TypeAdapterFactory) converter);
			return this;
		}

		throw new SerializationException(
				String.format("Cannot register converter [%s] invalid instance type.", converter.getClass().getName()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jspare.core.serializer.Json#toJSON(java.lang.Object)
	 */
	@Override
	public String toJSON(Object instance) throws SerializationException {

		return getGson().toJson(instance);
	}

	/**
	 * Gets the gson.
	 *
	 * @return the gson
	 */
	protected Gson getGson() {

		return builder.create();
	}
}