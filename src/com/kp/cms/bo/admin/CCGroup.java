package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.util.Date;

public class CCGroup implements Serializable {


	private int id;
	private String createdBy;;
	private String modifiedBy;
	private String name;
	private Date createdDate;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Date startDate;
	private Date endDate;
	
	public CCGroup() {
		super();
	}
	
	public CCGroup(int id, String createdBy, String modifiedBy, String name,
			Date createdDate, Date lastModifiedDate,Boolean isActive,Date startDate, Date endDate) {
		super();
		this.id = id;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.name = name;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive=isActive;
		this.startDate=startDate;
		this.endDate=endDate;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}