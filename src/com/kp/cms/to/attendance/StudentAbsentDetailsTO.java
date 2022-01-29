package com.kp.cms.to.attendance;

public class StudentAbsentDetailsTO {
private String studentName;
private String periods;
private String date;
private String subjectName;
private String registerNo;
public String getStudentName() {
	return studentName;
}
public void setStudentName(String studentName) {
	this.studentName = studentName;
}
public String getPeriods() {
	return periods;
}
public void setPeriods(String periods) {
	this.periods = periods;
}
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
public String getSubjectName() {
	return subjectName;
}
public void setSubjectName(String subjectName) {
	this.subjectName = subjectName;
}
public String getRegisterNo() {
	return registerNo;
}
public void setRegisterNo(String registerNo) {
	this.registerNo = registerNo;
}

}
