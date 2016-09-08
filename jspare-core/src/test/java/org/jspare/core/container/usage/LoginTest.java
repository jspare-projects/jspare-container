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
package org.jspare.core.container.usage;

import static org.jspare.core.container.Environment.my;
import static org.jspare.core.container.Environment.registryComponent;

import java.util.Optional;

import org.jspare.core.container.Environment;
import org.junit.Assert;
import org.junit.Test;

public class LoginTest {

	@Test
	public void anotherLoginDaoTest() {

		Environment.release();
		registryComponent(LoginDaoWithoutMaster.class);

		Assert.assertFalse(my(LoginDao.class).validate("admin", "admin"));

		Optional<String> token = my(LoginService.class).login("admin", "admin");
		Assert.assertFalse(token.isPresent());
	}

	@Test
	public void usageTest() {
		Environment.release();
		Optional<String> token = my(LoginService.class).login("admin", "admin");
		Assert.assertTrue(token.isPresent());
	}
}
