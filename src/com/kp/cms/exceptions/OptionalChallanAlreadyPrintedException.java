package com.kp.cms.exceptions;

/**
 * This Exception class is used in printing challan. This Exception will be
 * throws when challan already printed.
 * 
 */
public class OptionalChallanAlreadyPrintedException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public OptionalChallanAlreadyPrintedException() {

	}

	public OptionalChallanAlreadyPrintedException(String msg) {
		super(msg);
	}
}