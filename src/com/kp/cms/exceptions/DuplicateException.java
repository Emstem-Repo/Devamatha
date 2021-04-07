package com.kp.cms.exceptions;

/**
 * This Exception class is used in handling Duplication of records This
 * Exception will be throws when duplicate record is trying to insert to data
 * base.
 * 
 */
public class DuplicateException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public DuplicateException() {
	}

	public DuplicateException(String msg) {
		super(msg);
	}

	public DuplicateException(Exception e) {
		super(e);
	}
}