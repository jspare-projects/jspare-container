/**
 * Copyright 2016 Senior Sistemas.
 * <p>
 * Software sob Medida
 */
package org.jspare.unit.mock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The Interface Mock.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Mock {
}
