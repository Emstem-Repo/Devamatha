package com.kp.cms.bo.exam;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Subject;

public class ExamValuationProcess implements Serializable{
	
	private int id;
	private ExamDefinition exam;
	private Subject subject;
	private String createdBy;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Boolean valuationProcessCompleted;
	private Boolean overallProcessCompleted;
	private Course course;
	
	public ExamValuationProcess() {
		super();
	}
	
	
	public ExamValuationProcess(int id, ExamDefinition exam,
			Subject subject, String createdBy, String modifiedBy, Date createdDate,
			Date lastModifiedDate, Boolean isActive, Boolean valuationProcessCompleted, Boolean overallProcessCompleted,Course course) {
		super();
		this.id = id;
		this.exam = exam;
		this.subject = subject;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.valuationProcessCompleted = valuationProcessCompleted;
		this.overallProcessCompleted = overallProcessCompleted;
		this.course=course;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ExamDefinition getExam() {
		return exam;
	}

	public void setExam(ExamDefinition exam) {
		this.exam = exam;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
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


	public Boolean getValuationProcessCompleted() {
		return valuationProcessCompleted;
	}


	public void setValuationProcessCompleted(Boolean valuationProcessCompleted) {
		this.valuationProcessCompleted = valuationProcessCompleted;
	}


	public Boolean getOverallProcessCompleted() {
		return overallProcessCompleted;
	}


	public void setOverallProcessCompleted(Boolean overallProcessCompleted) {
		this.overallProcessCompleted = overallProcessCompleted;
	}


	public Course getCourse() {
		return course;
	}


	public void setCourse(Course course) {
		this.course = course;
	}
	
	
	
}
