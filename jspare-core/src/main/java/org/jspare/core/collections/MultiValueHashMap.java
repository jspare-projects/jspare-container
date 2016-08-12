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
package org.jspare.core.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The Class MultiValueHashMap.
 *
 * @author pflima
 * @param <K>
 *            the key type
 * @param <V>
 *            the value type
 * @since 26/07/2016
 */
public class MultiValueHashMap<K, V> implements MultiValueMap<K, V> {

	/** The store. */
	protected Map<K, List<V>> store = new HashMap<>();

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.core.collections.MultiValueMap#add(java.lang.Object,
	 * java.util.List)
	 */
	@Override
	public void add(K key, List<V> values) {
		if (!store.containsKey(key)) {

			store.put(key, new ArrayList<>());
		}
		store.get(key).addAll(values);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.core.collections.MultiValueMap#add(java.lang.Object,
	 * java.lang.Object)
	 */
	@Override
	public void add(K key, V value) {
		if (!store.containsKey(key)) {

			store.put(key, new ArrayList<>());
		}
		store.get(key).add(value);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#clear()
	 */
	@Override
	public void clear() {

		store.clear();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#containsKey(java.lang.Object)
	 */
	@Override
	public boolean containsKey(Object key) {

		return store.containsKey(key);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#containsValue(java.lang.Object)
	 */
	@Override
	public boolean containsValue(Object value) {

		return store.containsValue(value);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#entrySet()
	 */
	@Override
	public Set<java.util.Map.Entry<K, List<V>>> entrySet() {

		return store.entrySet();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#get(java.lang.Object)
	 */
	@Override
	public List<V> get(Object key) {

		return store.get(key);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#isEmpty()
	 */
	@Override
	public boolean isEmpty() {

		return store.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#keySet()
	 */
	@Override
	public Set<K> keySet() {

		return store.keySet();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	public List<V> put(K key, List<V> value) {

		return store.put(key, value);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#putAll(java.util.Map)
	 */
	@Override
	public void putAll(Map<? extends K, ? extends List<V>> m) {

		store.putAll(m);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.jspare.core.collections.MultiValueMap#putSingle(java.lang.Object,
	 * java.lang.Object)
	 */
	@Override
	public void putSingle(K key, V value) {

		store.put(key, Arrays.asList(value));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	@Override
	public List<V> remove(Object key) {

		return store.remove(key);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#size()
	 */
	@Override
	public int size() {

		return store.size();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#values()
	 */
	@Override
	public Collection<List<V>> values() {

		return store.values();
	}

}
