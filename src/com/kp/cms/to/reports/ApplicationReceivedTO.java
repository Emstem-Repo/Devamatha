package com.kp.cms.to.reports;

import java.io.Serializable;

public class ApplicationReceivedTO implements Serializable{

	private String applicationNo;
	private String name;
	private String firstPreference;
	private String secondPreference;
	private String thirdPreference;
	
	public String getApplicationNo() {
		return applicationNo;
	}
	public String getName() {
		return name;
	}
	public String getFirstPreference() {
		return firstPreference;
	}
	public String getSecondPreference() {
		return secondPreference;
	}
	public String getThirdPreference() {
		return thirdPreference;
	}
	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setFirstPreference(String firstPreference) {
		this.firstPreference = firstPreference;
	}
	public void setSecondPreference(String secondPreference) {
		this.secondPreference = secondPreference;
	}
	public void setThirdPreference(String thirdPreference) {
		this.thirdPreference = thirdPreference;
	}
}
