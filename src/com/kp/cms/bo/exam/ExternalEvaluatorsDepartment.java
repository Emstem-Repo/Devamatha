package com.kp.cms.bo.exam;

import java.util.Date;

import com.kp.cms.bo.admin.Department;

public class ExternalEvaluatorsDepartment {
	private int id;
	private ExamValuators evaluators;
	private Department department;
	private String createdBy;
	private String modifiedBy;
	private Boolean isActive;
	private Date createdDate;
	private Date lastModifiedDate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ExamValuators getEvaluators() {
		return evaluators;
	}
	public void setEvaluators(ExamValuators evaluators) {
		this.evaluators = evaluators;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
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
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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
