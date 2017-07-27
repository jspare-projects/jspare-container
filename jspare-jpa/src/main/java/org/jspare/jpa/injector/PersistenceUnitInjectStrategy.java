package org.jspare.jpa.injector;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jspare.core.InjectorAdapter;
import org.jspare.core.MySupport;
import org.jspare.jpa.PersistenceUnitProvider;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.lang.reflect.Field;

@Slf4j
public class PersistenceUnitInjectStrategy extends MySupport implements InjectorAdapter {

  @Inject
  private PersistenceUnitProvider provider;

  @Override
  public boolean isInjectable(Field field) {
    return field.isAnnotationPresent(PersistenceUnit.class);
  }

  @Override
  public void inject(Object result, Field field) {
    try {

      String unitName = field.getAnnotation(PersistenceUnit.class).unitName();
      if (StringUtils.isEmpty(unitName))
        unitName = PersistenceUnitProvider.DEFAULT_DS;

      if(!field.getType().equals(EntityManagerFactory.class)){

        log.error("Failed to create PersistenceUnit, type of field is not a EntityManagerFactory");
        return;
      }

      EntityManagerFactory emf = provider.getProvider();

      field.setAccessible(true);
      field.set(result, emf);
    } catch (Exception e) {

      log.error("Failed to create PersistenceUnit", e);
    }
  }
}
