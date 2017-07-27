package org.jspare.unit.mock;

import lombok.experimental.UtilityClass;
import org.jspare.core.internal.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * Created by paulo.ferreira on 23/06/2017.
 */
@UtilityClass
public class MockerUtils {

  /**
   * Initialize one Mocker class.
   *
   * @param clazz the class
   */
  public void initialize(Class<?> clazz) {
    ReflectionUtils.getFieldsWithAnnotation(clazz, Mock.class).forEach(MockerUtils::addProxy);
  }

  /**
   * Adds the proxy.
   * @param field the field
   */
  private void addProxy(Field field) {
    Object mocker = Mocker.createProxy(field.getType());
    Mocks.registry(field.getType(), mocker);
  }
}
