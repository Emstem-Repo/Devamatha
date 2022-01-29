package com.kp.cms.to.employee;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ExceptionTypeTO implements Serializable{
	private int id;
	private String exceptionType;
	private String exceptionShortName;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getExceptionType() {
		return exceptionType;
	}
	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}
	public String getExceptionShortName() {
		return exceptionShortName;
	}
	public void setExceptionShortName(String exceptionShortName) {
		this.exceptionShortName = exceptionShortName;
	}
	

}
