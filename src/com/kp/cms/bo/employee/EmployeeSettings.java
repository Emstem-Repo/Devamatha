package com.kp.cms.bo.employee;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.admin.EmpLeaveType;

public class EmployeeSettings implements Serializable{

private int id;
private Integer ageOfRetirement;
private Boolean smsAlert;
private Boolean absenceChecking;
private EmpLeaveType accumulateLeaveType;
private String currentApplicationNo;
private String modifiedBy;
private Date lastModifiedDate;
private Integer machineIdForOfflineINPunch;
private Integer machineIdForOfflineOUTPunch;
public EmployeeSettings(){
	
}
public EmployeeSettings(int id, Integer ageOfRetirement, Boolean smsAlert,
		Boolean absenceChecking, EmpLeaveType accumulateLeaveType,
		String currentApplicationNo, String modifiedBy, Date lastModifiedDate,Integer machineIdForOfflineINPunch,Integer machineIdForOfflineOUTPunch) {
	super();
	this.id = id;
	this.ageOfRetirement = ageOfRetirement;
	this.smsAlert = smsAlert;
	this.absenceChecking = absenceChecking;
	this.accumulateLeaveType = accumulateLeaveType;
	this.currentApplicationNo = currentApplicationNo;
	this.modifiedBy = modifiedBy;
	this.lastModifiedDate = lastModifiedDate;
	this.machineIdForOfflineINPunch=machineIdForOfflineINPunch;
	this.machineIdForOfflineOUTPunch=machineIdForOfflineOUTPunch;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public Integer getAgeOfRetirement() {
	return ageOfRetirement;
}
public void setAgeOfRetirement(Integer ageOfRetirement) {
	this.ageOfRetirement = ageOfRetirement;
}
public Boolean getSmsAlert() {
	return smsAlert;
}
public void setSmsAlert(Boolean smsAlert) {
	this.smsAlert = smsAlert;
}
public Boolean getAbsenceChecking() {
	return absenceChecking;
}
public void setAbsenceChecking(Boolean absenceChecking) {
	this.absenceChecking = absenceChecking;
}
public String getCurrentApplicationNo() {
	return currentApplicationNo;
}
public void setCurrentApplicationNo(String currentApplicationNo) {
	this.currentApplicationNo = currentApplicationNo;
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
public EmpLeaveType getAccumulateLeaveType() {
	return accumulateLeaveType;
}
public void setAccumulateLeaveType(EmpLeaveType accumulateLeaveType) {
	this.accumulateLeaveType = accumulateLeaveType;
}
public Integer getMachineIdForOfflineINPunch() {
	return machineIdForOfflineINPunch;
}
public void setMachineIdForOfflineINPunch(Integer machineIdForOfflineINPunch) {
	this.machineIdForOfflineINPunch = machineIdForOfflineINPunch;
}
public Integer getMachineIdForOfflineOUTPunch() {
	return machineIdForOfflineOUTPunch;
}
public void setMachineIdForOfflineOUTPunch(Integer machineIdForOfflineOUTPunch) {
	this.machineIdForOfflineOUTPunch = machineIdForOfflineOUTPunch;
}
}
