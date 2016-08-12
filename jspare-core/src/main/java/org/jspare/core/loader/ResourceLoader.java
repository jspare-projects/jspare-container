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
package org.jspare.core.loader;

import java.io.IOException;
import java.io.InputStream;

import org.jspare.core.container.Component;

/**
 * The Interface ResourceLoader.
 *
 * @author pflima
 * @since 30/03/2016
 */
@Component
public interface ResourceLoader {

	/** The classpath prefix pattern. */
	String CLASSPATH_PREFIX_PATTERN = "classpath:";

	/**
	 * Read file to input stream.
	 *
	 * @param resource
	 *            the resource
	 * @return the input stream
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	InputStream readFileToInputStream(String resource) throws IOException;

	/**
	 * Read file to string.
	 *
	 * @param resource
	 *            the resource
	 * @return the string
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	String readFileToString(String resource) throws IOException;

	/**
	 * Read file to string if exist.
	 *
	 * @param resource
	 *            the resource
	 * @return the string
	 */
	String readFileToStringIfExist(String resource);
}