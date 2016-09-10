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
package org.jspare.core.config;

import java.util.Map;

import org.jspare.core.container.Component;
import org.jspare.core.container.Scope;

/**
 * The Interface CommonsConfig.
 *
 * <br>
 *
 * It is used for manipulating configuration applications
 *
 * @author pflima
 * @since 30/03/2016
 */
@Component(scope = Scope.APPLICATION)
public interface CommonsConfig {

	/**
	 * Gets the.
	 *
	 * @param name the name
	 * @return the string
	 */
	String get(String name);

	/**
	 * Gets the.
	 *
	 * @param <T> the generic type
	 * @param name the name
	 * @param defaultValue the default value
	 * @return the t
	 */
	<T> T get(String name, Object defaultValue);

	/**
	 * Load file.
	 *
	 * @param fileToLoad the file to load
	 */
	void loadFile(String fileToLoad);

	/**
	 * Put.
	 *
	 * @param name the name
	 * @param value the value
	 */
	void put(String name, String value);

	/**
	 * Put.
	 *
	 * @param name the name
	 * @param value the value
	 * @param overwrite the overwrite
	 */
	void put(String name, String value, boolean overwrite);

	/**
	 * Put all.
	 *
	 * @param parameters the parameters
	 * @param overwrite the overwrite
	 */
	void putAll(Map<String, String> parameters, boolean overwrite);

	/**
	 * Removes the.
	 *
	 * @param name the name
	 */
	void remove(String name);

	/**
	 * Store.
	 */
	void store();
}
