package org.jspare.jpa;

import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Data
public class PersistenceOptions {

  private static final String DEFAULT_HBM2DDL = "update";
  public static final int DEFAULT_MAX_STATEMENTS_PER_CONNECTION = 0;
  public static final int DEFAULT_MAX_IDLE_TIME = 0;
  public static final int DEFAULT_INITIAL_POOL = 1;
  public static final int DEFAULT_MAX_POOL = 15;
  public static final int DEFAULT_MIN_POOL = 1;
  public static final String DEFAULT_H2_DRIVER = "org.h2.Driver";
  public static final String DEFAULT_URL = "jdbc:h2:mem:test_mem;";
  public static final String DEFAULT_H2_DIALECT = "org.hibernate.dialect.H2Dialect";

  private String url;
  private String driverClass;
  private String dialect;
  private String username;
  private String password;
  private Integer maxPool;
  private Integer minPool;
  private Integer initialPool;
  private Integer maxIdleTime;
  private Integer maxStatementsPerConnection;
  private Boolean showCommands;
  private Boolean testConnection;
  private String hbm2ddl;
  private List<String> annotatedClasses;

  public PersistenceOptions() {
    init();
  }

  public PersistenceOptions(PersistenceOptions other) {
    this();
    url = other.url;
    driverClass = other.driverClass;
    dialect = other.dialect;
    username = other.username;
    password = other.password;
    maxPool = other.maxPool;
    minPool = other.minPool;
    initialPool = other.initialPool;
    maxIdleTime = other.maxIdleTime;
    maxStatementsPerConnection = other.maxStatementsPerConnection;
    showCommands = other.showCommands;
    testConnection = other.testConnection;
    annotatedClasses = other.annotatedClasses;
    hbm2ddl = other.hbm2ddl;
  }

  private void init() {

    url = DEFAULT_URL;
    driverClass = DEFAULT_H2_DRIVER;
    dialect = DEFAULT_H2_DIALECT;
    username = StringUtils.EMPTY;
    password = StringUtils.EMPTY;
    maxPool = DEFAULT_MAX_POOL;
    minPool = DEFAULT_MIN_POOL;
    initialPool = DEFAULT_INITIAL_POOL;
    maxIdleTime = DEFAULT_MAX_IDLE_TIME;
    maxStatementsPerConnection = DEFAULT_MAX_STATEMENTS_PER_CONNECTION;
    showCommands = false;
    testConnection = true;
    hbm2ddl = DEFAULT_HBM2DDL;
    annotatedClasses = new ArrayList<>();
  }
}