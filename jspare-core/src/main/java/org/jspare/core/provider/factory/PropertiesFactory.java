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
package org.jspare.core.provider.factory;

import static org.jspare.core.container.Environment.my;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.jspare.core.loader.ResourceLoader;
import org.jspare.core.provider.Factory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PropertiesFactory extends Factory<Properties> {

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.core.provider.Factory#load(java.util.Map)
	 */
	@Override
	public void load(Map<String, String> mapping) {

		for (String key : mapping.keySet()) {
			String value = mapping.get(key);
			Properties properties = new Properties();
			try {
				properties.load(my(ResourceLoader.class).readFileToInputStream(value));
				resources.put(key, properties);
			} catch (IOException e) {
				log.error(e.getMessage(), e);
				continue;
			}

		}
	}
}
