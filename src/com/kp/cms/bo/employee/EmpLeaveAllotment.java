package com.kp.cms.bo.employee;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.admin.EmpLeaveType;

public class EmpLeaveAllotment implements Serializable{
private int id;
private EmpType empType;
private EmpLeaveType empLeaveType;
private Integer allottedLeave;
private Boolean initRequired;
private Integer noOfAccumulatedLeave;
private Date createdDate;
private Date lastModifiedDate;
private String createdBy;;
private String modifiedBy;
private Boolean isActive;
public EmpLeaveAllotment(){
	
}
public EmpLeaveAllotment(int id, EmpType empType, EmpLeaveType empLeaveType,
		Integer allottedLeave, Boolean initRequired,
		Integer noOfAccumulatedLeave, Date createdDate, Date lastModifiedDate,
		String createdBy, String modifiedBy, Boolean isActive) {
	super();
	this.id = id;
	this.empType = empType;
	this.empLeaveType = empLeaveType;
	this.allottedLeave = allottedLeave;
	this.initRequired = initRequired;
	this.noOfAccumulatedLeave = noOfAccumulatedLeave;
	this.createdDate = createdDate;
	this.lastModifiedDate = lastModifiedDate;
	this.createdBy = createdBy;
	this.modifiedBy = modifiedBy;
	this.isActive = isActive;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public EmpType getEmpType() {
	return empType;
}
public void setEmpType(EmpType empType) {
	this.empType = empType;
}
public EmpLeaveType getEmpLeaveType() {
	return empLeaveType;
}
public void setEmpLeaveType(EmpLeaveType empLeaveType) {
	this.empLeaveType = empLeaveType;
}
public Integer getAllottedLeave() {
	return allottedLeave;
}
public void setAllottedLeave(Integer allottedLeave) {
	this.allottedLeave = allottedLeave;
}
public Boolean getInitRequired() {
	return initRequired;
}
public void setInitRequired(Boolean initRequired) {
	this.initRequired = initRequired;
}
public Integer getNoOfAccumulatedLeave() {
	return noOfAccumulatedLeave;
}
public void setNoOfAccumulatedLeave(Integer noOfAccumulatedLeave) {
	this.noOfAccumulatedLeave = noOfAccumulatedLeave;
}
public Date getCreatedDate() {
	return createdDate;
}
public void setCreatedDate(Date createdDate) {
	this.createdDate = createdDate;
}
public Date getLastModifiedDate() {
	return lastModifiedDate;
}
public void setLastModifiedDate(Date lastModifiedDate) {
	this.lastModifiedDate = lastModifiedDate;
}
public String getCreatedBy() {
	return createdBy;
}
public void setCreatedBy(String createdBy) {
	this.createdBy = createdBy;
}
public String getModifiedBy() {
	return modifiedBy;
}
public void setModifiedBy(String modifiedBy) {
	this.modifiedBy = modifiedBy;
}
public Boolean getIsActive() {
	return isActive;
}
public void setIsActive(Boolean isActive) {
	this.isActive = isActive;
}

}
