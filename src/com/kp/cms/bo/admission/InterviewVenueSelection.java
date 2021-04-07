package com.kp.cms.bo.admission;

import java.util.Date;

import com.kp.cms.bo.admin.ExamCenter;

public class InterviewVenueSelection implements java.io.Serializable{

	private int id;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private InterviewSelectionSchedule interviewSelectionSchedule;
	private ExamCenter examCenter;
	
	
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
	public InterviewSelectionSchedule getInterviewSelectionSchedule() {
		return interviewSelectionSchedule;
	}
	public void setInterviewSelectionSchedule(
			InterviewSelectionSchedule interviewSelectionSchedule) {
		this.interviewSelectionSchedule = interviewSelectionSchedule;
	}
	public ExamCenter getExamCenter() {
		return examCenter;
	}
	public void setExamCenter(ExamCenter examCenter) {
		this.examCenter = examCenter;
	}
	public InterviewVenueSelection(){
		
	}
	public InterviewVenueSelection(int id, String createdBy, Date createdDate,
			String modifiedBy, Date lastModifiedDate, Boolean isActive,
			InterviewSelectionSchedule interviewSelectionSchedule,
			ExamCenter examCenter) {
		super();
		this.id = id;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.interviewSelectionSchedule = interviewSelectionSchedule;
		this.examCenter = examCenter;
	}
	
}
