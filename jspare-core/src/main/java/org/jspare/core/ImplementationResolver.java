package org.jspare.core;

/**
 * Created by paulo.ferreira on 08/05/2017.
 */
public interface ImplementationResolver {
  Class<?> supply(Class<?> classInterface);
}
