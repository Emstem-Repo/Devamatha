package com.kp.cms.exceptions;

/**
 * This Exception class is used when No data found This Exception will be throws
 * when any search will give 0 records.
 * 
 */
public class DataNotFoundException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public DataNotFoundException() {

	}

	public DataNotFoundException(String msg) {
		super(msg);
	}
}