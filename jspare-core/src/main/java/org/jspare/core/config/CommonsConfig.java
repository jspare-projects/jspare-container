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

import org.jspare.core.container.Component;
import org.jspare.core.container.Scope;

/**
 * The Interface CommonsConfig.
 *
 * @author pflima
 * @since 30/03/2016
 */
@Component(scope = Scope.APPLICATION)
public interface CommonsConfig {

	/**
	 * Gets the.
	 *
	 * @param name
	 *            the name
	 * @return the string
	 */
	String get(String name);

	/**
	 *
	 * The default value will be called as toString method.
	 *
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	String get(String name, Object defaultValue);

	/**
	 * Load file.
	 *
	 * @param fileToLoad
	 *            the file to load
	 */
	void loadFile(String fileToLoad);

	/**
	 * Put.
	 *
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 */
	void put(String name, String value);

	/**
	 * Put.
	 *
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 * @param overwrite
	 *            the overwrite
	 */
	void put(String name, String value, boolean overwrite);

	/**
	 * Removes the.
	 *
	 * @param name
	 *            the name
	 */
	void remove(String name);

	/**
	 * Store.
	 */
	void store();
}
