package com.kp.cms.to.phd;

import java.io.Serializable;

public class PhdGuideDetailsTO implements Serializable{
	/**
	 * Dec 23, 2009
	 * Created By 9Elements Team
	 */
	
	private int id;
	private String name;
	private String empanelmentNo;
	private String phoneNo;
	private String mobileNo;
	private String email;
	private String pinCode;
	private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private String addressLine4;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmpanelmentNo() {
		return empanelmentNo;
	}
	public void setEmpanelmentNo(String empanelmentNo) {
		this.empanelmentNo = empanelmentNo;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPinCode() {
		return pinCode;
	}
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getAddressLine2() {
		return addressLine2;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	public String getAddressLine3() {
		return addressLine3;
	}
	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}
	public String getAddressLine4() {
		return addressLine4;
	}
	public void setAddressLine4(String addressLine4) {
		this.addressLine4 = addressLine4;
	}
	
	
}
