package com.kp.cms.to.reports;

public class StudentForUniversityTO {
	private String applicantName;
	private String gender;
	private String categoryName;
	private String programName;
	private String courseName;
	private String maxIntake;
	private String secondLanguage;
	private String courseId;
	private int slNo;
	private String registerNo;
	public int getSlNo() {
		return slNo;
	}
	public void setSlNo(int slNo) {
		this.slNo = slNo;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getSecondLanguage() {
		return secondLanguage;
	}
	public void setSecondLanguage(String secondLanguage) {
		this.secondLanguage = secondLanguage;
	}
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getMaxIntake() {
		return maxIntake;
	}
	public void setMaxIntake(String maxIntake) {
		this.maxIntake = maxIntake;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getApplicantName() {
		return applicantName;
	}
	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}
	public String getRegisterNo() {
		return registerNo;
	}
	
}
