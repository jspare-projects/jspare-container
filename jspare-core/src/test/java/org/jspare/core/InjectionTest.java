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
package org.jspare.core;

import org.jspare.core.helpers.annotation.ClassInjected;
import org.jspare.core.helpers.qualifier.CmptQualifierOneImpl;
import org.jspare.core.helpers.qualifier.CmptQualifierTwoImpl;
import org.jspare.core.helpers.usage.Cmpt;
import org.jspare.core.helpers.usage.CmptImpl;
import org.jspare.core.helpers.usage.CmptOtherImpl;
import org.jspare.core.internal.Bind;
import org.junit.Test;

import static org.jspare.core.Environment.my;
import static org.jspare.core.Environment.registry;
import static org.junit.Assert.*;

/**
 * The Class EnvironmentTest.
 *
 * @author pflima
 * @since 05/10/2015
 */
public class InjectionTest extends AbstractApplicationTest {

  /**
   * Test container components instantiation.
   */
  @Test
  public void testContainerComponentsInstantiation() {

    registry(Bind.bind(Cmpt.class).to(CmptImpl.class));

    Cmpt c1 = my(Cmpt.class);
    assertNotNull(c1);
    assertTrue(c1 instanceof CmptImpl);

    Cmpt c2 = my(Cmpt.class);
    assertSame(c1, c2);

    registry(Bind.bind(Cmpt.class), new CmptImpl());
    registry(Bind.bind(Cmpt.class), new CmptOtherImpl());

    Cmpt c3 = my(Cmpt.class);
    assertNotSame(c1, c3);
    assertTrue(c3 instanceof CmptOtherImpl);
  }

  /**
   * Test getInstance annotation.
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

    registry(Bind.bind(Cmpt.class), new CmptImpl());

    registry(Bind.bind(Cmpt.class), new CmptQualifierOneImpl());
    registry(Bind.bind(Cmpt.class), new CmptQualifierTwoImpl());

    Cmpt c1 = my(Cmpt.class, "CmptQualifierOneImpl");
    assertNotNull(c1);
    assertTrue(c1 instanceof CmptQualifierOneImpl);

    Cmpt c2 = my(Cmpt.class, "CmptQualifierTwoImpl");
    assertNotNull(c2);
    assertTrue(c2 instanceof CmptQualifierTwoImpl);

    Cmpt c1Factory = Environment.provide(Cmpt.class, "CmptQualifierOneImpl");
    assertNotNull(c1Factory);
  }
}
