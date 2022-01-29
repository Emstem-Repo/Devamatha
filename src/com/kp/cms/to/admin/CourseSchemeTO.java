package com.kp.cms.to.admin;

import java.io.Serializable;

public class CourseSchemeTO implements Serializable {
	private int courseSchemeId;
	private String courseSchemeName;
	private String isActive;
	private String createdBy;;
	private String modifiedBy;
	private String createdDate;
	private String lastModifiedDate;
	
	
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public int getCourseSchemeId() {
		return courseSchemeId;
	}

	public void setCourseSchemeId(int courseSchemeId) {
		this.courseSchemeId = courseSchemeId;
	}

	public String getCourseSchemeName() {
		return courseSchemeName;
	}

	public void setCourseSchemeName(String courseSchemeName) {
		this.courseSchemeName = courseSchemeName;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public String getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

}
