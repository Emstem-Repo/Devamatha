package com.kp.cms.to.pettycash;

import java.io.Serializable;

public class StudentDetailsTo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String appRegRollNo;
	private String academicYear;
	private String studentName;
	private String studentid;
	private String applNo;
	private String rollNo;
	private String regNo;
	public String getAppRegRollNo() {
		return appRegRollNo;
	}
	public void setAppRegRollNo(String appRegRollNo) {
		this.appRegRollNo = appRegRollNo;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getStudentid() {
		return studentid;
	}
	public void setStudentid(String studentid) {
		this.studentid = studentid;
	}
	public String getApplNo() {
		return applNo;
	}
	public void setApplNo(String applNo) {
		this.applNo = applNo;
	}
	public String getRollNo() {
		return rollNo;
	}
	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}
	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	
	
	

}
