/**
 * Copyright 2016 Senior Sistemas.
 * <p>
 * Software sob Medida
 */
package org.jspare.unit.mock;

import java.util.function.Function;

/**
 * The Interface Mocked.
 */
public interface Mocked {

  /**
   * Fix return.
   *
   * @param methodName the method name
   * @param result     the result
   */
  void fixReturn(String methodName, Object result);

  <T> void fixReturnSupplied(String methodName, Function<Object[], T> result);
}
