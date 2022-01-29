package com.kp.cms.exceptions;

/**
 * This Exception class is used when subjects groups are not assigned to a
 * students.
 * 
 */
public class SubjectGroupNotDefinedException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public SubjectGroupNotDefinedException() {

	}

	public SubjectGroupNotDefinedException(String msg) {
		super(msg);
	}
}