package com.kp.cms.to.admin;

import java.io.Serializable;

public class AppStatusTO implements Serializable{
	private int id;
	 private String applicationStatus;
	 private String shortName;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getApplicationStatus() {
		return applicationStatus;
	}
	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	 
}
