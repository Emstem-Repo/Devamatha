package com.kp.cms.exceptions;

/**
 * This exception class will be used while generating the fee bill. This will be
 * thrown if any problem occurs while generating bill.
 * 
 */
public class BillGenerationException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public BillGenerationException() {

	}

	public BillGenerationException(String msg) {
		super(msg);
	}
}