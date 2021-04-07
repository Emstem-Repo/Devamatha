package com.kp.cms.exceptions;

/**
 * 
 * 
 * Class implemented to IDENTIFY BUSINESS RELATED EXCEPTION
 * 
 */
public class BusinessException extends Exception {

	private static final long serialVersionUID = 1L;

	public BusinessException() {

	}

	public BusinessException(Exception e) {
		super(e);
	}

	public BusinessException(String str) {
		super(str);
	}
}