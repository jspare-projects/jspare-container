/**
 * Copyright 2016 Senior Sistemas.
 * <p>
 * Software sob Medida
 */
package org.jspare.unit.mock;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * The Class Mocker.
 */
public final class Mocker {

  /**
   * Allow fixed return
   */
  private static final Map<Class<?>, Map<String, Object>> MOCKED = new HashMap<>();

  /**
   * Clear returns.
   */
  public static void clearReturns() {

    MOCKED.values().forEach(Map::clear);
  }

  /**
   * Do return.
   *
   * @param mocked     the mocked
   * @param methodName the method name
   * @param result     the result
   */
  public static void whenReturn(Object mocked, String methodName, Object result) {
    ((Mocked) mocked).fixReturn(methodName, result);
  }

  /**
   * Do return.
   *
   * @param mocked     the mocked
   * @param methodName the method name
   * @param result     the result
   */
  public static void whenReturn(Object mocked, String methodName, Function<Object[], Object> result) {
    ((Mocked) mocked).fixReturnSupplied(methodName, result);
  }

  /**
   * Creates the proxy.
   *
   * @param <T>            the generic type
   * @param interfaceClass the interface class
   * @return the t
   */
  @SuppressWarnings("unchecked")
  public static <T> T createProxy(Class<T> interfaceClass) {

    MOCKED.put(interfaceClass, new HashMap<>());

    return (T) Proxy.newProxyInstance(Mocker.class.getClassLoader(), new Class[]{interfaceClass, Mocked.class},
      new InvocationHandler() {

        private Class<T> clazz = interfaceClass;

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

          if (Arrays.asList("fixReturn", "fixReturnSupplied").contains(method.getName())) {
            MOCKED.get(clazz).put((String) args[0], args[1]);
            return null;
          }
          Object result = null;

          if (MOCKED.get(clazz).containsKey(method.getName())) {

            result = MOCKED.get(clazz).get(method.getName());
            if (result instanceof Function) {

              result = ((Function) result).apply(args);
            }
          }
          return result;
        }
      });
  }
}
