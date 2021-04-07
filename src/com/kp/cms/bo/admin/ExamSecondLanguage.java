package com.kp.cms.bo.admin;

import java.util.Date;

public class ExamSecondLanguage {
	private int id;
	private String createdBy;;
	private String modifiedBy;
	private String name;
	private Date createdDate;
	private Date lastModifiedDate;
	private Boolean isActive;
	
	public ExamSecondLanguage() {
	}

	public ExamSecondLanguage(int id) {
		this.id = id;
	}

	public ExamSecondLanguage(int id, String createdBy,
			String modifiedBy, String name, Date createdDate,
			Date lastModifiedDate) {
		this.id = id;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.name = name;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy)  {
		this.createdBy = createdBy;
	}

	public String getModifiedBy()  {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModifiedDate() {
		return this.lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

}