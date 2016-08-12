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

import static java.util.Collections.unmodifiableCollection;
import static java.util.Collections.unmodifiableSet;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * The Class TimedHashMap.
 *
 * @author pflima
 * @param <K>
 *            the key type
 * @param <V>
 *            the value type
 * @since 26/07/2016
 */
public class TimedHashMap<K, V> implements TimedMap<K, V> {

	/** The store. */
	private final ConcurrentHashMap<K, V> store = new ConcurrentHashMap<>();

	/** The timestamps. */
	private final ConcurrentHashMap<K, LocalDateTime> timestamps = new ConcurrentHashMap<>();

	/** The time to live. */
	private final long timeToLive;

	/**
	 * Instantiates a new timed hash map.
	 *
	 * @param timeUnit
	 *            the time unit
	 * @param timeToLiveValue
	 *            the time to live value
	 */
	public TimedHashMap(TimeUnit timeUnit, long timeToLiveValue) {
		this.timeToLive = timeUnit.toMillis(timeToLiveValue);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#clear()
	 */
	@Override
	public void clear() {
		timestamps.clear();
		store.clear();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#containsKey(java.lang.Object)
	 */
	@Override
	public boolean containsKey(Object key) {
		return store.containsKey(key) && !expired(key);
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
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		clearExpired();
		return unmodifiableSet(store.entrySet());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#get(java.lang.Object)
	 */
	@Override
	public V get(Object key) {

		if (key == null) {

			return null;
		}

		V value = this.store.get(key);

		if (value != null && expired(key)) {
			store.remove(key);
			timestamps.remove(key);
			return null;
		} else {
			return value;
		}
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
		clearExpired();
		return unmodifiableSet(store.keySet());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	public V put(K key, V value) {

		timestamps.put(key, LocalDateTime.now());
		return store.put(key, value);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#putAll(java.util.Map)
	 */
	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		for (Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
			this.put(e.getKey(), e.getValue());
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	@Override
	public V remove(Object key) {
		timestamps.remove(key);
		return store.remove(key);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.core.collections.TimedMap#renewKey(java.lang.Object)
	 */
	@Override
	public void renewKey(K key) {
		timestamps.put(key, LocalDateTime.now());
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
	public Collection<V> values() {
		clearExpired();
		return unmodifiableCollection(store.values());
	}

	/**
	 * Clear expired.
	 */
	private void clearExpired() {
		for (K k : store.keySet()) {
			this.get(k);
		}
	}

	/**
	 * Expired.
	 *
	 * @param key
	 *            the key
	 * @return true, if successful
	 */
	private boolean expired(Object key) {

		return Duration.between(LocalDateTime.now(), timestamps.get(key)).toMillis() > this.timeToLive;
	}
}