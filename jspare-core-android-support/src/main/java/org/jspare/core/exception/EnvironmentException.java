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
package org.jspare.core.exception;

import lombok.Getter;

/**
 * The Class EnvironmentException.
 *
 * @author pflima
 * @since 05/10/2015
 */
public class EnvironmentException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant FORMATTED_MESSAGE. */
	private static final String FORMATTED_MESSAGE = "EX%s - %s";

	/** The error. */
	@Getter
	private final ErrorType error;

	/**
	 * Instantiates a new environment exception.
	 *
	 * @param error the error
	 */
	public EnvironmentException(ErrorType error) {

		super(String.format(FORMATTED_MESSAGE, error.code(), error.message()), error.throwable());
		this.error = error;
	}
}