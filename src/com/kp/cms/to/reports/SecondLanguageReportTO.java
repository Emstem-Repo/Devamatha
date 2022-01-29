package com.kp.cms.to.reports;

import java.io.Serializable;

public class SecondLanguageReportTO implements Serializable{

	private String courseName;
	private String applicationNo;
	private String rollNo;		
	private String registerNo;
	private String studentName;
	private String secondLanguage;
	private int slNo;
	
	public int getSlNo() {
		return slNo;
	}
	public void setSlNo(int slNo) {
		this.slNo = slNo;
	}
	public String getCourseName() {
		return courseName;
	}
	public String getApplicationNo() {
		return applicationNo;
	}
	public String getRollNo() {
		return rollNo;
	}
	public String getRegisterNo() {
		return registerNo;
	}
	public String getStudentName() {
		return studentName;
	}
	public String getSecondLanguage() {
		return secondLanguage;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}
	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}
	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public void setSecondLanguage(String secondLanguage) {
		this.secondLanguage = secondLanguage;
	}
	
	
}
