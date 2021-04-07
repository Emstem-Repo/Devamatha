package com.kp.cms.exceptions;

/**
 * This Exception class is used in handling Reactivation of records This
 * Exception will be throws when we are trying in insert the data it is already
 * in the deleted state.
 * 
 */
public class ReActivateException extends BusinessException {

	private static final long serialVersionUID = 1L;
	int id = 0;

	public ReActivateException() {

	}

	public ReActivateException(int id) {
		this.id = id;
	}

	public ReActivateException(String msg) {
		super(msg);
	}
	
	public int getID(){
		return id;
	}
}