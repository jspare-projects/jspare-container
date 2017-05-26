package org.jspare.jpa.injector;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jspare.core.InjectorAdapter;
import org.jspare.core.MySupport;
import org.jspare.jpa.PersistenceUnitProvider;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.Field;

@Slf4j
public class PersistenceContextInjectStrategy extends MySupport implements InjectorAdapter {

    @Inject
    private PersistenceUnitProvider provider;

    @Override
    public boolean isInjectable(Field field) {
        return field.isAnnotationPresent(PersistenceContext.class);
    }

    @Override
    public void inject(Object result, Field field) {
        try {

            String unitName = field.getAnnotation(PersistenceContext.class).unitName();
            if (StringUtils.isEmpty(unitName))
                unitName = PersistenceUnitProvider.DEFAULT_DS;

            if (!field.getType().equals(EntityManager.class)) {

                log.error("Failed to create PersistenceContext, type of field is not a EntityManager");
                return;
            }

            EntityManager em = provider.getProvider().createEntityManager();

            field.setAccessible(true);
            field.set(result, em);
        } catch (Exception e) {

            log.error("Failed to create PersistenceContext", e);
        }
    }
}