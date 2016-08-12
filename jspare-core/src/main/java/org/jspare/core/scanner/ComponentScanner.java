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
package org.jspare.core.scanner;

import org.jspare.core.container.Component;
import org.jspare.core.util.Perform;

/**
 * The Interface ComponentScanner.
 *
 * @author pflima
 * @since 30/03/2016
 */
@Component
public interface ComponentScanner {

	/** The Constant ALL_SCAN_QUOTE. */
	String ALL_SCAN_QUOTE = ".*";

	/**
	 * Scan and execute.
	 *
	 * @param packageConvetion
	 *            the package convetion
	 * @param perform
	 *            the perform
	 */
	void scanAndExecute(String packageConvetion, Perform<Class<Void>> perform);
}
