package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.util.Date;

public class SmsGatewayCredits implements Serializable {
	
	private int id;
	private String vendorName;
	private Integer creditsLeft;
	private Integer creditsAssigned;
	private Date lastChecked;
	public SmsGatewayCredits() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SmsGatewayCredits(int id, String vendorName, Integer creditsLeft,
			Integer creditsAssigned, Date lastChecked) {
		super();
		this.id = id;
		this.vendorName = vendorName;
		this.creditsLeft = creditsLeft;
		this.creditsAssigned = creditsAssigned;
		this.lastChecked = lastChecked;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public Integer getCreditsLeft() {
		return creditsLeft;
	}
	public void setCreditsLeft(Integer creditsLeft) {
		this.creditsLeft = creditsLeft;
	}
	public Integer getCreditsAssigned() {
		return creditsAssigned;
	}
	public void setCreditsAssigned(Integer creditsAssigned) {
		this.creditsAssigned = creditsAssigned;
	}
	public Date getLastChecked() {
		return lastChecked;
	}
	public void setLastChecked(Date lastChecked) {
		this.lastChecked = lastChecked;
	}
}
