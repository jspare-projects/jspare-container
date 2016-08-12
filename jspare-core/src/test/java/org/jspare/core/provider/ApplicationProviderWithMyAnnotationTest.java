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

import static org.jspare.core.container.Environment.my;
import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.Map;
import java.util.Properties;

import org.jspare.core.container.Inject;
import org.jspare.core.container.MySupport;
import org.jspare.core.exception.InfraException;
import org.jspare.core.provider.factory.PropertiesFactory;
import org.junit.Test;

/**
 * The Class ApplicationProviderWithMyAnnotationTest.
 *
 * @author pflima
 * @since 30/03/2016
 */
public class ApplicationProviderWithMyAnnotationTest extends MySupport {

	/** The application provider. */
	@Inject
	private ApplicationProvider applicationProvider;

	/**
	 * Test properties provider.
	 *
	 * @throws InfraException
	 *             the infra exception
	 */
	@Test
	public void testPropertiesProvider() throws InfraException {

		Map<String, Map<String, String>> map = Collections.singletonMap(PropertiesFactory.class.getCanonicalName(),
				Collections.singletonMap("config", "factory/properties/config.properties"));

		my(ApplicationProvider.class).load(map);

		PropertiesFactory propertiesFactory = applicationProvider.getFactory(PropertiesFactory.class);
		Properties properties = propertiesFactory.get("config");
		assertEquals("teste", properties.getProperty("parameter"));
	}
}
