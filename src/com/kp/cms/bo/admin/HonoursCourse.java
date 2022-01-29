package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.util.Date;

public class HonoursCourse implements Serializable{
	private int id;
	private Course honoursCourse;
	private Course eligibleCourse;
	private Date createdDate;
	private String createdBy;
	private Date lastModifiedDate;
	private String modifiedBy;
	private Boolean isActive;
	public HonoursCourse(int id, Course honoursCourse, Course eligibleCourse,
			Date createdDate, String createdBy, Date lastModifiedDate,
			String modifiedBy, Boolean isActive) {
		super();
		this.id = id;
		this.honoursCourse = honoursCourse;
		this.eligibleCourse = eligibleCourse;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.lastModifiedDate = lastModifiedDate;
		this.modifiedBy = modifiedBy;
		this.isActive = isActive;
	}
	
	public HonoursCourse() {
		super();
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Course getHonoursCourse() {
		return honoursCourse;
	}
	public void setHonoursCourse(Course honoursCourse) {
		this.honoursCourse = honoursCourse;
	}
	public Course getEligibleCourse() {
		return eligibleCourse;
	}
	public void setEligibleCourse(Course eligibleCourse) {
		this.eligibleCourse = eligibleCourse;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
}
