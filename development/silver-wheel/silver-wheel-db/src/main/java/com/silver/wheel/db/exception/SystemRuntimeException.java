package com.silver.wheel.db.exception;

/**
 *
 * @author Liaojian
 */
public class SystemRuntimeException extends RuntimeException{
    public SystemRuntimeException(String message) {
        super(message);
    }
    
    public SystemRuntimeException(Throwable cause) {
        super(cause);
    }
    
    public SystemRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
