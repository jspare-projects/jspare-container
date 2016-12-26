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

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * The Class ComponentKey.
 *
 * Used to index components on {@link Environment }
 *
 * The annotation {@link EqualsAndHashCode } are responsible to generate a valid
 * and optimized hashcode to perform on iterate of Map.
 *
 */
@AllArgsConstructor
@EqualsAndHashCode
public class ComponentKey {

	/**
	 * Gets the clazz interface.
	 *
	 * @return the clazz interface
	 */
	@Getter
	private final Class<?> clazzInterface;

	/**
	 * Gets the qualifier.
	 *
	 * @return the qualifier
	 */
	@Getter
	private String qualifier;
}