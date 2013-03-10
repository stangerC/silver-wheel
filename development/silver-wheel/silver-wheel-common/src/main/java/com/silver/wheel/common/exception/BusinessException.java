package com.silver.wheel.common.exception;

/**
 * 业务异常基类。继承自Exception，是一个CheckedException，表明这个异常必须被捕捉或再次抛出。
 * 可以处理的异常可以由采用这个异常进行再次封装，或者实现为本异常的一个子类。
 *
 * @author liaojian
 */
@SuppressWarnings("serial")
public class BusinessException extends Exception {
	/**
	 * 错误代码
	 */
	private ErrorCode errorCode;		
	/**
	 * 默认构造函数 
	 */
	public BusinessException() {
	}
	/**
	 * 需要错误代码参数的构造函数
	 * @param errorCode
	 * 		异常的错误代码
	 */
	public BusinessException(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}
	/**
	 * 带有错误代码、原因异常的构造函数
	 * @param errorCode
	 * 		异常的错误代码
	 * @param cause
	 * 		原因异常
	 */
	public BusinessException(ErrorCode errorCode, Throwable cause) {
		super(cause);
		this.errorCode = errorCode;		
	}
	
	/**
	 * 需要原因异常的构造函数
	 * @param cause
	 * 		原因异常
	 */
	public BusinessException(Throwable cause) {
		super(cause);
	}
		
	public ErrorCode getErrorCode() {		
		return errorCode;
	}
	
	public void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}	
}
