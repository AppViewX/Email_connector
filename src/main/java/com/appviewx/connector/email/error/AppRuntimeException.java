package com.appviewx.connector.email.error;

/**
 * AppRuntimeException class is a parent class used to handle the all types of
 * exceptions and erros.
 * 
 * @author sakthimurugan.m
 * @since 1.0.0
 * @version 1.0.0
 */
public class AppRuntimeException extends RuntimeException {

	/**
	 * Unique Serialization ID for version Control.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Error code is used to identify an error across the application.
	 */
	private ErrorCode errorCode;

	/**
	 * Error message.
	 */
	private String exceptionMessage;

	/**
	 * Error code value.
	 */
	private String errorCodeValue;

	/**
	 * Error trace.
	 */
	private String errorTrace;

	/**
	 * Public constructor with initialization parameters.
	 * 
	 * @param errorMessage
	 *            - Error Message gives description about the error
	 */
	public AppRuntimeException(String errorMessage) {
		super(errorMessage);
		this.exceptionMessage = errorMessage;
	}

	/**
	 * Public constructor with initialization parameters.
	 * 
	 * @param errorCodeValue
	 *            - Error code value
	 * @param errorMessage
	 *            - Error Message
	 * @param errorTrace
	 *            - Error trace
	 */
	public AppRuntimeException(String errorCodeValue, String errorMessage, String errorTrace) {
		super(errorMessage);
		this.errorCodeValue = errorCodeValue;
		this.exceptionMessage = errorMessage;
		this.errorTrace = errorTrace;
	}

	/**
	 * Public constructor with initialization parameters.
	 * 
	 * @param errorCodeValue
	 *            - Error code value
	 * @param errorTrace
	 *            - Error trace
	 */
	public AppRuntimeException(String errorCodeValue, String errorTrace) {
		this.errorCodeValue = errorCodeValue;
		this.errorTrace = errorTrace;
	}

	/**
	 * Public constructor with initialization parameters.
	 * 
	 * @param errorCode
	 *            - Error Code defines the type of the error
	 */
	public AppRuntimeException(ErrorCode errorCode) {
		super(errorCode.getCodeDescription());
		this.errorCode = errorCode;
	}

	/**
	 * Public constructor with initialization parameters.
	 * 
	 * @param errorCode
	 *            - Error Code defines the type of the error
	 * @param errorMessage
	 *            - Error Message gives description about the error
	 */
	public AppRuntimeException(ErrorCode errorCode, String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
	}

	/**
	 * Public constructor with initialization parameters.
	 * 
	 * @param errorCode
	 *            - Error Code defines the type of the error
	 * @param throwable
	 *            - StackTrace
	 */
	public AppRuntimeException(ErrorCode errorCode, Throwable throwable) {
		super(throwable);
		this.errorCode = errorCode;
	}

	/**
	 * Public constructor with initialization parameters.
	 * 
	 * @param errorCode
	 *            - Error Code defines the type of the error
	 * @param exceptionMessage
	 *            - Error Message gives description about the error
	 * @param throwable
	 *            - StackTrace
	 */
	public AppRuntimeException(ErrorCode errorCode, String exceptionMessage, Throwable throwable) {
		super(throwable);
		this.exceptionMessage = exceptionMessage;
		this.errorCode = errorCode;
	}

	/**
	 * @return the errorCode
	 */
	public ErrorCode getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode
	 *            the errorCode to set
	 */
	public void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * Gets the exception message.
	 * 
	 * @return the exception message
	 */
	public String getExceptionMessage() {
		return exceptionMessage;
	}

	/**
	 * Sets the exception message.
	 * 
	 * @param exceptionMessage
	 *            the new exception message
	 */
	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	/**
	 * @return the errorCodeValue
	 */
	public String getErrorCodeValue() {
		return errorCodeValue;
	}

	/**
	 * @param errorCodeValue
	 *            the errorCodeValue to set
	 */
	public void setErrorCodeValue(String errorCodeValue) {
		this.errorCodeValue = errorCodeValue;
	}

	/**
	 * @return the errorTrace
	 */
	public String getErrorTrace() {
		return errorTrace;
	}

	/**
	 * @param errorTrace
	 *            the errorTrace to set
	 */
	public void setErrorTrace(String errorTrace) {
		this.errorTrace = errorTrace;
	}

	@Override
	public String toString() {
		return "AppRuntimeException [errorCode=" + getErrorCode() + ", getExceptionMessage()=" + getExceptionMessage()
				+ ", getMessage()=" + getMessage() + "]";
	}

}
