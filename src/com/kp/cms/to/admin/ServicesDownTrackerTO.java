package com.kp.cms.to.admin;

import java.io.Serializable;

public class ServicesDownTrackerTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String serviceName;
	private String date;
	private String downFrom;
	private String downTill;
	private String remarks;
	private int id;
	
	
	
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDownFrom() {
		return downFrom;
	}
	public void setDownFrom(String downFrom) {
		this.downFrom = downFrom;
	}
	public String getDownTill() {
		return downTill;
	}
	public void setDownTill(String downTill) {
		this.downTill = downTill;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	
}
