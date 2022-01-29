package com.kp.cms.bo.sap;

import java.util.Date;
import java.util.Set;

import com.kp.cms.bo.admin.EmployeeWorkLocationBO;

public class SapVenue implements java.io.Serializable{
	
	private int id;
	private EmployeeWorkLocationBO workLocationId;
	private String venueName;
	private int capacity;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	
	public SapVenue(){
	}
	
	public SapVenue(int id, EmployeeWorkLocationBO workLocationId, String venueName, int capacity, 
			String createdBy, Date createdDate, String modifiedBy, Date lastModifiedDate, Boolean isActive) {
		super();
		this.id = id;
		this.workLocationId = workLocationId;
		this.venueName = venueName;
		this.capacity = capacity;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public EmployeeWorkLocationBO getWorkLocationId() {
		return workLocationId;
	}

	public void setWorkLocationId(EmployeeWorkLocationBO workLocationId) {
		this.workLocationId = workLocationId;
	}

	public String getVenueName() {
		return venueName;
	}

	public void setVenueName(String venueName) {
		this.venueName = venueName;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
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
