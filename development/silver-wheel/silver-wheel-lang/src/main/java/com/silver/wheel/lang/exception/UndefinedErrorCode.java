package com.silver.wheel.lang.exception;

/**
 *
 * @author Liaojian
 */
public enum UndefinedErrorCode implements ErrorCode{
    
    UNDEFINED(-1);

    private long codeNumber;
    
    private UndefinedErrorCode(long codeNumber) {
        this.codeNumber = codeNumber;
    }
    
    public long getCodeNumber() {
        return codeNumber;
    }
    
}
