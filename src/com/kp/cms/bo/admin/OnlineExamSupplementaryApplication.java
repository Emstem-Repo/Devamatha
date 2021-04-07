package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.util.Date;

public class OnlineExamSupplementaryApplication implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private Student studentId;
	private Classes stdClassId;
	private String venue ;
	private String time;
	private Date dateOfExam;
	private Date appliedDate;
	private String academicYear;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	
	public OnlineExamSupplementaryApplication() {
		super();
		// TODO Auto-generated constructor stub
	}


	public OnlineExamSupplementaryApplication(Student studentId,
			Classes stdClassId, String venue, String time, Date dateOfExam,
			Date appliedDate, String academicYear, String createdBy, Date createdDate,
			String modifiedBy, Date lastModifiedDate, Boolean isActive) {
		super();
		this.studentId = studentId;
		this.stdClassId = stdClassId;
		this.venue = venue;
		this.time = time;
		this.dateOfExam = dateOfExam;
		this.appliedDate = appliedDate;
		this.academicYear= academicYear;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Student getStudentId() {
		return studentId;
	}
	public void setStudentId(Student studentId) {
		this.studentId = studentId;
	}
	public String getVenue() {
		return venue;
	}
	public void setVenue(String venue) {
		this.venue = venue;
	}
	public Date getAppliedDate() {
		return appliedDate;
	}
	public void setAppliedDate(Date appliedDate) {
		this.appliedDate = appliedDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Classes getStdClassId() {
		return stdClassId;
	}
	public void setStdClassId(Classes stdClassId) {
		this.stdClassId = stdClassId;
	}
	public Date getDateOfExam() {
		return dateOfExam;
	}
	public void setDateOfExam(Date dateOfExam) {
		this.dateOfExam = dateOfExam;
	}

	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	
	
	
	

}
