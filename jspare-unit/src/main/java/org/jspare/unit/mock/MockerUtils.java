package org.jspare.unit.mock;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.jspare.core.Environment;
import org.jspare.core.internal.Bind;
import org.jspare.core.internal.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * Created by paulo.ferreira on 23/06/2017.
 */
@UtilityClass
public class MockerUtils {

  public void initialize(Class<?> clazz) {
    ReflectionUtils.getFieldsWithAnnotation(clazz, Mock.class).forEach(MockerUtils::addProxy);
  }

  /**
   * Adds the proxy.
   *
   * @param field the field
   */
  @SneakyThrows
  private void addProxy(Field field) {
    Object mocker = Mocker.createProxy(field.getType());
    Environment.registry(Bind.bind(field.getType()), mocker);
  }
}
