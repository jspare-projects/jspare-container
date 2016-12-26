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

import static org.jspare.core.container.Environment.my;
import static org.junit.Assert.assertTrue;

import org.jspare.core.AbstractApplicationTest;
import org.jspare.core.bootstrap.EnvironmentBuilder;
import org.jspare.core.dummy.application.qualified.Qualified;
import org.jspare.core.dummy.application.qualified.QualifiedImpl;
import org.jspare.core.dummy.application.qualified.QualifiedOne;
import org.jspare.core.dummy.application.qualified.QualifiedTwo;
import org.jspare.core.dummy.usage.Cmpt;
import org.jspare.core.exception.EnvironmentException;
import org.junit.Test;

public class InjectionQualifiedTest extends AbstractApplicationTest {

	@Test
	public void testQualifiedComponents() {

		Qualified multiple = my(Qualified.class);
		assertTrue(multiple instanceof QualifiedImpl);

		Qualified multipleOne = my(Qualified.class, "MultipleOne");
		assertTrue(multipleOne instanceof QualifiedOne);

		Qualified multipleTwo = my(Qualified.class, "MultipleTwo");
		assertTrue(multipleTwo instanceof QualifiedTwo);
	}

	/**
	 * Test invalid components instantiation.
	 */
	@Test(expected = EnvironmentException.class)
	public void testQualifierComponentsInstantiationWithoutRegistry() {

		my(Cmpt.class, "QualifierThatNotExist");
	}

	@Override
	protected EnvironmentBuilder toLoad() {

		return new EnvironmentBuilder()
				.registryComponent(QualifiedOne.class)
				.registryComponent("org.jspare.core.dummy.application.qualified.QualifiedTwo");
	}
}