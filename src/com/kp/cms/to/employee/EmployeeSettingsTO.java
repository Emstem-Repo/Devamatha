package com.kp.cms.to.employee;

import java.io.Serializable;

public class EmployeeSettingsTO implements Serializable{
private int ageOfRetirement;
private String smsAlert;
private String absenceChecking;
private String employeeLeaveType;
private int currentApplicationNo;
private int id;
public int getAgeOfRetirement() {
	return ageOfRetirement;
}
public void setAgeOfRetirement(int ageOfRetirement) {
	this.ageOfRetirement = ageOfRetirement;
}
public String getSmsAlert() {
	return smsAlert;
}
public void setSmsAlert(String smsAlert) {
	this.smsAlert = smsAlert;
}
public String getAbsenceChecking() {
	return absenceChecking;
}
public void setAbsenceChecking(String absenceChecking) {
	this.absenceChecking = absenceChecking;
}
public String getEmployeeLeaveType() {
	return employeeLeaveType;
}
public void setEmployeeLeaveType(String employeeLeaveType) {
	this.employeeLeaveType = employeeLeaveType;
}
public int getCurrentApplicationNo() {
	return currentApplicationNo;
}
public void setCurrentApplicationNo(int currentApplicationNo) {
	this.currentApplicationNo = currentApplicationNo;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}

}
