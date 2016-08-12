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
import java.time.LocalDate;

import org.jspare.core.serializer.JsonConverter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * The Class LocalDateConverter.
 *
 * @author pflima
 * @since 30/03/2016
 */
public class LocalDateConverter implements JsonConverter {

	/**
	 * The Class LocalDateAdapter.
	 *
	 * @author pflima
	 * @since 30/03/2016
	 */
	class LocalDateAdapter extends TypeAdapter<LocalDate> {

		/*
		 * (non-Javadoc)
		 *
		 * @see
		 * com.google.gson.TypeAdapter#read(com.google.gson.stream.JsonReader)
		 */
		@Override
		public LocalDate read(JsonReader reader) throws IOException {

			if (reader.peek() == JsonToken.NULL) {
				reader.nextNull();
				return null;
			}
			String dateStr = reader.nextString();

			return "".equals(dateStr) ? null : LocalDate.parse(dateStr);
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see
		 * com.google.gson.TypeAdapter#write(com.google.gson.stream.JsonWriter,
		 * java.lang.Object)
		 */
		@Override
		public void write(JsonWriter writer, LocalDate localDate) throws IOException {

			if (localDate == null) {
				writer.nullValue();
				return;
			}
			writer.value(localDate.toString());
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.core.serializer.JsonConverter#getAdapter()
	 */
	@Override
	public Object getAdapter() {

		return new LocalDateAdapter();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.core.serializer.JsonConverter#getType()
	 */
	@Override
	public Type getType() {
		return LocalDate.class;
	}

}
