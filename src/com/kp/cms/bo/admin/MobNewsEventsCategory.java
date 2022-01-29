package com.kp.cms.bo.admin;

import java.util.Date;
import java.util.Set;

public class MobNewsEventsCategory implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String createdBy;;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private Boolean isActive;
	private String category;

	public MobNewsEventsCategory() {
		super();
	}

	public MobNewsEventsCategory(int id, String createdBy,
			String modifiedBy, String category,
			Date createdDate,
			Date lastModifiedDate, Boolean isActive)
	
	{
		this.id=id;
		this.createdBy=createdBy;
		this.modifiedBy=modifiedBy;
		this.createdDate=createdDate;
		this.lastModifiedDate=lastModifiedDate;
		this.category=category;
		this.isActive=isActive;
	}
	
	public MobNewsEventsCategory(int id, Boolean isActive) {
		this.id = id;
		this.isActive = isActive;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	
}
