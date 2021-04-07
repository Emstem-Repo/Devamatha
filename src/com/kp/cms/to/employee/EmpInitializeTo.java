package com.kp.cms.to.employee;

import java.util.Date;

public class EmpInitializeTo {
	private int id;
	private Integer allotLeaves;
	private String allotedDate;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Boolean isInitializeRequired;
	private String empTypeName;
	private String leaveTypeName;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Integer getAllotLeaves() {
		return allotLeaves;
	}
	public void setAllotLeaves(Integer allotLeaves) {
		this.allotLeaves = allotLeaves;
	}
	
	public String getAllotedDate() {
		return allotedDate;
	}
	public void setAllotedDate(String allotedDate) {
		this.allotedDate = allotedDate;
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
	public Boolean getIsInitializeRequired() {
		return isInitializeRequired;
	}
	public void setIsInitializeRequired(Boolean isInitializeRequired) {
		this.isInitializeRequired = isInitializeRequired;
	}
	public String getEmpTypeName() {
		return empTypeName;
	}
	public void setEmpTypeName(String empTypeName) {
		this.empTypeName = empTypeName;
	}
	public String getLeaveTypeName() {
		return leaveTypeName;
	}
	public void setLeaveTypeName(String leaveTypeName) {
		this.leaveTypeName = leaveTypeName;
	}
}
