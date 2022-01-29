package com.kp.cms.to.employee;

import java.util.Date;

public class EmpFeeConcessionTO {
	private int id;
	private String employeeId;
	private String feeConcessionDate;
	private String feeConcessionDetails;
	private String feeConcessionAmount;
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
	public String getFeeConcessionDate() {
		return feeConcessionDate;
	}
	public void setFeeConcessionDate(String feeConcessionDate) {
		this.feeConcessionDate = feeConcessionDate;
	}
	public String getFeeConcessionDetails() {
		return feeConcessionDetails;
	}
	public void setFeeConcessionDetails(String feeConcessionDetails) {
		this.feeConcessionDetails = feeConcessionDetails;
	}
	public String getFeeConcessionAmount() {
		return feeConcessionAmount;
	}
	public void setFeeConcessionAmount(String feeConcessionAmount) {
		this.feeConcessionAmount = feeConcessionAmount;
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
