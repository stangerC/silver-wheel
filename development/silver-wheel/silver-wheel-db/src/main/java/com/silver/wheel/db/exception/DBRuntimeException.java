package com.silver.wheel.db.exception;

/**
 *
 * @author Liaojian
 */
public class DBRuntimeException extends RuntimeException{
    public DBRuntimeException(String message) {
        super(message);
    }
    
    public DBRuntimeException(Throwable cause) {
        super(cause);
    }
    
    public DBRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
