package org.jspare.jpa.spi;

import java.util.List;
import java.util.Properties;

import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;

/**
 * The Interface DefaultPersistanceUnitInfo.
 */
public interface SmartPersistanceUnitInfo extends PersistenceUnitInfo {

  /**
   * Adds the annotated class name.
   *
   * @param clazz
   *          the clazz
   */
  void addAnnotatedClassName(Class<?> clazz);

  /**
   * Adds the annotated class name.
   *
   * @param className
   *          the class name
   */
  void addAnnotatedClassName(String className);

  /**
   * Adds the managed package.
   *
   * @param packageName
   *          the package name
   */
  void addManagedPackage(String packageName);

  /**
   * Gets the managed packages.
   *
   * @return the managed packages
   */
  List<String> getManagedPackages();

  /**
   * Sets the persistence provider package name.
   *
   * @param persistenceProviderPackageName
   *          the new persistence provider package 'name
   */
  void setPersistenceProviderPackageName(String persistenceProviderPackageName);

  /**
   * Sets the properties.
   *
   * @param properties
   *          the new properties
   */
  void setProperties(Properties properties);

  /**
   * Sets the transaction type.
   *
   * @param resourceLocal
   *          the new transaction type
   */
  void setTransactionType(PersistenceUnitTransactionType resourceLocal);
}