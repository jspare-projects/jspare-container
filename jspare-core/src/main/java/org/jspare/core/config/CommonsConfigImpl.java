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

import static org.jspare.core.commons.Definitions.COMMON_FILE_TO_LOAD;

import java.util.Map;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonsConfigImpl implements CommonsConfig {

	/** The file to load. */
	private String fileToLoad;

	/** The configuration. */
	private Configuration configuration;

	/**
	 * Instantiates a new commons config impl.
	 */
	public CommonsConfigImpl() {

		loadFile(COMMON_FILE_TO_LOAD);
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
	@SuppressWarnings("unchecked")
	public <T> T get(String name, Object defaultValue) {
		String result = configuration.getString(name);
		return (T) (StringUtils.isEmpty(result) ? defaultValue.toString() : result);
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
	public void put(String name, String value) {

		put(name, value, false);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.core.config.CommonsConfig#put(java.lang.String,
	 * java.lang.String, boolean)
	 */
	@Override
	public void put(String name, String value, boolean overwrite) {

		if (!overwrite && configuration.containsKey(name)) {

			return;
		}
		configuration.setProperty(name, value);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.core.config.CommonsConfig#put(java.lang.String,
	 * java.lang.String, boolean)
	 */
	@Override
	public void putAll(Map<String, String> parameters, boolean overwrite) {

		parameters.forEach((k, v) -> put(k, v, overwrite));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.core.config.CommonsConfig#remove(java.lang.String)
	 */
	@Override
	public void remove(String name) {

		configuration.clearProperty(name);
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
}