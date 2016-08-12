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
import java.lang.reflect.Type;
import java.time.LocalTime;

import org.jspare.core.serializer.JsonConverter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * The Class LocalTimeConverter.
 *
 * @author pflima
 * @since 30/03/2016
 */
public class LocalTimeConverter implements JsonConverter {

	/**
	 * The Class LocalTimeAdapter.
	 *
	 * @author pflima
	 * @since 30/03/2016
	 */
	class LocalTimeAdapter extends TypeAdapter<LocalTime> {

		/*
		 * (non-Javadoc)
		 *
		 * @see
		 * com.google.gson.TypeAdapter#read(com.google.gson.stream.JsonReader)
		 */
		@Override
		public LocalTime read(JsonReader reader) throws IOException {

			if (reader.peek() == JsonToken.NULL) {
				reader.nextNull();
				return null;
			}
			String dateStr = reader.nextString();
			return "".equals(dateStr) ? null : LocalTime.parse(dateStr);
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see
		 * com.google.gson.TypeAdapter#write(com.google.gson.stream.JsonWriter,
		 * java.lang.Object)
		 */
		@Override
		public void write(JsonWriter writer, LocalTime LocalTime) throws IOException {

			if (LocalTime == null) {
				writer.nullValue();
				return;
			}
			writer.value(LocalTime.toString());
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.core.serializer.JsonConverter#getAdapter()
	 */
	@Override
	public Object getAdapter() {

		return new LocalTimeAdapter();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.core.serializer.JsonConverter#getType()
	 */
	@Override
	public Type getType() {

		return LocalTime.class;
	}

}
