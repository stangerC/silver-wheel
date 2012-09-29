/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silver.wheel.io;

/**
 *
 * @author liaojian
 */
public interface Action<E, T> {
    public void process(E e);
    
    public T getResult();
}
