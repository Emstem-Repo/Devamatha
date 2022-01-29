package com.kp.cms.bo.employee;

import java.io.Serializable;
import java.util.Date;

public class BiometricDetails implements Serializable{
private int id;
private String machineId;
private String machineIp;
private String machineName;
private String createdBy;;
private String modifiedBy;
private Boolean isActive;
private Date createdDate;
private Date lastModifiedDate;
private String workLocation;
public BiometricDetails(){
	
}
public BiometricDetails(String machineId, String machineIp,
		String machineName, String createdBy, String modifiedBy,
		Boolean isActive, Date createdDate, Date lastModifiedDate, String workLocation) {
	super();
	this.machineId = machineId;
	this.machineIp = machineIp;
	this.machineName = machineName;
	this.createdBy = createdBy;
	this.modifiedBy = modifiedBy;
	this.isActive = isActive;
	this.createdDate = createdDate;
	this.lastModifiedDate = lastModifiedDate;
	this.workLocation = workLocation;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getMachineId() {
	return machineId;
}
public void setMachineId(String machineId) {
	this.machineId = machineId;
}
public String getMachineIp() {
	return machineIp;
}
public void setMachineIp(String machineIp) {
	this.machineIp = machineIp;
}
public String getMachineName() {
	return machineName;
}
public void setMachineName(String machineName) {
	this.machineName = machineName;
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
public String getWorkLocation() {
	return workLocation;
}
public void setWorkLocation(String workLocation) {
	this.workLocation = workLocation;
}

}
