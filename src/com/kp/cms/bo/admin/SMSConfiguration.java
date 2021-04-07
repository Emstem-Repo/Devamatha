package com.kp.cms.bo.admin;

import java.io.Serializable;

public class SMSConfiguration implements Serializable {
	
	private int id;
	private String senderName;
	private String senderNumber;
	
	public SMSConfiguration() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public SMSConfiguration(int id, String senderName, String senderNumber) {
		super();
		this.id = id;
		this.senderName = senderName;
		this.senderNumber = senderNumber;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public String getSenderNumber() {
		return senderNumber;
	}
	public void setSenderNumber(String senderNumber) {
		this.senderNumber = senderNumber;
	}
	
}
