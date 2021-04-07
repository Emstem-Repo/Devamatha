package com.kp.cms.bo.admin;

// Generated May 18, 2009 3:06:44 PM by Hibernate Tools 3.2.0.b9

import java.util.Date;

/**
 * AppnoRegnoSection generated by hbm2java
 */
public class AppnoRegnoSection implements java.io.Serializable {

	private int applnNo;
	private Course course;
	private String registerNo;
	private String section;
	private String createdBy;
	private Date createdDate;
	private int appliedYear;
	private String rollNo;
	private int semester;

	public AppnoRegnoSection() {
	}

	public AppnoRegnoSection(int applnNo, Course course, int appliedYear,
			String rollNo, int semester) {
		this.applnNo = applnNo;
		this.course = course;
		this.appliedYear = appliedYear;
		this.rollNo = rollNo;
		this.semester = semester;
	}

	public AppnoRegnoSection(int applnNo, Course course, String registerNo,
			String section, String createdBy, Date createdDate,
			int appliedYear, String rollNo, int semester) {
		this.applnNo = applnNo;
		this.course = course;
		this.registerNo = registerNo;
		this.section = section;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.appliedYear = appliedYear;
		this.rollNo = rollNo;
		this.semester = semester;
	}

	public int getApplnNo() {
		return this.applnNo;
	}

	public void setApplnNo(int applnNo) {
		this.applnNo = applnNo;
	}

	public Course getCourse() {
		return this.course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getRegisterNo() {
		return this.registerNo;
	}

	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}

	public String getSection() {
		return this.section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public int getAppliedYear() {
		return this.appliedYear;
	}

	public void setAppliedYear(int appliedYear) {
		this.appliedYear = appliedYear;
	}

	public String getRollNo() {
		return this.rollNo;
	}

	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}

	public int getSemester() {
		return this.semester;
	}

	public void setSemester(int semester) {
		this.semester = semester;
	}

}