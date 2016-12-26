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
package org.jspare.core.container;

import org.jspare.core.bootstrap.Application;

/**
 *
 * MySupport Class
 *
 * <h2>Important:</h2>
 * <ul>
 * <li>For {@link Application} the ContainerUtils .processInjection never is
 * called. This class use for supply, super start method.</li>
 * </ul>
 *
 * @author pflima
 *
 */
public abstract class MySupport {

	/**
	 * Instantiates a new my support.
	 */
	public MySupport() {

		ContainerUtils.processInjection(this);
	}
}