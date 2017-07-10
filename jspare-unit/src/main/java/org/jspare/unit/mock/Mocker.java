/**
 * Copyright 2016 Senior Sistemas.
 * <p>
 * Software sob Medida
 */
package org.jspare.unit.mock;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * The Class Mocker.
 */
public abstract class Mocker {

  /** The Constant MOCKED. */
  protected static final Map<Class<?>, Map<String, Object>> MOCKED = new HashMap<>();

  /**
   * Clear returns.
   */
  public static void clearReturns() {

    MOCKED.values().forEach(Map::clear);
  }

  /**
   * Do return Completable.
   *
   * @param mocked
   *            the mocked
   * @param methodName
   *            the method name
   * @param result
   *            the result
   */
  public static void whenCompletableFutureReturn(Object mocked, String methodName, Object result) {
    ((Mocked) mocked).fixReturn(methodName, CompletableFuture.supplyAsync(() -> result));
  }

  /**
   * Do return.
   *
   * @param mocked
   *            the mocked
   * @param methodName
   *            the method name
   * @param result
   *            the result
   */
  public static void whenReturn(Object mocked, String methodName, Object result) {
    ((Mocked) mocked).fixReturn(methodName, result);
  }

  /**
   * Creates the proxy.
   *
   * @param <T>
   *            the generic type
   * @param interfaceClass
   *            the interface class
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

          if (method.getName().equals("fixReturn")) {
            MOCKED.get(clazz).put((String) args[0], args[1]);
            return null;
          }

          Object result = null;

          if (MOCKED.get(clazz).containsKey(method.getName())) {
            result = MOCKED.get(clazz).get(method.getName());
            return result;
          }

          return result;
        }
      });
  }
}
