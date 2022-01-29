package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.util.Date;

public class EmpInitializeLeaves implements Serializable {
	private int id;
	private EmployeeTypeBO empTypeId;
	private Integer allotLeaves;
	private Date allotedDate;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Boolean isInitializeRequired;
	private EmpLeaveType leaveType;
	
	
	public EmpInitializeLeaves() {

	}
	
	public EmpInitializeLeaves(int id, EmployeeTypeBO empTypeId,
			Integer allotLeaves, Date allotedDate, String createdBy,
			Date createdDate, String modifiedBy, Date lastModifiedDate,
			Boolean isActive, Boolean isInitializeRequired,EmpLeaveType leaveType) {
		super();
		this.id = id;
		this.empTypeId = empTypeId;
		this.allotLeaves = allotLeaves;
		this.allotedDate = allotedDate;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.isInitializeRequired = isInitializeRequired;
		this.leaveType=leaveType;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public EmployeeTypeBO getEmpTypeId() {
		return empTypeId;
	}
	public void setEmpTypeId(EmployeeTypeBO empTypeId) {
		this.empTypeId = empTypeId;
	}
	public Integer getAllotLeaves() {
		return allotLeaves;
	}
	public void setAllotLeaves(Integer allotLeaves) {
		this.allotLeaves = allotLeaves;
	}
	public Date getAllotedDate() {
		return allotedDate;
	}
	public void setAllotedDate(Date allotedDate) {
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

	public EmpLeaveType getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(EmpLeaveType leaveType) {
		this.leaveType = leaveType;
	}
	
}
