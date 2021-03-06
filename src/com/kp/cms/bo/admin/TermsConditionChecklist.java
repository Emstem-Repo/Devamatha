package com.kp.cms.bo.admin;

// Generated Jul 13, 2009 4:34:25 PM by Hibernate Tools 3.2.0.b9

import java.util.Date;

/**
 * TermsConditionChecklist generated by hbm2java
 */
public class TermsConditionChecklist implements java.io.Serializable {

	private int id;
	private Course course;
	private String checklistDescription;
	private Boolean isMandatory;
	private Boolean isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Integer year;

	public TermsConditionChecklist() {
	}

	public TermsConditionChecklist(int id) {
		this.id = id;
	}

	public TermsConditionChecklist(int id, Course course,
			Boolean isMandatory, Boolean isActive, String createdBy,
			Date createdDate,String checklistDescription, String modifiedBy, Date lastModifiedDate) {
		this.id = id;
		this.course = course;
		this.isMandatory = isMandatory;
		this.checklistDescription = checklistDescription;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Course getCourse() {
		return this.course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
	public Boolean getIsMandatory() {
		return this.isMandatory;
	}

	public void setIsMandatory(Boolean isMandatory) {
		this.isMandatory = isMandatory;
	}
	public String getChecklistDescription() {
		return this.checklistDescription;
	}

	public void setChecklistDescription(String checklistDescription) {
		this.checklistDescription = checklistDescription;
	}
	public Boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getLastModifiedDate() {
		return this.lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

}
