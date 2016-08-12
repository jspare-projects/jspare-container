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

import java.util.Map;

import org.jspare.core.container.Component;
import org.jspare.core.exception.ProviderLoaderException;

/**
 * The Interface ApplicationProvider.
 *
 * @author pflima
 * @since 30/03/2016
 */
@Component
public interface ApplicationProvider {

	/**
	 * Gets the factory.
	 *
	 * @param <T>
	 *            the generic type
	 * @param provider
	 *            the provider
	 * @return the factory
	 */
	<T> T getFactory(Class<?> provider);

	/**
	 * Load.
	 *
	 * @param providers
	 *            the providers
	 * @throws ProviderLoaderException
	 *             the provider loader exception
	 */
	void load(Map<String, Map<String, String>> providers) throws ProviderLoaderException;
}
