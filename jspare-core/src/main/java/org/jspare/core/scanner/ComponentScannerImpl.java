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

import java.util.ArrayList;
import java.util.List;

import org.jspare.core.util.PerformAsync;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;

/**
 * The Class ComponentScannerImpl.
 *
 * @author pflima
 * @since 30/03/2016
 */
public class ComponentScannerImpl implements ComponentScanner {

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.jspare.core.scanner.ComponentScanner#scanAndExecute(java.lang.String,
	 * org.jspare.core.util.Perform)
	 */
	@Override
	public void scanAndExecute(String packageConvetion, PerformAsync<String> perform) {
		String packageForScan = packageConvetion;
		if (packageForScan.endsWith(".*")) {
			packageForScan = packageForScan.substring(0, packageForScan.length() - 2);
		}

		List<String> matchingClasses = new ArrayList<>();
		new FastClasspathScanner(packageForScan).scan().getNamesOfAllClasses().forEach(matchingClasses::add);

		matchingClasses.forEach(clazzName -> {

			perform.doIt(clazzName);
		});
	}

}
