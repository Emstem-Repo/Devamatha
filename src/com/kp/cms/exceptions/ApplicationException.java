package com.kp.cms.exceptions;

/**
 * 
 * 
 * Class implemented to IDENTIFY APPLICATION LEVEL EXCEPTION
 * 
 */
public class ApplicationException extends Exception {

	private static final long serialVersionUID = 1L;

	public ApplicationException() {

	}

	public ApplicationException(Exception e) {
		super(e);
	}
	
	public ApplicationException(String msg) {
		super(msg);
	}
}