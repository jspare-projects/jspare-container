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

import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;

/**
 * The Class SerializerImpl.
 *
 * @author pflima
 * @since 05/10/2015
 */
public class SerializerImpl implements Serializer {

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.core.serializer.Serializer#convert(java.lang.Class,
	 * java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> T convert(Class<T> targetType, String value) {
		PropertyEditor editor = PropertyEditorManager.findEditor(targetType);
		editor.setAsText(value);
		return (T) editor.getValue();
	}
}