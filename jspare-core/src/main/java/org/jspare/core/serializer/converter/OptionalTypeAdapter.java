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
package org.jspare.core.serializer.converter;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

import org.jspare.core.serializer.converter.OptionalTypeAdapter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * The Class OptionalTypeAdapter.
 *
 * @author pflima
 * @param <E>
 *            the element type
 * @since 30/03/2016
 */
public class OptionalTypeAdapter<E> extends TypeAdapter<Optional<E>> {

	/** The Constant FACTORY. */
	public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
		@Override
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
			Class<T> rawType = (Class<T>) type.getRawType();
			if (rawType != Optional.class) {
				return null;
			}
			final ParameterizedType parameterizedType = (ParameterizedType) type.getType();
			final Type actualType = parameterizedType.getActualTypeArguments()[0];
			final TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(actualType));
			return new OptionalTypeAdapter(adapter);
		}
	};

	/** The adapter. */
	private final TypeAdapter<E> adapter;

	/**
	 * Instantiates a new optional type adapter.
	 *
	 * @param adapter
	 *            the adapter
	 */
	public OptionalTypeAdapter(TypeAdapter<E> adapter) {

		this.adapter = adapter;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.google.gson.TypeAdapter#read(com.google.gson.stream.JsonReader)
	 */
	@Override
	public Optional<E> read(JsonReader in) throws IOException {
		final JsonToken peek = in.peek();
		if (peek != JsonToken.NULL) {
			return Optional.ofNullable(adapter.read(in));
		}
		return Optional.empty();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.google.gson.TypeAdapter#write(com.google.gson.stream.JsonWriter,
	 * java.lang.Object)
	 */
	@Override
	public void write(JsonWriter out, Optional<E> value) throws IOException {
		if (value.isPresent()) {
			adapter.write(out, value.get());
		} else {
			out.nullValue();
		}
	}

}
