package com.kp.cms.exceptions;

/**
 * This Exception class is used in printing challan. This Exception will be
 * throws when challan already printed.
 * 
 */
public class ChallanAlreadyPrintedException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public ChallanAlreadyPrintedException() {

	}

	public ChallanAlreadyPrintedException(String msg) {
		super(msg);
	}
}