package com.silver.wheel.common.exception;

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
public class CodeRuntimeException extends RuntimeException {
	/**
	 * 错误代码
	 */
	private ErrorCode errorCode;        
	/**
	 * 默认构造函数，ErrorCode使用默认的ErrorCode 
	 */
	public CodeRuntimeException() {	
            this.errorCode = DefaultErrorCode.DEFAULT_CODE;
	}
        
        public CodeRuntimeException(ErrorCode errorCode) {
            this.errorCode = errorCode;
        }
        
	/**
	 * 需要异常信息参数的构造函数
	 * @param message
	 * 	异常信息
	 */
	public CodeRuntimeException(String message) {
		super(message);
                this.errorCode = DefaultErrorCode.DEFAULT_CODE;
	}
	/**
	 * 带有异常信息、原因异常的构造函数
	 * @param message
	 * 		异常的错误代码
	 * @param cause
	 * 		原因异常
	 */
	public CodeRuntimeException(String message, Throwable cause) {
		super(message, cause);	
                this.errorCode = DefaultErrorCode.DEFAULT_CODE;
	}
	/**
	 * 需要原因异常的构造函数
	 * @param cause
	 * 		原因异常
	 */
	public CodeRuntimeException(Throwable cause) {
		super(cause);
                this.errorCode = DefaultErrorCode.DEFAULT_CODE;
	}                	

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode code) {
        this.errorCode = code;
    }                
}
