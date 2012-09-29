/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silver.wheel.io;

import java.io.File;

/**
 *
 * @author liaojian
 */
public abstract class FileDirectoryAction<T> implements Action<File, T> {

    @Override
    public void process(File file) {
        if (file != null && file.exists()) {
            if (file.isFile()) {
                processFile(file);
            } else {
                processDirecotry(file);
            }
        }
    }

    protected abstract void processFile(File file);

    protected abstract void processDirecotry(File file);

    public T getResult() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
