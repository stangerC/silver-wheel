package com.silver.wheel.lang.pattern.factory;

/**
 *
 * @author Liaojian
 */
public interface VariableFactory<T, V> {
    public T create(V variable);
}
