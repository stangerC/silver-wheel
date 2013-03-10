package com.silver.wheel.common.exception;

/**
 *
 * @author Liaojian
 */
public enum DefaultErrorCode implements ErrorCode{

    DEFAULT_CODE(0);
    
    private int codeNumber;
    
    private DefaultErrorCode(int codeNumber) {
        this.codeNumber = codeNumber;
    }

    public int getCodeNumber() {
        return codeNumber;
    }    
}
