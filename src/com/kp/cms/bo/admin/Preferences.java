package com.kp.cms.bo.admin;

// Generated Jan 22, 2009 3:35:47 PM by Hibernate Tools 3.2.0.b9

import java.util.Date;

/**
 * Preferences generated by hbm2java
 */
public class Preferences implements java.io.Serializable {

	private int id;
	private String createdBy;;
	private String modifiedBy;
	private Course courseByCourseId;
	private Course courseByPrefCourseId;
	private Date createdDate;
	private Date lastModifiedDate;
	private Boolean isActive;
	

	public Preferences() {
	}

	public Preferences(int id) {
		this.id = id;
	}

	public Preferences(int id, String createdBy,
			String modifiedBy, Course courseByCourseId,
			Course courseByPrefCourseId, Date createdDate, Date lastModifiedDate) {
		this.id = id;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.courseByCourseId = courseByCourseId;
		this.courseByPrefCourseId = courseByPrefCourseId;
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

	public Course getCourseByCourseId() {
		return this.courseByCourseId;
	}

	public void setCourseByCourseId(Course courseByCourseId) {
		this.courseByCourseId = courseByCourseId;
	}

	public Course getCourseByPrefCourseId() {
		return this.courseByPrefCourseId;
	}

	public void setCourseByPrefCourseId(Course courseByPrefCourseId) {
		this.courseByPrefCourseId = courseByPrefCourseId;
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
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	
}
