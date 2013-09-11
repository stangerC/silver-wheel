package com.silver.wheel.lang.exception;

/**
 * 系统运行时异常。继承自RuntimeException，是一个UncheckedException。对于系统底层抛出的、无法挽救的
 * CheckedException异常，如SQLException，IOException，在进行必要的处理（如关闭资源）后，使用本异常
 * 或本异常的子类进行封装，然后抛出。这样在各个调用层次上，无需再进行捕获或显示抛出。可以在一个统一的层次再 
 * 进行处理，比如View层。
 *
 * @author Liaojian
 *
 */
@SuppressWarnings("serial")
public class CodedRuntimeException extends RuntimeException {

    /**
     * 错误代码
     */
    private ErrorCode errorCode;

    /**
     * 使用错误代码创建一个新的异常
     * @param errorCode
     */
    public CodedRuntimeException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * 使用错误代码、异常信息参数创建异常对象
     *
     * @param message 异常信息
     */
    public CodedRuntimeException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * 使用错误代码、异常信息、原因异常创建异常对象
     *
     * @param message 异常的错误代码
     * @param cause 原因异常
     */
    public CodedRuntimeException(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    /**
     * 使用异常信息、原因异常创建异常对象，
     * 未指明错误代码的异常使用未定义错误代码
     *
     * @param cause 原因异常
     */
    public CodedRuntimeException(ErrorCode errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }
    /**
     * 使用原因异常创建异常对象，未指明错误编码的异常使用未定义错误编码
     * @param cause 
     */
    public CodedRuntimeException(Throwable cause) {
        super(cause);
        this.errorCode = UndefinedErrorCode.UNDEFINED;
    }
    /**
     * 使用异常信息创建异常对象，未指明错误编码的异常使用未定义错误编码
     * @param message 
     */
    public CodedRuntimeException(String message) {
        super(message);
        this.errorCode = UndefinedErrorCode.UNDEFINED;
    }
    
    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode code) {
        this.errorCode = code;
    }
}
