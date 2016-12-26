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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@AllArgsConstructor
@RequiredArgsConstructor
@Accessors(fluent = true)
public class ErrorType {

	public static ErrorType create(int code, String message) {

		return new ErrorType(code, message);
	}

	@Getter
	private final int code;

	private final String message;

	@Getter
	@Setter
	private Throwable throwable;

	private Object[] arguments;

	public ErrorType arguments(Object... arguments) {
		this.arguments = arguments;
		return this;
	}

	public String message() {

		return String.format(message, arguments);
	}
}