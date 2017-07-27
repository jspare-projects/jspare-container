package org.jspare.core;

import javax.inject.Provider;

/**
 * <p>Responsible to create one instance instead of one {@link Provider}. </p>
 *
 * @param <T> the t
 * @author <a href="https://pflima92.github.io/">Paulo Lima</a>
 */
public interface Factory<T> extends Provider<T> {
}
