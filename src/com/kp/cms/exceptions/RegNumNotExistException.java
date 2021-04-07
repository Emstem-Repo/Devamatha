package com.kp.cms.exceptions;

public class RegNumNotExistException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public RegNumNotExistException() {

	}

	public RegNumNotExistException(String msg) {
		super(msg);
	}
}