package com.introapp.library.api;

import java.io.Serializable;

/**
 * @author Sachit Wadhawan
 * 
 */
public class APIException extends Exception implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * error code
	 */
	private String code;

	/**
	 * @param code
	 * @param message
	 */
	public APIException(String code, String message) {
		super(message);
		this.code = code;
	}

	/**
	 * @param message
	 */
	public APIException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public APIException(Throwable cause) {
		super("Caused by: " + cause.toString());
	}

	/**
	 * @return
	 */
	public String getCode() {
		return code;
	}
}
