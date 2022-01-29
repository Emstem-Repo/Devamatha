package com.kp.cms.bo.sap;

import java.util.Date;

import com.kp.cms.bo.admin.Student;


public class SapRegistration implements java.io.Serializable{
	
	private int id;
	private Student stdId;
	private Date registeredDate;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private SapKeysBo sapKeyId;

	public SapRegistration() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public SapRegistration(int id, Student stdId, Date registeredDate,
			String createdBy, Date createdDate, String modifiedBy,
			Date lastModifiedDate, Boolean isActive,SapKeysBo sapKeyId) {
		super();
		this.id = id;
		this.stdId = stdId;
		this.registeredDate = registeredDate;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.sapKeyId=sapKeyId;
	}
	
	
	public SapKeysBo getSapKeyId() {
		return sapKeyId;
	}

	public void setSapKeyId(SapKeysBo sapKeyId) {
		this.sapKeyId = sapKeyId;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Student getStdId() {
		return stdId;
	}
	public void setStdId(Student stdId) {
		this.stdId = stdId;
	}
	public Date getRegisteredDate() {
		return registeredDate;
	}
	public void setRegisteredDate(Date registeredDate) {
		this.registeredDate = registeredDate;
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
