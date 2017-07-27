package org.jspare.unit.mock;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.jspare.core.Environment;
import org.jspare.core.internal.Bind;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Mocks preserve instances on unit test context.
 * Created by paulo.ferreira on 25/07/2017.
 */
@UtilityClass
public final class Mocks {

  private final Map<Class<?>, Object> MOCKS = new HashMap<>();

  public void registry(Class<?> clazz, Object instance) {
    MOCKS.put(clazz, instance);
    Environment.registry(Bind.bind(clazz), instance);
  }

  @SneakyThrows
  public <T> T injectMocks(Class<T> clazz) {
    if (!MOCKS.containsKey(clazz)) {
      T instance = Environment.my(clazz);
      Arrays.asList(instance.getClass().getDeclaredFields()).stream().forEach(f -> accept(f, instance));
      return instance;
    }
    return (T) MOCKS.get(clazz);
  }

  @SneakyThrows
  private void accept(Field field, Object instance) {
    if (MOCKS.containsKey(field.getType())) {
      field.setAccessible(true);
      field.set(instance, MOCKS.get(field.getType()));
    }
  }
}
