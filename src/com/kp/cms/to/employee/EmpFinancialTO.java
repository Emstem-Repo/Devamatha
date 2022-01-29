package com.kp.cms.to.employee;

import java.util.Date;

public class EmpFinancialTO {
	
	private int id;
	private String employeeId;
	private String financialDate;
	private String financialDetails;
	private String financialAmount;
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
	public String getFinancialDate() {
		return financialDate;
	}
	public void setFinancialDate(String financialDate) {
		this.financialDate = financialDate;
	}
	public String getFinancialDetails() {
		return financialDetails;
	}
	public void setFinancialDetails(String financialDetails) {
		this.financialDetails = financialDetails;
	}
	public String getFinancialAmount() {
		return financialAmount;
	}
	public void setFinancialAmount(String financialAmount) {
		this.financialAmount = financialAmount;
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
