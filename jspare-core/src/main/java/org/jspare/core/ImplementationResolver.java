package org.jspare.core;

/**
 * @author <a href="https://pflima92.github.io/">Paulo Lima</a>
 */
public interface ImplementationResolver {
  Class<?> supply(Class<?> classInterface);
}
