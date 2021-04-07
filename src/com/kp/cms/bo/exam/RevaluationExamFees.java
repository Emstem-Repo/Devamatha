package com.kp.cms.bo.exam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Program;

public class RevaluationExamFees implements Serializable {
	
	private int id;
	private Course course;
	private String createdBy;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private Boolean isActive;
	private BigDecimal applicationFees;
	private BigDecimal marksCopyFees;
	private BigDecimal revaluationFees;
	private BigDecimal scrutinyFees;
	private BigDecimal onlineSevriceFees;
	private String academicYear;
	private BigDecimal challengeValuationFees;

	
	public RevaluationExamFees() {
		super();
	}
	
	

	public RevaluationExamFees(int id, Course course, String createdBy,
			String modifiedBy, Date createdDate, Date lastModifiedDate,
			Boolean isActive, BigDecimal applicationFees,
			BigDecimal marksCopyFees, BigDecimal revaluationFees,
			BigDecimal scrutinyFees, BigDecimal onlineSevriceFees,
			String academicYear) {
		super();
		this.id = id;
		this.course = course;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.applicationFees = applicationFees;
		this.marksCopyFees = marksCopyFees;
		this.revaluationFees = revaluationFees;
		this.scrutinyFees = scrutinyFees;
		this.onlineSevriceFees = onlineSevriceFees;
		this.academicYear = academicYear;
	}



	public BigDecimal getMarksCopyFees() {
		return marksCopyFees;
	}

	public void setMarksCopyFees(BigDecimal marksCopyFees) {
		this.marksCopyFees = marksCopyFees;
	}
	public BigDecimal getRevaluationFees() {
		return revaluationFees;
	}

	public void setRevaluationFees(BigDecimal revaluationFees) {
		this.revaluationFees = revaluationFees;
	}

	public BigDecimal getScrutinyFees() {
		return scrutinyFees;
	}

	public void setScrutinyFees(BigDecimal scrutinyFees) {
		this.scrutinyFees = scrutinyFees;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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
	
	public BigDecimal getApplicationFees() {
		return applicationFees;
	}

	public void setApplicationFees(BigDecimal applicationFees) {
		this.applicationFees = applicationFees;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public BigDecimal getOnlineSevriceFees() {
		return onlineSevriceFees;
	}
	public BigDecimal getChallengeValuationFees() {
		return challengeValuationFees;
	}



	public void setChallengeValuationFees(BigDecimal challengeValuationFees) {
		this.challengeValuationFees = challengeValuationFees;
	}



	public void setOnlineSevriceFees(BigDecimal onlineSevriceFees) {
		this.onlineSevriceFees = onlineSevriceFees;
	}
	
}