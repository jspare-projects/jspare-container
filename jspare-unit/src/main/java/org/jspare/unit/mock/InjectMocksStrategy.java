package org.jspare.unit.mock;

import lombok.SneakyThrows;
import org.jspare.core.InjectorAdapter;

import java.lang.reflect.Field;

/**
 * Injector adapater for {@link InjectMocks} annotation.
 * <p>
 * Created by paulo.ferreira on 25/07/2017.
 */
public class InjectMocksStrategy implements InjectorAdapter {

  @Override
  public boolean isInjectable(Field field) {
    return field.isAnnotationPresent(InjectMocks.class);
  }

  @Override
  @SneakyThrows
  public void inject(Object instance, Field field) {

    Object result = Mocks.injectMocks(field.getType());
    field.setAccessible(true);
    field.set(instance, result);
  }
}
