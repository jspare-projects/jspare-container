package org.jspare.jpa;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.jspare.core.Environment;
import org.jspare.core.MySupport;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.inject.Singleton;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Singleton
public class PersistenceUnitProviderImpl extends MySupport implements PersistenceUnitProvider {

  private final Map<String, EntityManagerFactory> providers;

  public PersistenceUnitProviderImpl() {
    providers = new HashMap<>();
  }

  @Override
  public void create(PersistenceOptions config) {
    create(DEFAULT_DS, config);
  }

  @Override
  public void create(String datasourceName, PersistenceOptions config) {

    LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
    emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
    emf.setJpaProperties(createProperties(config));
    emf.setDataSource(getDataSource(datasourceName, config));
    emf.setPersistenceUnitName(datasourceName);
    emf.setPersistenceProvider(new HibernatePersistenceProvider());
    emf.setPackagesToScan(config.getAnnotatedClasses().toArray(new String[]{}));
    emf.afterPropertiesSet();

    providers.put(datasourceName, emf.getObject());
  }

  @Override
  public EntityManagerFactory getProvider() {

    return getProvider(DEFAULT_DS);
  }

  @Override
  public EntityManagerFactory getProvider(String datasourceName) {

    return providers.get(datasourceName);
  }

  private DataSource getDataSource(String datasourceName, PersistenceOptions config) {
    try {

      DataSource ds = new ComboPooledDataSource();
      ((ComboPooledDataSource) ds).setDataSourceName(datasourceName);
      ((ComboPooledDataSource) ds).setDriverClass(config.getDriverClass());
      ((ComboPooledDataSource) ds).setJdbcUrl(config.getUrl());
      ((ComboPooledDataSource) ds).setUser(config.getUsername());
      ((ComboPooledDataSource) ds).setPassword(config.getPassword());
      ((ComboPooledDataSource) ds).setInitialPoolSize(config.getInitialPool());
      ((ComboPooledDataSource) ds).setMinPoolSize(config.getMinPool());
      ((ComboPooledDataSource) ds).setMaxPoolSize(config.getMaxPool());
      ((ComboPooledDataSource) ds).setMaxIdleTime(config.getMaxIdleTime());
      ((ComboPooledDataSource) ds).setMaxStatementsPerConnection(config.getMaxStatementsPerConnection());
      return ds;
    } catch (Exception e) {

      throw new RuntimeException(e);
    }
  }

  private Properties createProperties(PersistenceOptions config) {
    Properties properties = new Properties();
    properties.setProperty("hibernate.hbm2ddl.auto", config.getHbm2ddl());
    properties.setProperty("hibernate.dialect", config.getDialect());
    properties.setProperty("hibernate.show_sql", config.getShowCommands().toString());
    return properties;
  }
}
