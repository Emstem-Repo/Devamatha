package com.kp.cms.exceptions;

/**
 * This Exception class is used in Fee payment. This Exception will be throws
 * when these is no scheme duration assigned for particular course.
 * 
 */
public class CurriculumSchemeNotFoundException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public CurriculumSchemeNotFoundException() {

	}

	public CurriculumSchemeNotFoundException(String msg) {
		super(msg);
	}
}