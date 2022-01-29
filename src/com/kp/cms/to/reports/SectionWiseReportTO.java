package com.kp.cms.to.reports;

import java.io.Serializable;

public class SectionWiseReportTO implements Serializable {
	
	private int slno;
	private String regNo;
	private String rollNo;
	private String studentName;
	private String applnNumber;
	private String gender;
	private String secondLanguage;
	private String percentage;
	private String sectionName;
	private String acedamicYear;
	private int classId;
	private String className;
	
	public int getSlno() {
		return slno;
	}
	public void setSlno(int slno) {
		this.slno = slno;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getApplnNumber() {
		return applnNumber;
	}
	public void setApplnNumber(String applnNumber) {
		this.applnNumber = applnNumber;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getSecondLanguage() {
		return secondLanguage;
	}
	public void setSecondLanguage(String secondLanguage) {
		this.secondLanguage = secondLanguage;
	}
	public String getPercentage() {
		return percentage;
	}
	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	public String getRollNo() {
		return rollNo;
	}
	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	public String getAcedamicYear() {
		return acedamicYear;
	}
	public void setAcedamicYear(String acedamicYear) {
		this.acedamicYear = acedamicYear;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}

}