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

import org.jspare.core.bootstrap.Application;
import org.jspare.core.bootstrap.EnvironmentBuilder;
import org.junit.Before;

public abstract class AbstractApplicationTest {

    @Before
    public void setup() {

        // Simulate static main invocation
        new BootstrapUnitTest().run();
    }

    protected EnvironmentBuilder toLoad() {
        return EnvironmentBuilder.create();
    }

    class BootstrapUnitTest extends Application {

        @Override
        public void start() {

        }

        @Override
        public void setup() {

            builder(toLoad());
        }
    }
}