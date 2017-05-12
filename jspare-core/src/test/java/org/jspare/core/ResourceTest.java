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

import org.jspare.core.helpers.FooResource;
import org.junit.Assert;
import org.junit.Test;

import static org.jspare.core.Environment.my;

public class ResourceTest {

  @Test
  public void resourceInjectionTest() {

    Environment.release();

    FooResource foo = my(FooResource.class);

    // Test injection
    Assert.assertNotNull(foo);

    // Test default value of helpers data
    Assert.assertEquals(FooResource.DEFAULT, foo.getDefaultName());

    foo.setDefaultName("test");

    // Test reference retention
    Assert.assertEquals("test", my(FooResource.class).getDefaultName());

    // Test factory instantiation
    Assert.assertEquals(FooResource.DEFAULT, Environment.provide(FooResource.class).getDefaultName());

    // Test original reference retention
    Assert.assertEquals("test", my(FooResource.class).getDefaultName());
  }
}
