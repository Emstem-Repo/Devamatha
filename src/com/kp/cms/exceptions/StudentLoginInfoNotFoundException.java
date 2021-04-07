package com.kp.cms.exceptions;

public class StudentLoginInfoNotFoundException extends BusinessException {
	private static final long serialVersionUID = 1L;

	public StudentLoginInfoNotFoundException() {

	}

	public StudentLoginInfoNotFoundException(String msg) {
		super(msg);
	}
}
