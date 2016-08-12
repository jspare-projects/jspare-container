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

import org.jspare.core.exception.ProviderLoaderException;

/**
 * The Class ApplicationProviderImpl.
 *
 * @author pflima
 * @since 30/03/2016
 */
public class ApplicationProviderImpl implements ApplicationProvider {

	/** The factories. */
	private Map<Class<?>, Factory<?>> factories = new HashMap<>();

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.jspare.core.provider.ApplicationProvider#getFactory(java.lang.Class)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getFactory(Class<?> provider) {
		return (T) factories.get(provider);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.core.provider.ApplicationProvider#load(java.util.Map)
	 */
	@Override
	public void load(Map<String, Map<String, String>> providers) throws ProviderLoaderException {

		for (String key : providers.keySet()) {

			try {
				loadProvider(Class.forName(key), providers.get(key));
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {

				throw new ProviderLoaderException(e);
			}

		}

	}

	/**
	 * Load provider.
	 *
	 * @param clazz
	 *            the clazz
	 * @param resources
	 *            the resources
	 * @throws InstantiationException
	 *             the instantiation exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 */
	private void loadProvider(Class<?> clazz, Map<String, String> resources) throws InstantiationException, IllegalAccessException {

		Factory<?> factory = (Factory<?>) clazz.newInstance();
		factory.load(resources);

		factories.put(clazz, factory);

	}

}