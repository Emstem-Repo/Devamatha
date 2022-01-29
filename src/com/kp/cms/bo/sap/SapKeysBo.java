package com.kp.cms.bo.sap;

import java.util.Date;

import com.kp.cms.bo.admin.EmployeeWorkLocationBO;

public class SapKeysBo implements java.io.Serializable{
	
	private int id;
	private String key;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private EmployeeWorkLocationBO workLocationId;

	public SapKeysBo() {
		
	}
	public SapKeysBo(int id,String key, String createdBy, Date createdDate, String modifiedBy,
			Date lastModifiedDate, Boolean isActive,EmployeeWorkLocationBO workLocationId) {
		this.id = id;
		this.key = key;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.workLocationId = workLocationId;
	}
	
	public EmployeeWorkLocationBO getWorkLocationId() {
		return workLocationId;
	}
	public void setWorkLocationId(EmployeeWorkLocationBO workLocationId) {
		this.workLocationId = workLocationId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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
