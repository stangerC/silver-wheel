package com.silver.wheel.lang;

import java.io.File;

/**
 *
 * @author Liaojian
 */
public abstract class Validation {
    public static final String FILE_NOT_EXISTS_MESSAGE = "file doesn't exists!";
    
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
    
    public static File checkFileNotExists(File file) {
        return checkFileNotExists(file, null);
    }
    
    public static File checkFileNotExists(File file, String message) {            
        if(!file.exists()) {
            if(message != null) {
                throw new IllegalArgumentException(message);
            }
            throw new IllegalArgumentException();
        }
        
        return file;
    }
}
