package com.kp.cms.to.admin;

import java.util.Date;

public class EmpLeaveTO {
	private int id;
	private String empLeaveTypeId;
	private String empLeaveTypeName;
	private String employeeId;
	private String leavesAllocated;
	private String leavesSanctioned;
	private String leavesRemaining;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private boolean isActive;
	private String name;
	private Integer year;
	private String month;
	private String period;
	private Date date;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmpLeaveTypeId() {
		return empLeaveTypeId;
	}
	public void setEmpLeaveTypeId(String empLeaveTypeId) {
		this.empLeaveTypeId = empLeaveTypeId;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getLeavesAllocated() {
		return leavesAllocated;
	}
	public void setLeavesAllocated(String leavesAllocated) {
		this.leavesAllocated = leavesAllocated;
	}
	public String getLeavesSanctioned() {
		return leavesSanctioned;
	}
	public void setLeavesSanctioned(String leavesSanctioned) {
		this.leavesSanctioned = leavesSanctioned;
	}
	public String getLeavesRemaining() {
		return leavesRemaining;
	}
	public void setLeavesRemaining(String leavesRemaining) {
		this.leavesRemaining = leavesRemaining;
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
	public String getEmpLeaveTypeName() {
		return empLeaveTypeName;
	}
	public void setEmpLeaveTypeName(String empLeaveTypeName) {
		this.empLeaveTypeName = empLeaveTypeName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
