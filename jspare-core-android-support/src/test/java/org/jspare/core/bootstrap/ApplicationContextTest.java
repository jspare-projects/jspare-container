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
package org.jspare.core.bootstrap;

import org.jspare.core.dummy.DummyBoostrap;
import org.jspare.core.dummy.DummyInvalidBoostrap;
import org.junit.Assert;
import org.junit.Test;

public class ApplicationContextTest {

	private static final String KEY = "key";

	@Test
	public void contextTest() {

		Application application = new Application() {

			@Override
			public void start() {

				getContext().put(KEY, true);
			}
		};
		application.run();

		Assert.assertTrue((boolean) application.getContext().getAs(KEY));
	}
	
	@Test
	public void runBootstrapTest(){
		
		Application application = Application.create(DummyBoostrap.class);
		Assert.assertNotNull(application);
		
		Application.run(DummyBoostrap.class);
	}
	
	@Test(expected=IllegalAccessException.class)
	public void invalidBootstrapTest(){
		
		Application.create(DummyInvalidBoostrap.class);
	}
	
	@Test(expected=IllegalAccessException.class)
	public void invalidRunBootstrapTest(){
		
		Application.run(DummyInvalidBoostrap.class);
	}
}