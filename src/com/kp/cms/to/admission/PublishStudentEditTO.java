package com.kp.cms.to.admission;

public class PublishStudentEditTO {
	
	public int id;
	public String className;
	public String academicYear;
	public String[] classId;
	public String startDate;
	public String endDate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public String[] getClassId() {
		return classId;
	}
	public void setClassId(String[] classId) {
		this.classId = classId;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	

}
