package com.silver.wheel.common;

import java.io.File;

/**
 *
 * @author Liaojian
 */
public abstract class Validation {
    public static <T> T checkNotNull(T object) {
        return checkNotNull(object, null);
    }
    
    public static <T> T checkNotNull(T object, String message) {
        if(object == null) {
            if(message != null) {
                throw new NullPointerException(message);
            }            
            throw new NullPointerException();            
        }
        return object;
    }
    
    public static File checkFileNotExists(File file, String message) {        
        if(!file.exists()) {
            throw new IllegalArgumentException(message);
        }
        
        return file;
    }
}
