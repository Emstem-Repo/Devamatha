package com.kp.cms.to.admin;

import java.util.Date;

public class EmpJobAllowanceTO {
	private int id;
	private String empAllowanceId;
	private String empAllowanceName;
	private String empJobId;
	private String amount;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private boolean isActive;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getEmpAllowanceId() {
		return empAllowanceId;
	}
	public void setEmpAllowanceId(String empAllowanceId) {
		this.empAllowanceId = empAllowanceId;
	}
	public String getEmpAllowanceName() {
		return empAllowanceName;
	}
	public void setEmpAllowanceName(String empAllowanceName) {
		this.empAllowanceName = empAllowanceName;
	}
	public String getEmpJobId() {
		return empJobId;
	}
	public void setEmpJobId(String empJobId) {
		this.empJobId = empJobId;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
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
	
	
	
}
