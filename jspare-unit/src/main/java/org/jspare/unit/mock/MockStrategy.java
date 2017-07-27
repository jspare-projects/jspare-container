package org.jspare.unit.mock;

import lombok.SneakyThrows;
import org.jspare.core.InjectorAdapter;

import java.lang.reflect.Field;

/**
 * Injector adapater for {@link Mock} annotation.
 * Created by paulo.ferreira on 25/07/2017.
 */
public class MockStrategy implements InjectorAdapter {

  @Override
  public boolean isInjectable(Field field) {
    return field.isAnnotationPresent(Mock.class);
  }

  @Override
  @SneakyThrows
  public void inject(Object instance, Field field) {

    Object result = Mocks.injectMocks(field.getType());
    field.setAccessible(true);
    field.set(instance, result);
  }
}
