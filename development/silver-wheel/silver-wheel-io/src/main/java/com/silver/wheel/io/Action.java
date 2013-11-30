package com.silver.wheel.io;

/**
 *
 * @author Liaojian
 */
public interface Action<E, T> {
    public void process(E e);
    
    public T getResult();
}
