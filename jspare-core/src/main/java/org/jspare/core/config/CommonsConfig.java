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

import org.apache.commons.configuration.Configuration;
import org.jspare.core.annotation.Component;

/**
 * The Interface CommonsConfig.
 *
 * <br>
 *
 * It is used for manipulating the configuration of an application
 *
 * This interface extends {@link Configuration } from Apache Commons, And its
 * responsibility is to facilitate the manipulation of the settings.
 *
 * @author pflima
 * @since 30/03/2016
 */
@Component
public interface CommonsConfig extends Configuration {

	/**
	 * Gets the property key from configuration.
	 *
	 * @param key
	 *            the key
	 * @return the string
	 */
	String get(String key);

	/**
	 * Gets the property key from configuration, if anyone value is setted the
	 * defaultValue is returned.
	 *
	 * @param key
	 *            the name
	 * @param defaultValue
	 *            the default value
	 * @return the string
	 */
	String get(String key, String defaultValue);

	/**
	 * Load new file to component instance.
	 *
	 * @param fileToLoad
	 *            the file to load
	 */
	void loadFile(String fileToLoad);

	/**
	 * Put property into configuration.
	 *
	 * @param key
	 *            the name
	 * @param value
	 *            the value
	 */
	void put(String key, Object value);

	/**
	 * Put property into configuration overwriting original if the variable is
	 * setted to do this.
	 *
	 * @param key
	 *            the name
	 * @param value
	 *            the value
	 * @param overwrite
	 *            the overwrite
	 */
	void put(String key, Object value, boolean overwrite);

	/**
	 * Put all paramters overwritingoriginal if the variable is setted to do
	 * this.
	 *
	 * @param parameters
	 *            the parameters
	 * @param overwrite
	 *            the overwrite
	 */
	void putAll(Map<String, Object> parameters, boolean overwrite);

	/**
	 * Store and overwrite the configuration file.
	 */
	void store();

	/**
	 * Return one {@link Map } with the balues.
	 *
	 * @return the map
	 */
	Map<String, Object> values();
}