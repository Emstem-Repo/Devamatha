package com.kp.cms.to.admission;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.to.admin.CourseTO;

public class ExcelDataTO implements Serializable {
	
	private int applicationId;
	private String registrationNumber;
	private String section;
	private CourseTO courseTO;
	private String rollNumber;
	private int semester;
	private Date appliedYear;
	private String userId;
	private int classSchemeWiseId;
	private int studentId;
	private int classId;
	private String year;
	private String challanNumber;
	private java.sql.Date challanDate;
	private String examId;
	private boolean isVerified;
	private double amount;
	private String universityRegNo;
	
	public String getUniversityRegNo() {
		return universityRegNo;
	}
	public void setUniversityRegNo(String universityRegNo) {
		this.universityRegNo = universityRegNo;
	}
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	
	public int getClassSchemeWiseId() {
		return classSchemeWiseId;
	}
	public void setClassSchemeWiseId(int classSchemeWiseId) {
		this.classSchemeWiseId = classSchemeWiseId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getAppliedYear() {
		return appliedYear;
	}
	public void setAppliedYear(Date appliedYear) {
		this.appliedYear = appliedYear;
	}
	public String getRegistrationNumber() {
		return registrationNumber;
	}
	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public CourseTO getCourseTO() {
		return courseTO;
	}
	public void setCourseTO(CourseTO courseTO) {
		this.courseTO = courseTO;
	}
	public String getRollNumber() {
		return rollNumber;
	}
	public void setRollNumber(String rollNumber) {
		this.rollNumber = rollNumber;
	}
	public int getSemester() {
		return semester;
	}
	public void setSemester(int semester) {
		this.semester = semester;
	}
	public int getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getChallanNumber() {
		return challanNumber;
	}
	public void setChallanNumber(String challanNumber) {
		this.challanNumber = challanNumber;
	}
	public java.sql.Date getChallanDate() {
		return challanDate;
	}
	public void setChallanDate(java.sql.Date challanDate) {
		this.challanDate = challanDate;
	}
	public String getExamId() {
		return examId;
	}
	public void setExamId(String examId) {
		this.examId = examId;
	}
	public boolean getIsVerified() {
		return isVerified;
	}
	public void setIsVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
}