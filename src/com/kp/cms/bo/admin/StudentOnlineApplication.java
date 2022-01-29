package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.util.Date;

public class StudentOnlineApplication implements Serializable{
	
	private int id;
	private String emailId;
	private String mobileCode;
	private String mobileNo;
	private Date dateOfBirth;
	private String uniqueId;
	private Integer year;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private String name;
	private String programTypeId;
	private String categoryId;
	private String gender;
	private String subReligionId;
	private String challanNumber;
	private boolean isAided;
	private boolean mngQuota;
	private String isCommuQuota;
	public StudentOnlineApplication() {
		super();
	}
	public StudentOnlineApplication(int id, String emailId, String mobileCode,
			String mobileNo, Date dateOfBirth, String uniqueId, Integer year,
			String createdBy, Date createdDate, String modifiedBy,
			Date lastModifiedDate, Boolean isActive, String name,
			String programTypeId, String categoryId, String gender,
			String subReligionId, String challanNumber, boolean isAided, boolean mngQuota) {
		super();
		this.id = id;
		this.emailId = emailId;
		this.mobileCode = mobileCode;
		this.mobileNo = mobileNo;
		this.dateOfBirth = dateOfBirth;
		this.uniqueId = uniqueId;
		this.year = year;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.name = name;
		this.programTypeId = programTypeId;
		this.categoryId = categoryId;
		this.gender = gender;
		this.subReligionId = subReligionId;
		this.challanNumber = challanNumber;
		this.isAided = isAided;
		this.mngQuota = mngQuota;
	}
	/* setters getter methods*/
	public int getId() {
		return id;
		
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
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
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProgramTypeId() {
		return programTypeId;
	}
	public void setProgramTypeId(String programTypeId) {
		this.programTypeId = programTypeId;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getMobileCode() {
		return mobileCode;
	}
	public void setMobileCode(String mobileCode) {
		this.mobileCode = mobileCode;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getSubReligionId() {
		return subReligionId;
	}
	public void setSubReligionId(String subReligionId) {
		this.subReligionId = subReligionId;
	}
	public String getChallanNumber() {
		return challanNumber;
	}
	public void setChallanNumber(String challanNumber) {
		this.challanNumber = challanNumber;
	}
	public boolean getIsAided() {
		return isAided;
	}
	public void setIsAided(boolean isAided) {
		this.isAided = isAided;
	}
	public boolean isMngQuota() {
		return mngQuota;
	}
	public void setMngQuota(boolean mngQuota) {
		this.mngQuota = mngQuota;
	}
	public String getIsCommuQuota() {
		return isCommuQuota;
	}
	public void setIsCommuQuota(String isCommuQuota) {
		this.isCommuQuota = isCommuQuota;
	}
	public void setAided(boolean isAided) {
		this.isAided = isAided;
	}
}
