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

import org.jspare.core.exception.EnvironmentException;
import org.jspare.core.helpers.application.qualified.Qualified;
import org.jspare.core.helpers.application.qualified.QualifiedImpl;
import org.jspare.core.helpers.application.qualified.QualifiedOne;
import org.jspare.core.helpers.application.qualified.QualifiedTwo;
import org.jspare.core.helpers.usage.Cmpt;
import org.jspare.core.internal.Bind;
import org.junit.Assert;
import org.junit.Test;

import static org.jspare.core.Environment.my;
import static org.jspare.core.Environment.registry;
import static org.junit.Assert.assertTrue;

public class InjectionQualifiedTest extends AbstractApplicationTest {

  @Test
  public void testQualifiedComponents() {

    registry(Bind.bind(Qualified.class).to(QualifiedOne.class));
    registry(Bind.bind(Qualified.class).to(QualifiedTwo.class));

    Qualified multiple = my(Qualified.class);
    assertTrue(multiple instanceof QualifiedImpl);

    Qualified multipleOne = my(Qualified.class, "QualifiedOne");
    assertTrue(multipleOne instanceof QualifiedOne);

    Qualified multipleTwo = my(Qualified.class, "MultipleTwo");
    assertTrue(multipleTwo instanceof QualifiedTwo);
  }

  /**
   * Test invalid components instantiation.
   */
  @Test(expected = EnvironmentException.class)
  public void testQualifierComponentsInstantiationWithoutRegistry() {

    Cmpt cmpt = my(Cmpt.class, "QualifierThatNotExist");
    Assert.assertNull(cmpt);
  }

}
