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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.jspare.core.annotation.Inject;

public class LoginServiceImpl implements LoginService {

	private static Map<String, String> user2token = new HashMap<>();

	@Inject
	private LoginDao loginDao;

	@Override
	public Optional<String> login(String username, String password) {

		if (user2token.containsKey(username)) {

			return Optional.of(user2token.get(username));
		}

		if (!loginDao.validate(username, password)) {

			return Optional.empty();
		}

		String token = UUID.randomUUID().toString();
		user2token.put(username, token);
		return Optional.of(token);
	}

	@Override
	public boolean validate(String token) {

		return user2token.values().contains(token);
	}
}