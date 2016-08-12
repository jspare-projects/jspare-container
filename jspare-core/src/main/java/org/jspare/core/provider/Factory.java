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
package org.jspare.core.provider;

import java.util.HashMap;
import java.util.Map;

/**
 * The Class Factory.
 *
 * @author pflima
 * @param <T>
 *            the generic type
 * @since 30/03/2016
 */
public abstract class Factory<T> {

	/** The resources. */
	protected Map<String, T> resources = new HashMap<>();

	/**
	 * Gets the.
	 *
	 * @param key
	 *            the key
	 * @return the t
	 */
	public T get(String key) {
		return resources.get(key);
	}

	/**
	 * Load.
	 *
	 * @param mapping
	 *            the mapping
	 */
	public abstract void load(Map<String, String> mapping);
}
