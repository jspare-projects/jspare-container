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
package org.jspare.core.container.test.qualifier;

import org.jspare.core.container.Qualifier;
import org.jspare.core.container.test.usage.Cmpt;

/**
 * The Class CmptQualifierTwoImpl.
 *
 * @author pflima
 * @since 30/03/2016
 */
@Qualifier("Qualifier2")
public class CmptQualifierTwoImpl implements Cmpt {

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.core.container.additional.usage.Cmpt#print()
	 */
	@Override
	public String print() {
		return getClass().getSimpleName();
	}
}