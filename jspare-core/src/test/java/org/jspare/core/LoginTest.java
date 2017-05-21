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

import org.jspare.core.helpers.LoginDao;
import org.jspare.core.helpers.LoginDaoWithoutMaster;
import org.jspare.core.helpers.LoginService;
import org.jspare.core.internal.Bind;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

import static org.jspare.core.Environment.my;
import static org.jspare.core.Environment.registry;

public class LoginTest extends AbstractApplicationTest {

  @Test
  public void anotherLoginDaoTest() {

    Environment.release();
    registry(Bind.bind(LoginDao.class).to(LoginDaoWithoutMaster.class));
    Assert.assertFalse(my(LoginDao.class).validate("admin", "admin"));
    Optional<String> token = my(LoginService.class).login("admin", "admin");
    Assert.assertFalse(token.isPresent());
  }

  @Test
  public void usageTest() {

    Environment.release();
    LoginService service = my(LoginService.class);
    Optional<String> token = service.login("admin", "admin");
    Assert.assertTrue(token.isPresent());
  }
}
