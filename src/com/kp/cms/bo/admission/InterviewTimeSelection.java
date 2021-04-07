package com.kp.cms.bo.admission;

import java.util.Date;

public class InterviewTimeSelection implements java.io.Serializable{
	private int id;
	private int maxSeats;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private String time;
	private InterviewSelectionSchedule interviewSelectionSchedule;
	private String endTime;
	
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMaxSeats() {
		return maxSeats;
	}
	public void setMaxSeats(int maxSeats) {
		this.maxSeats = maxSeats;
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
	public InterviewSelectionSchedule getInterviewSelectionSchedule() {
		return interviewSelectionSchedule;
	}
	public void setInterviewSelectionSchedule(
			InterviewSelectionSchedule interviewSelectionSchedule) {
		this.interviewSelectionSchedule = interviewSelectionSchedule;
	}
	
	public InterviewTimeSelection(){
		
	}
	public InterviewTimeSelection(int id, int maxSeats, String createdBy,
			Date createdDate, String modifiedBy, Date lastModifiedDate,
			Boolean isActive, String time,
			InterviewSelectionSchedule interviewSelectionSchedule) {
		super();
		this.id = id;
		this.maxSeats = maxSeats;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.time = time;
		this.interviewSelectionSchedule = interviewSelectionSchedule;
	}
	
}
