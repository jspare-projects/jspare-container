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
package org.jspare.core.container;

import java.util.HashMap;

import org.jspare.core.annotation.Resource;

/**
 * The Class Context.
 *
 * Used for store context of application. Your implementation extends the
 * {@link HashMap }.
 */
@Resource
public class Context extends HashMap<Object, Object> {

	/**
	 *
	 */
	private static final long serialVersionUID = 2876291977103253345L;


	/**
	 * Gets the generic type expected.
	 *
	 * @param <T> the generic type
	 * @param key the key
	 * @return the as
	 */
	@SuppressWarnings("unchecked")
	public <T> T getAs(Object key) {

		return (T) get(key);
	}
}