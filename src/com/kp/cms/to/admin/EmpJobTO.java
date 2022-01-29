package com.kp.cms.to.admin;

import java.util.Date;
import java.util.List;

import com.kp.cms.bo.admin.EmpJob;

public class EmpJobTO {
	private String id;
	private String employeeId;
	private String jobTitle;
	private String jobSpecification;
	private String employmentStatus;
	private String jobDuties;
	private String joiningDate;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private boolean isActive;
	
	private String departmentType;
	private String dateOfRetirement;
	private String basicPay;
	private String grossPay;
	private String dateOfResign;
	private String resignationReason;
	private String dateOfLeaving;
	private String financialAssisstance;
	private EmpJob originalJob;
	private String dateOfRejoin;
	private String employeeType;
	
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
	public String getJobSpecification() {
		return jobSpecification;
	}
	public void setJobSpecification(String jobSpecification) {
		this.jobSpecification = jobSpecification;
	}
	public String getEmploymentStatus() {
		return employmentStatus;
	}
	public void setEmploymentStatus(String employmentStatus) {
		this.employmentStatus = employmentStatus;
	}
	public String getJobDuties() {
		return jobDuties;
	}
	public void setJobDuties(String jobDuties) {
		this.jobDuties = jobDuties;
	}
	public String getJoiningDate() {
		return joiningDate;
	}
	public void setJoiningDate(String joiningDate) {
		this.joiningDate = joiningDate;
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
	public String getEmployeeType() {
		return employeeType;
	}
	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}
	public String getDepartmentType() {
		return departmentType;
	}
	public void setDepartmentType(String departmentType) {
		this.departmentType = departmentType;
	}
	public String getDateOfRetirement() {
		return dateOfRetirement;
	}
	public void setDateOfRetirement(String dateOfRetirement) {
		this.dateOfRetirement = dateOfRetirement;
	}
	public String getBasicPay() {
		return basicPay;
	}
	public void setBasicPay(String basicPay) {
		this.basicPay = basicPay;
	}
	public String getGrossPay() {
		return grossPay;
	}
	public void setGrossPay(String grossPay) {
		this.grossPay = grossPay;
	}
	public String getDateOfResign() {
		return dateOfResign;
	}
	public void setDateOfResign(String dateOfResign) {
		this.dateOfResign = dateOfResign;
	}
	public String getResignationReason() {
		return resignationReason;
	}
	public void setResignationReason(String resignationReason) {
		this.resignationReason = resignationReason;
	}
	public String getDateOfLeaving() {
		return dateOfLeaving;
	}
	public void setDateOfLeaving(String dateOfLeaving) {
		this.dateOfLeaving = dateOfLeaving;
	}
	public String getFinancialAssisstance() {
		return financialAssisstance;
	}
	public void setFinancialAssisstance(String financialAssisstance) {
		this.financialAssisstance = financialAssisstance;
	}
	public EmpJob getOriginalJob() {
		return originalJob;
	}
	public void setOriginalJob(EmpJob originalJob) {
		this.originalJob = originalJob;
	}
	public String getDateOfRejoin() {
		return dateOfRejoin;
	}
	public void setDateOfRejoin(String dateOfRejoin) {
		this.dateOfRejoin = dateOfRejoin;
	}
}
