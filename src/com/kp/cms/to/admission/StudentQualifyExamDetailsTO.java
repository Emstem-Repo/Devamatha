package com.kp.cms.to.admission;

import java.util.Date;

// Generated May 20, 2009 11:04:46 AM by Hibernate Tools 3.2.0.b9

/**
 * TO class for StudentQualifyexamDetail BO
 * StudentQualifyexamDetail generated by hbm2java
 */
public class StudentQualifyExamDetailsTO implements java.io.Serializable {

	private int id;
	private int admApplnId;
	private String optionalSubjects;
	private String secondLanguage;
	private double totalMarks;
	private double obtainedMarks;
	private double percentage;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private double coreSubjectsPercentage;
	private double coreSubjectsTotalMarks;
	private double coreSubjectsObtainedMarks;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAdmApplnId() {
		return admApplnId;
	}
	public void setAdmApplnId(int admApplnId) {
		this.admApplnId = admApplnId;
	}
	public String getOptionalSubjects() {
		return optionalSubjects;
	}
	public void setOptionalSubjects(String optionalSubjects) {
		this.optionalSubjects = optionalSubjects;
	}
	public String getSecondLanguage() {
		return secondLanguage;
	}
	public void setSecondLanguage(String secondLanguage) {
		this.secondLanguage = secondLanguage;
	}
	public double getTotalMarks() {
		return totalMarks;
	}
	public void setTotalMarks(double totalMarks) {
		this.totalMarks = totalMarks;
	}
	public double getObtainedMarks() {
		return obtainedMarks;
	}
	public void setObtainedMarks(double obtainedMarks) {
		this.obtainedMarks = obtainedMarks;
	}
	public double getPercentage() {
		return percentage;
	}
	public void setPercentage(double percentage) {
		this.percentage = percentage;
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
	public double getCoreSubjectsPercentage() {
		return coreSubjectsPercentage;
	}
	public void setCoreSubjectsPercentage(double coreSubjectsPercentage) {
		this.coreSubjectsPercentage = coreSubjectsPercentage;
	}
	public double getCoreSubjectsTotalMarks() {
		return coreSubjectsTotalMarks;
	}
	public void setCoreSubjectsTotalMarks(double coreSubjectsTotalMarks) {
		this.coreSubjectsTotalMarks = coreSubjectsTotalMarks;
	}
	public double getCoreSubjectsObtainedMarks() {
		return coreSubjectsObtainedMarks;
	}
	public void setCoreSubjectsObtainedMarks(double coreSubjectsObtainedMarks) {
		this.coreSubjectsObtainedMarks = coreSubjectsObtainedMarks;
	}

	
	

}
