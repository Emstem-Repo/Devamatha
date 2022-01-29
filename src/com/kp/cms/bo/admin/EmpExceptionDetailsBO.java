package com.kp.cms.bo.admin;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class EmpExceptionDetailsBO implements java.io.Serializable {

	private int id;
	private EmpExceptionTypeBO exceptionTypeBO;

	private Date staffStartDate;
	private boolean staffStartDateAm;
	private boolean staffStartDatePm;
	private Date staffEndDate;
	private boolean staffEndDateAm;
	private boolean staffEndDatePm;
	private String remarks;

	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Employee employee;
	private Set<EmpExceptionDetailsDates> empExceptionDetailsDates = new HashSet<EmpExceptionDetailsDates>(0);

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public EmpExceptionTypeBO getExceptionTypeBO() {
		return exceptionTypeBO;
	}

	public void setExceptionTypeBO(EmpExceptionTypeBO exceptionTypeBO) {
		this.exceptionTypeBO = exceptionTypeBO;
	}

	public Date getStaffStartDate() {
		return staffStartDate;
	}

	public void setStaffStartDate(Date staffStartDate) {
		this.staffStartDate = staffStartDate;
	}

	public boolean getStaffStartDateAm() {
		return staffStartDateAm;
	}

	public void setStaffStartDateAm(boolean staffStartDateAm) {
		this.staffStartDateAm = staffStartDateAm;
	}

	public boolean getStaffStartDatePm() {
		return staffStartDatePm;
	}

	public void setStaffStartDatePm(boolean staffStartDatePm) {
		this.staffStartDatePm = staffStartDatePm;
	}

	public Date getStaffEndDate() {
		return staffEndDate;
	}

	public void setStaffEndDate(Date staffEndDate) {
		this.staffEndDate = staffEndDate;
	}

	public boolean getStaffEndDateAm() {
		return staffEndDateAm;
	}

	public void setStaffEndDateAm(boolean staffEndDateAm) {
		this.staffEndDateAm = staffEndDateAm;
	}

	public boolean getStaffEndDatePm() {
		return staffEndDatePm;
	}

	public void setStaffEndDatePm(boolean staffEndDatePm) {
		this.staffEndDatePm = staffEndDatePm;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Set<EmpExceptionDetailsDates> getEmpExceptionDetailsDates() {
		return empExceptionDetailsDates;
	}

	public void setEmpExceptionDetailsDates(
			Set<EmpExceptionDetailsDates> empExceptionDetailsDates) {
		this.empExceptionDetailsDates = empExceptionDetailsDates;
	}

}
