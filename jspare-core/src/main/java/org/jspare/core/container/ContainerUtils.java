/*
 * Copyright 2016 JSpare.org.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.jspare.core.container;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import org.jspare.core.annotation.After;
import org.jspare.core.annotation.Component;
import org.jspare.core.annotation.Resource;
import org.jspare.core.exception.EnvironmentException;
import org.jspare.core.exception.Errors;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static org.jspare.core.container.Environment.*;

/**
 * The Class ContainerUtils.
 * <p>
 * Used by Environment for Container actions
 * <p>
 * The Constant log.
 */

/** The Constant log. */

/** The Constant log. */
public final class ContainerUtils {

    /** The Constant SUFIX_DEFAULT_IMPL. */
    private static final String SUFIX_DEFAULT_IMPL = "Impl";

    /** The Constant ALL_SCAN_QUOTE. */
    private static final String ALL_SCAN_QUOTE = ".*";

    /**
     * Process environment injection using the container.
     *
     * @param result
     *            the result
     */
    public static void processInjection(Object result) {

        Class<?> clazz = result.getClass();
        try {

            for (Field field : collectFields(clazz)) {

                setField(result, field);
            }

            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(After.class)) {

                    method.setAccessible(true);
                    method.invoke(result);
                }
            }

        } catch (StackOverflowError | IllegalArgumentException | IllegalAccessException | ClassNotFoundException
                | InvocationTargetException e) {

            throw new EnvironmentException(Errors.INVALID_INJECTION);
        }
    }

    /**
     * Process injection. <br>
     * <p>
     * Deprecated method, use {@link ContainerUtils }.processInjection(Object
     * result)
     * </p>
     *
     * @param clazz
     *            the clazz
     * @param result
     *            the result
     */
    @Deprecated
    public static void processInjection(Class<?> clazz, Object result) {
        processInjection(result);
    }

    /**
     * Collec interfaces.
     *
     * @param clazz
     *            the clazz
     * @return the list
     */
    protected static List<Class<?>> collecInterfaces(Class<?> clazz) {
        List<Class<?>> interfaces = new ArrayList<>();
        interfaces.addAll(Arrays.asList(clazz.getInterfaces()));

        if (clazz.getSuperclass() != null) {

            interfaces.addAll(Arrays.asList(clazz.getSuperclass().getInterfaces()));
        }
        return interfaces;
    }

    /**
     * Collect fields.
     *
     * @param clazz
     *            the clazz
     * @return the list
     */
    protected static List<Field> collectFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));

        if (clazz.getSuperclass() != null) {

            fields.addAll(Arrays.asList(clazz.getSuperclass().getDeclaredFields()));
        }
        return fields;
    }

    /**
     * Collect injectors.
     *
     * @param field
     *            the field
     * @return the list
     */
    protected static List<InjectorStrategy> collectInjectors(Field field) {

        return Environment.INJECTORS.keySet().stream().filter(clazz -> field.isAnnotationPresent(clazz))
                .map(clazz -> Environment.INJECTORS.get(clazz)).collect(Collectors.toList());
    }

    /**
     * Contains interface class.
     *
     * @param clazzImpl
     *            the clazz impl
     * @param interfaceClazz
     *            the interface clazz
     * @return true, if successful
     */
    protected static boolean containsInterfaceClass(Class<?> clazzImpl, Class<?> interfaceClazz) {

        return collecInterfaces(clazzImpl).contains(interfaceClazz);
    }

    /**
     * Find clazz impl.
     *
     * @param clazz
     *            the clazz
     * @return the class
     */
    protected static Class<?> findClazzImpl(Class<?> clazz) {

        Class<?> clazzImpl;
        try {

            clazzImpl = Class.forName(clazz.getName().concat(SUFIX_DEFAULT_IMPL));

        } catch (ClassNotFoundException e) {

            throw new EnvironmentException(Errors.NO_CMPT_REGISTERED.arguments(clazz.getName()).throwable(e));
        }

        return clazzImpl;
    }

    /**
     * Find component interface.
     *
     * @param clazz
     *            the clazz
     * @return the class
     */
    protected static Optional<Class<?>> findComponentInterface(Class<?> clazz) {
        Class<?>[] interfaces = clazz.getInterfaces();
        for (Class<?> candidate : interfaces) {
            if (candidate.getAnnotation(Component.class) != null) {
                return Optional.of(candidate);
            }
        }

        return Optional.empty();
    }

    /**
     * Instatiate new class.
     *
     * @param <T>
     *            the generic type
     * @param clazz
     *            the clazz
     * @return the t
     * @throws EnvironmentException
     *             the environment exception
     */
    protected static <T> T instatiate(Class<T> clazz) throws EnvironmentException {

        try {

            T result = clazz.newInstance();

            if (containsInterfaceClass(clazz, ParameterizedTypeRetention.class)) {

                Type[] types = ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments();
                ((ParameterizedTypeRetention) result).setTypes(types);
            }

            ContainerUtils.processInjection(result);
            return result;

        } catch (InstantiationException | IllegalAccessException e) {

            throw new EnvironmentException(Errors.FAILED_INSTANTIATION.throwable(e));
        }
    }

    /**
     * Checks if is available for register.
     *
     * @param clazzImpl
     *            the clazz impl
     * @return true, if is available for register
     */
    protected static boolean isAvailableForRegister(Class<?> clazzImpl) {

        Optional<Class<?>> optionalClazzInterface = findComponentInterface(clazzImpl);

        if (!optionalClazzInterface.isPresent() || !optionalClazzInterface.get().isAnnotationPresent(Component.class)) {

            return false;
        }

        return true;
    }

    /**
     * Checks if is available for store instantiate.
     *
     * @param clazzImpl
     *            the clazz impl
     * @return true, if is available for store instantiate
     */
    protected static boolean isValidComponent(Class<?> clazzImpl) {

        Optional<Class<?>> optionalClazzInterface = findComponentInterface(clazzImpl);
        if (!optionalClazzInterface.isPresent() || !isAvailableForRegister(clazzImpl)) {

            return false;
        }
        return true;
    }

    /**
     * Checks if is valid resource.
     *
     * @param clazzImpl
     *            the clazz impl
     * @return true, if is valid resource
     */
    protected static boolean isValidResource(Class<?> clazzImpl) {

        return clazzImpl.isAnnotationPresent(Resource.class);
    }

    /**
     * Perform component scanner.
     *
     * @param componentScanner
     *            the component scanner
     * @throws EnvironmentException
     *             the environment exception
     */
    protected static void performComponentScanner(String componentScanner) throws EnvironmentException {
        // Validate if component contain all scan quote.
        if (!componentScanner.endsWith(ALL_SCAN_QUOTE)) {
            try {

                Class<?> clazz = Class.forName(componentScanner);
                registryComponent(clazz);

                return;

            } catch (ClassNotFoundException e) {

                throw new EnvironmentException(Errors.NO_CMPT_REGISTERED.throwable(e));
            }
        }

        scanAndExecute(componentScanner, clazzName -> {
            try {

                Class<?> clazz = Class.forName(clazzName);

                if (isAvailableForRegister(clazz)) {
                    registryComponent(clazz);
                }

            } catch (Exception e) {

                System.err.println(e);
            }
        });
    }

    /**
     * Scan and execute.
     *
     * @param packageConvetion
     *            the package convetion
     * @param perform
     *            the perform
     */
    protected static void scanAndExecute(String packageConvetion, Consumer<String> perform) {
        String packageForScan = packageConvetion;
        if (packageForScan.endsWith(".*")) {
            packageForScan = packageForScan.substring(0, packageForScan.length() - 2);
        }

        new FastClasspathScanner(packageForScan).scan().getNamesOfAllClasses().forEach(perform::accept);
    }

    /**
     * Sets the field.
     *
     * @param result
     *            the result
     * @param field
     *            the field
     * @throws IllegalAccessException
     *             the illegal access exception
     * @throws ClassNotFoundException
     *             the class not found exception
     */
    protected static void setField(Object result, Field field) throws IllegalAccessException, ClassNotFoundException {

        List<InjectorStrategy> injectors = collectInjectors(field);
        if (injectors.isEmpty()) {

            return;
        }

        if (injectors.size() > 1) {

            System.err.print(String.format("More than one Injector founded for field [%s] on [%s] class. ", field.getName(), result.getClass().getName()));
        }

        InjectorStrategy injector = injectors.get(0);
        injector.inject(result, field);
    }
}