package com.kp.cms.to.employee;

import java.util.Date;

public class EmpIncentivesTO {
	
	private int id;
	private String employeeId;
	private String incentivesDate;
	private String incentivesDetails;
	private String incentivesAmount;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getIncentivesDate() {
		return incentivesDate;
	}
	public void setIncentivesDate(String incentivesDate) {
		this.incentivesDate = incentivesDate;
	}
	public String getIncentivesDetails() {
		return incentivesDetails;
	}
	public void setIncentivesDetails(String incentivesDetails) {
		this.incentivesDetails = incentivesDetails;
	}
	public String getIncentivesAmount() {
		return incentivesAmount;
	}
	public void setIncentivesAmount(String incentivesAmount) {
		this.incentivesAmount = incentivesAmount;
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
	
}
