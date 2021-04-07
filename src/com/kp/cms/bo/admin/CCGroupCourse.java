package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.util.Date;

public class CCGroupCourse implements Serializable {
	
	private int id;
	private CCGroup ccGroup;
	private Course course;
	private Boolean isActive;
	private String createdBy;;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	
	public CCGroupCourse() {
		super();
	}
	
	public CCGroupCourse(int id, CCGroup ccGroup, Course course,
			Boolean isActive, String createdBy, String modifiedBy,
			Date createdDate, Date lastModifiedDate) {
		super();
		this.id = id;
		this.ccGroup = ccGroup;
		this.course = course;
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
	public CCGroup getCcGroup() {
		return ccGroup;
	}
	public void setCcGroup(CCGroup ccGroup) {
		this.ccGroup = ccGroup;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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

	
}
