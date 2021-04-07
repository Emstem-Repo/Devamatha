package com.kp.cms.to.admin;

import java.util.Date;

import com.kp.cms.bo.admin.EmpSkills;

public class EmpSkillsTO {
	private String id;
	private String employeeId;
	private String name;
	private String yearOfExperience;
	private String comments;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private boolean isActive;
	private boolean originalPresent;
	private EmpSkills originalSkill;
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getYearOfExperience() {
		return yearOfExperience;
	}
	public void setYearOfExperience(String yearOfExperience) {
		this.yearOfExperience = yearOfExperience;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
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
	public EmpSkills getOriginalSkill() {
		return originalSkill;
	}
	public void setOriginalSkill(EmpSkills originalSkill) {
		this.originalSkill = originalSkill;
	}
	
	
}
