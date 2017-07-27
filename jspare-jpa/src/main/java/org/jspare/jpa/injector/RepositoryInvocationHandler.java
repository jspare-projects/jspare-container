/*
 * Copyright (c) 2011-2014 The original author or authors
 * ------------------------------------------------------
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 *
 *     The Eclipse Public License is available at
 *     http://www.eclipse.org/legal/epl-v10.html
 *
 *     The Apache License v2.0 is available at
 *     http://www.opensource.org/licenses/apache2.0.php
 *
 * You may elect to redistribute this code under either of these licenses.
 */
package org.jspare.jpa.injector;

import lombok.RequiredArgsConstructor;
import org.jspare.core.MySupport;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.support.RepositoryProxyPostProcessor;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.interceptor.MatchAlwaysTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author <a href="https://pflima92.github.io/">Paulo Lima</a>
 */
@RequiredArgsConstructor
public class RepositoryInvocationHandler extends MySupport implements InvocationHandler {

  private final Class<?> type;

  private final EntityManagerFactory emf;

  /*
   * (non-Javadoc)
   *
   * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object,
   * java.lang.reflect.Method, java.lang.Object[])
   */
  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    EntityManager em = emf.createEntityManager();

    // Create your transaction manager and RespositoryFactory
    final JpaTransactionManager xactManager = new JpaTransactionManager(emf);
    final JpaRepositoryFactory factory = new JpaRepositoryFactory(em);

    // Make sure calls to the repository instance are intercepted for
    // annotated transactions
    factory.addRepositoryProxyPostProcessor(new RepositoryProxyPostProcessor() {
      @Override
      public void postProcess(ProxyFactory factory, RepositoryInformation repositoryInformation) {
        factory.addAdvice(new TransactionInterceptor(xactManager, new MatchAlwaysTransactionAttributeSource()));
      }
    });

    @SuppressWarnings("rawtypes")
    Repository repository = (Repository) factory.getRepository(type);

    // Bind the same EntityManger used to create the Repository to the thread
    TransactionSynchronizationManager.bindResource(emf, new EntityManagerHolder(em));
    TransactionSynchronizationManager.initSynchronization();
    try {

      return method.invoke(repository, args);
    } finally {

      if (em.isOpen()) {
        em.close();
      }

      // Make sure to unbind when done with the repository instance
      TransactionSynchronizationManager.unbindResource(emf);
      TransactionSynchronizationManager.clearSynchronization();
    }
  }
}
