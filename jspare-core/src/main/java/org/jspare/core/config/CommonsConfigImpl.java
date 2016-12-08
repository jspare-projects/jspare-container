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

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class CommonsConfigImpl extends PropertiesConfiguration implements CommonsConfig {

	/** The file to load with default value 'conf/config.ini'. */
	@Setter
	private String fileToLoad = "conf/config.ini";

	/** The configuration. */
	private Configuration configuration;

	/**
	 * Instantiates a new commons config impl.
	 */
	public CommonsConfigImpl() {

		loadFile(fileToLoad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.core.config.CommonsConfig#get(java.lang.String)
	 */
	@Override
	public String get(String name) {

		return configuration.getString(name);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.core.config.CommonsConfig#get(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	public String get(String name, String defaultValue) {

		return configuration.getString(name, defaultValue);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.core.config.CommonsConfig#loadFile(java.lang.String)
	 */
	@Override
	public void loadFile(String fileToLoad) {

		this.fileToLoad = fileToLoad;

		try {

			configuration = new PropertiesConfiguration(this.fileToLoad);
		} catch (ConfigurationException e) {

			configuration = new PropertiesConfiguration();
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.core.config.CommonsConfig#put(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void put(String key, Object value) {

		put(key, value, false);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.core.config.CommonsConfig#put(java.lang.String,
	 * java.lang.String, boolean)
	 */
	@Override
	public void put(String key, Object value, boolean overwrite) {

		if (!overwrite && configuration.containsKey(key)) {

			return;
		}
		configuration.setProperty(key, value);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.core.config.CommonsConfig#put(java.lang.String,
	 * java.lang.String, boolean)
	 */
	@Override
	public void putAll(Map<String, Object> parameters, boolean overwrite) {

		parameters.forEach((k, v) -> put(k, v, overwrite));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.core.config.CommonsConfig#store()
	 */
	@Override
	public void store() {

		try {

			PropertiesConfiguration targetConfiguration = new PropertiesConfiguration(fileToLoad);
			configuration.getKeys().forEachRemaining(k -> targetConfiguration.setProperty(k, configuration.getProperties(k)));
			targetConfiguration.save();

		} catch (ConfigurationException e) {

			log.error("Error when trying to save a configuration [{}] - Message [{}]", fileToLoad, e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jspare.core.config.CommonsConfig#values()
	 */
	@Override
	public Map<String, Object> values() {
		Map<String, Object> values = new HashMap<>();
		configuration.getKeys().forEachRemaining(k -> values.put(k, configuration.getString(k)));
		return values;
	}
}