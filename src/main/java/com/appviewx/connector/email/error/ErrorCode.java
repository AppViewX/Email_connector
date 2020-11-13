package com.appviewx.connector.email.error;

/**
 * The Enum ErrorCode.
 * 
 * @author sakthimurugan.m ErrorCodes is an interface used to define the Error
 *         codes and constants for Database related operation.
 */
public enum ErrorCode {

	/**
	 * Error code for empty streams.
	 */
	DATASOURCE_EMPTY_ERROR(508, "Stream is empty");

	/**
	 * Status code defines the response status of HTTP request.
	 */
	private int httpStatusCode;

	/**
	 * AppViewX defined code to represent an error.
	 */
	private String code;

	/**
	 * Code summary.
	 */
	private String codeDescription;

	/**
	 * Actual Code value.
	 */
	private String codeValue;

	/**
	 * Instantiates a new error code.
	 * 
	 * @param httpStatusCode
	 *            - HTTP Status Code
	 * @param codeDescription
	 *            - Error Code Description
	 */
	private ErrorCode(int httpStatusCode, String codeDescription) {
		this.httpStatusCode = httpStatusCode;
		this.code = this.name();
		this.codeDescription = codeDescription;
	}

	/**
	 * Instantiates a new error code.
	 * 
	 * @param httpStatusCode
	 *            - HTTP Status Code
	 * @param codeValue
	 *            the code value
	 * @param codeDescription
	 *            - Error Code Description
	 */
	private ErrorCode(int httpStatusCode, String codeValue, String codeDescription) {
		this.httpStatusCode = httpStatusCode;
		this.code = this.name();
		this.codeDescription = codeDescription;
		this.codeValue = codeValue;
	}

	/**
	 * Gets the http status code.
	 * 
	 * @return the httpStatusCode
	 */
	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	/**
	 * Gets the code.
	 * 
	 * @return the errorCode
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Gets the code value.
	 * 
	 * @return the errorCode Value
	 */
	public String getCodeValue() {
		return codeValue;
	}

	/**
	 * Gets the code description.
	 * 
	 * @return the codeDescription
	 */
	public String getCodeDescription() {
		return codeDescription;
	}

	/**
	 * Update code description.
	 * 
	 * @param codeDescriptionParam
	 *            code Desc
	 */
	public void updateCodeDescription(final String codeDescriptionParam) {
		this.codeDescription = codeDescriptionParam;
	}

	/**
	 * (non-Javadoc).
	 * 
	 * @return the string.
	 * @see java.lang.Enum#toString().
	 */
	@Override
	public String toString() {
		return "Error Code: " + this.codeValue + " - " + this.code + " : " + this.codeDescription;
	}
}