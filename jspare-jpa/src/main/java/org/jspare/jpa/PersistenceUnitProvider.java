package org.jspare.jpa;

import org.jspare.core.Component;

import javax.persistence.EntityManagerFactory;

@Component
public interface PersistenceUnitProvider {

  String DEFAULT_DS = "DEFAULT_DS";

  void create(PersistenceOptions config);

  void create(String datasource, PersistenceOptions config);

  EntityManagerFactory getProvider();

  EntityManagerFactory getProvider(String datasourceName);
}
