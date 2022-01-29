package com.kp.cms.to.admin;

import java.util.Date;

import com.kp.cms.bo.admin.EmpEducation;
import com.kp.cms.bo.admin.EmpImmigration;

public class EmpEducationTO {
	private String id;
	private String employee;
	private String empEducationMasterId;
	private String empEducationMasterName;
	private String specialisation;
	private String gpaGrade;
	private String year;
	private String startDate;
	private String endDate;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private boolean isActive;
	private boolean originalPresent;
	private EmpEducation originalEducation;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmployee() {
		return employee;
	}
	public void setEmployee(String employee) {
		this.employee = employee;
	}
	public String getEmpEducationMasterId() {
		return empEducationMasterId;
	}
	public void setEmpEducationMasterId(String empEducationMasterId) {
		this.empEducationMasterId = empEducationMasterId;
	}
	public String getSpecialisation() {
		return specialisation;
	}
	public void setSpecialisation(String specialisation) {
		this.specialisation = specialisation;
	}
	public String getGpaGrade() {
		return gpaGrade;
	}
	public void setGpaGrade(String gpaGrade) {
		this.gpaGrade = gpaGrade;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
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
	public EmpEducation getOriginalEducation() {
		return originalEducation;
	}
	public void setOriginalEducation(EmpEducation originalEducation) {
		this.originalEducation = originalEducation;
	}
	public String getEmpEducationMasterName() {
		return empEducationMasterName;
	}
	public void setEmpEducationMasterName(String empEducationMasterName) {
		this.empEducationMasterName = empEducationMasterName;
	}
	
	
}
