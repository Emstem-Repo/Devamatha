package com.kp.cms.bo.exam;

import java.util.Date;

import com.kp.cms.bo.admin.Subject;

public class ExamMidsemExemptionDetails {
	
	private int id;
	private ExamMidsemExemption examMidsemExemption;
	private Subject subject;
	
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	
	public ExamMidsemExemptionDetails() {
		super();
	}
	
	public ExamMidsemExemptionDetails(int id, ExamMidsemExemption examMidsemExemption, Subject subject, 
			String createdBy, Date createdDate, String modifiedBy, Date lastModifiedDate, Boolean isActive){
		super();
		this.id = id;
		this.examMidsemExemption = examMidsemExemption;
		this.subject = subject;
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

	public ExamMidsemExemption getExamMidsemExemption() {
		return examMidsemExemption;
	}

	public void setExamMidsemExemption(ExamMidsemExemption examMidsemExemption) {
		this.examMidsemExemption = examMidsemExemption;
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
	
	
	
}
