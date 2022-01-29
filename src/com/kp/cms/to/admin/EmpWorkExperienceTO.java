package com.kp.cms.to.admin;

import java.util.Date;

import com.kp.cms.bo.admin.EmpWorkExperience;

public class EmpWorkExperienceTO {
	private String id;
	private String employeeId;
	private String jobTitle;
	private String employer;
	private String comments;
	private String startDate;
	private String endDate;
	private boolean internal;
	private boolean tempInternal;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private boolean isActive;
	private boolean originalPresent;
	private EmpWorkExperience originalExperience;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getEmployer() {
		return employer;
	}
	public void setEmployer(String employer) {
		this.employer = employer;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public boolean isInternal() {
		return internal;
	}
	public void setInternal(boolean internal) {
		this.internal = internal;
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
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public boolean isOriginalPresent() {
		return originalPresent;
	}
	public void setOriginalPresent(boolean originalPresent) {
		this.originalPresent = originalPresent;
	}
	public EmpWorkExperience getOriginalExperience() {
		return originalExperience;
	}
	public void setOriginalExperience(EmpWorkExperience originalExperience) {
		this.originalExperience = originalExperience;
	}
	public boolean isTempInternal() {
		return tempInternal;
	}
	public void setTempInternal(boolean tempInternal) {
		this.tempInternal = tempInternal;
	}
	
	
}
