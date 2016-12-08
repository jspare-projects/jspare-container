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

/**
 * The abstract class Errors.
 */
public abstract class Errors {

	public static final ErrorType NO_CMPT_REGISTERED = ErrorType.create(1000,
			"%s don't have default implementation class. Provide default implementation or registry one.");

	public static final ErrorType FAILED_INSTANTIATION = ErrorType.create(1001, "Failed to instantiate class.");

	public static final ErrorType INVALID_INJECTOR = ErrorType.create(1002, "Invalid injector type parameter");

	public static final ErrorType INVALID_INJECTION = ErrorType.create(1003, "Invalid injector type parameter");

	public static final ErrorType NO_QUALIFIER_REGISTERED = ErrorType.create(1004,
			"No implementation registered for class %s with Qualifier [%s]");

	public static final ErrorType NO_COMPONENT_FOUNDED_CLASS = ErrorType.create(1005,
			"No interface with annotation @Component founded for class [%s]");

	public static final ErrorType NO_COMPONENT_FOUNDED_FIELD = ErrorType.create(1006,
			"No interface with annotation @Component founded for field [%s]");

	public static final ErrorType INVALID_RESOURCE_CLASS = ErrorType.create(1007,
			"No @Resource annotation found on [%s] class, is not a valid resource.");

	public static final ErrorType NON_MAPPED = ErrorType.create(1999, "Environment Exception called by another Throwable.");
}