package com.ps.cff.exception;

/**
 * 
 * @author Hemant
 *
 */
public class UserNotAuthorizeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserNotAuthorizeException() {

	}

	public UserNotAuthorizeException(String message) {
		super(message);
	}
}
