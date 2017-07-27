package org.jspare.jpa.injector;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jspare.core.InjectorAdapter;
import org.jspare.core.MySupport;
import org.jspare.jpa.PersistenceUnitProvider;
import org.jspare.jpa.annotation.RepositoryInject;
import org.springframework.data.repository.Repository;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

@Slf4j
public class RepositoryInjectStrategy extends MySupport implements InjectorAdapter {

  @Inject
  private PersistenceUnitProvider provider;

  @Override
  public boolean isInjectable(Field field) {
    return field.isAnnotationPresent(RepositoryInject.class);
  }

  @Override
  public void inject(Object result, Field field) {
    try {

      String datasourceName = field.getAnnotation(RepositoryInject.class).datasource();
      EntityManagerFactory emf = provider.getProvider(datasourceName);

      if (emf == null)
        return;

      Object repository = Proxy.newProxyInstance(RepositoryInjectStrategy.class.getClassLoader(),
        new Class[]{field.getType(), Repository.class}, new RepositoryInvocationHandler(field.getType(), emf));

      field.setAccessible(true);
      field.set(result, repository);

    } catch (Exception e) {

      log.error("Failed to create repository {}", field.getType(), e);
    }
  }

  @Data
  @AllArgsConstructor
  class RepositoryHolder {
    private String datasource;
  }
}
