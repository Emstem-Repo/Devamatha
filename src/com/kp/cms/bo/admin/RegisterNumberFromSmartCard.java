package com.kp.cms.bo.admin;

import java.util.Date;

public class RegisterNumberFromSmartCard implements java.io.Serializable {

	private int id;
	private String registerNumber;
	private String ipAddress;
	
	public RegisterNumberFromSmartCard() {
	}

	public RegisterNumberFromSmartCard(int id) {
		this.id = id;
	}

	public RegisterNumberFromSmartCard(int id, String createdBy,
			String modifiedBy, Student student, Status status,
			String registerNumber, Date createdDate, Date lastModifiedDate) {
		this.id = id;
		this.registerNumber = registerNumber;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRegisterNumber() {
		return this.registerNumber;
	}

	public void setRegisterNumber(String registerNumber) {
		this.registerNumber = registerNumber;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

}

