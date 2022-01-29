package com.kp.cms.bo.phd;

/**
 * Dec 29, 2009
 * Created By 9Elements Team
 */
import java.io.Serializable;
import java.util.Date;

public class PhdGuideDetailsBO implements Serializable {
    
	private int id;
	private String name;
	private String empanelmentNo;
	private String phoneNo;
	private String mobileNo;
	private String email;
	private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private String addressLine4;
	private String pinCode;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;

	public PhdGuideDetailsBO() {
	}

	public PhdGuideDetailsBO(int id,String name,String empanelmentNo,String phoneNo,String mobileNo,
			String email,String addressLine1,String addressLine2,String addressLine3,String addressLine4,
			String pinCode,Boolean isActive,String createdBy,String modifiedBy,Date createdDate,Date lastModifiedDate) 
	{
		
		this.id = id;
		this.name=name;
		this.empanelmentNo=empanelmentNo;
		this.phoneNo=phoneNo;
		this.mobileNo=mobileNo;
		this.email=email;
		this.addressLine1=addressLine1;
		this.addressLine2=addressLine2;
		this.addressLine3=addressLine3;
		this.addressLine4=addressLine4;
		this.pinCode=pinCode;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
	}

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

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

}
