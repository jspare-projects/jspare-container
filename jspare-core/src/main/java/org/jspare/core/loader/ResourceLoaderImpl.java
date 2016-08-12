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

import static org.jspare.core.commons.Definitions.DEFAULT_CHARSET;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResourceLoaderImpl implements ResourceLoader {

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.jspare.core.loader.ResourceLoader#readFileToInputStream(java.lang.
	 * String)
	 */
	@Override
	public InputStream readFileToInputStream(String resource) throws IOException {

		InputStream content = null;

		String path = resolveClasspath(resource);
		File file = new File(path);
		if (file.exists()) {

			content = new FileInputStream(file);
		}
		if (content == null) {

			content = getClass().getClassLoader().getResourceAsStream(resource);
		}

		return content;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.jspare.core.loader.ResourceLoader#readFileToString(java.lang.String)
	 */
	@Override
	public String readFileToString(String resource) throws IOException {

		String content = null;

		String path = resolveClasspath(resource);

		File file = new File(path);
		if (file.exists()) {

			content = FileUtils.readFileToString(file, DEFAULT_CHARSET);
		}

		if (StringUtils.isEmpty(content)) {
			@Cleanup
			InputStream in = getClass().getResourceAsStream(String.format("%s%s", File.separator, path));
			if (in != null) {
				content = IOUtils.toString(in, DEFAULT_CHARSET);
			}
		}

		if (StringUtils.isEmpty(content)) {

			throw new FileNotFoundException();
		}

		return content;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.jspare.core.loader.ResourceLoader#readFileToStringIfExist(java.lang.
	 * String)
	 */
	@Override
	public String readFileToStringIfExist(String resource) {
		String result = null;
		try {
			result = readFileToString(resource);
		} catch (IOException e) {
			log.debug("None resource founded for [{}]", resource);
			result = StringUtils.EMPTY;
		}
		return result;
	}

	/**
	 * Resolve classpath.
	 *
	 * @param resource
	 *            the resource
	 * @return the string
	 */
	private String resolveClasspath(String resource) {
		boolean useClasshpath = resource.startsWith(CLASSPATH_PREFIX_PATTERN);
		if (!useClasshpath) {
			return resource;
		}

		String result = resource.replace(CLASSPATH_PREFIX_PATTERN,
				String.format("%s%s", System.getProperty("java.class.path"), File.separator));
		return result;
	}
}