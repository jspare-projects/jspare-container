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

import static org.jspare.core.container.Environment.factory;
import static org.jspare.core.container.Environment.my;
import static org.jspare.core.container.Environment.registryComponent;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.jspare.core.dummy.annotation.ClassInjected;
import org.jspare.core.dummy.notcomponent.NotCmpt;
import org.jspare.core.dummy.notcomponent.NotCmptImpl;
import org.jspare.core.dummy.qualifier.CmptQualifierOneImpl;
import org.jspare.core.dummy.qualifier.CmptQualifierTwoImpl;
import org.jspare.core.dummy.usage.Cmpt;
import org.jspare.core.dummy.usage.CmptImpl;
import org.jspare.core.dummy.usage.CmptOtherImpl;
import org.jspare.core.exception.EnvironmentException;
import org.junit.Test;

/**
 * The Class EnvironmentTest.
 *
 * @author pflima
 * @since 05/10/2015
 */
public class InjectionTest {

	/**
	 * Test container components instantiation.
	 */
	@Test
	public void testContainerComponentsInstantiation() {

		registryComponent(CmptImpl.class);
		
		Cmpt c1 = my(Cmpt.class);
		assertNotNull(c1);
		assertTrue(c1 instanceof CmptImpl);

		Cmpt c2 = my(Cmpt.class);
		assertSame(c1, c2);

		registryComponent(new CmptImpl());
		registryComponent(new CmptOtherImpl());

		Cmpt c3 = my(Cmpt.class);
		assertNotSame(c1, c3);
		assertTrue(c3 instanceof CmptOtherImpl);
	}

	/**
	 * Test invalid components instantiation.
	 */
	@Test
	public void testInvalidComponentsInstantiation() {

		try {
			registryComponent(new NotCmptImpl());
			fail();

		} catch (EnvironmentException e) {
			/* ignore */}

		try {
			registryComponent(new NotCmptImpl());
			fail();

		} catch (EnvironmentException e) {
			/* ignore */}

		try {
			my(NotCmpt.class);
			fail();

		} catch (EnvironmentException e) {
			/* ignore */}
	}

	/**
	 * Test my annotation.
	 */
	@Test
	public void testMyAnnotation() {

		ClassInjected classInjected = new ClassInjected();
		assertNotNull(classInjected.getCmpt());
	}

	/**
	 * Test invalid components instantiation.
	 */
	@Test
	public void testQualifierComponentsInstantiation() {

		registryComponent(new CmptQualifierOneImpl());
		registryComponent(new CmptQualifierTwoImpl());

		Cmpt c1 = my(Cmpt.class, "Qualifier1");
		assertNotNull(c1);
		assertTrue(c1 instanceof CmptQualifierOneImpl);
		
		Cmpt c1Factory = factory(Cmpt.class, "Qualifier1");
		assertNotNull(c1Factory);

		Cmpt c2 = my(Cmpt.class, "Qualifier2");
		assertNotNull(c2);
		assertTrue(c2 instanceof CmptQualifierTwoImpl);
	}
}