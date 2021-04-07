package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.util.Date;

public class HonoursCourseApplication implements Serializable{
	private int id;
	private Course course;
	private Student student;
	private int termNumber;
	private int academicYear;
	private Date createdDate;
	private String createdBy;
	private Date lastModifiedDate;
	private String modifiedBy;
	private Boolean isActive;
	
	public HonoursCourseApplication(){
		
	}
	public HonoursCourseApplication(int id, Course course, Student student,
			int termNumber, int academicYear, Date createdDate,
			String createdBy, Date lastModifiedDate, String modifiedBy,
			Boolean isActive) {
		super();
		this.id = id;
		this.course = course;
		this.student = student;
		this.termNumber = termNumber;
		this.academicYear = academicYear;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.lastModifiedDate = lastModifiedDate;
		this.modifiedBy = modifiedBy;
		this.isActive = isActive;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public int getTermNumber() {
		return termNumber;
	}
	public void setTermNumber(int termNumber) {
		this.termNumber = termNumber;
	}
	public int getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(int academicYear) {
		this.academicYear = academicYear;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
}