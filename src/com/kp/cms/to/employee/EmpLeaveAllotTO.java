package com.kp.cms.to.employee;

import java.io.Serializable;

import com.kp.cms.bo.admin.EmpLeaveType;

public class EmpLeaveAllotTO implements Serializable,Comparable<EmpLeaveAllotTO>{
private int id;
private String empType;
private String leaveType;
private String allottedLeave;
private String sanctionedLeave;
private String remainingLeave;
private EmpLeaveType empLeaveType;
private int empLeaveId;
private String month;
private Integer year;
public String getSanctionedLeave() {
	return sanctionedLeave;
}
public void setSanctionedLeave(String sanctionedLeave) {
	this.sanctionedLeave = sanctionedLeave;
}
private String initRequired;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getEmpType() {
	return empType;
}
public void setEmpType(String empType) {
	this.empType = empType;
}
public String getLeaveType() {
	return leaveType;
}
public void setLeaveType(String leaveType) {
	this.leaveType = leaveType;
}
public String getAllottedLeave() {
	return allottedLeave;
}
public void setAllottedLeave(String allottedLeave) {
	this.allottedLeave = allottedLeave;
}
public String getInitRequired() {
	return initRequired;
}
public void setInitRequired(String initRequired) {
	this.initRequired = initRequired;
}
public EmpLeaveType getEmpLeaveType() {
	return empLeaveType;
}
public void setEmpLeaveType(EmpLeaveType empLeaveType) {
	this.empLeaveType = empLeaveType;
}
public String getRemainingLeave() {
	return remainingLeave;
}
public void setRemainingLeave(String remainingLeave) {
	this.remainingLeave = remainingLeave;
}
public int getEmpLeaveId() {
	return empLeaveId;
}
public void setEmpLeaveId(int empLeaveId) {
	this.empLeaveId = empLeaveId;
}
public String getMonth() {
	return month;
}
public void setMonth(String month) {
	this.month = month;
}
public Integer getYear() {
	return year;
}
public void setYear(Integer year) {
	this.year = year;
}
@Override
public int compareTo(EmpLeaveAllotTO arg0) {
	if(arg0!=null && this!=null && arg0.getEmpLeaveType().getName()!=null
			 && this.getEmpLeaveType().getName()!=null){
		return this.getEmpLeaveType().getName().compareTo(arg0.getEmpLeaveType().getName());
	}		
	else
	return 0;
}

}
