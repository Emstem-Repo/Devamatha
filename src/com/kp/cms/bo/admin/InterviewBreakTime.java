package com.kp.cms.bo.admin;

// Generated Jan 22, 2009 3:35:47 PM by Hibernate Tools 3.2.0.b9

import java.util.Date;

/**
 * InterviewBreakTime generated by hbm2java
 */
public class InterviewBreakTime implements java.io.Serializable {

	private int id;
	private String createdBy;;
	private InterviewSchedule interviewSchedule;
	private String modifiedBy;
	private String startTime;
	private String endTime;
	private Date createdDate;
	private Date lastModifiedDate;

	public InterviewBreakTime() {
	}

	public InterviewBreakTime(int id) {
		this.id = id;
	}

	public InterviewBreakTime(int id, String createdBy,
			InterviewSchedule interviewSchedule, String modifiedBy,
			String startTime, String endTime, Date createdDate,
			Date lastModifiedDate) {
		this.id = id;
		this.createdBy = createdBy;
		this.interviewSchedule = interviewSchedule;
		this.modifiedBy = modifiedBy;
		this.startTime = startTime;
		this.endTime = endTime;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy)  {
		this.createdBy = createdBy;
	}

	public InterviewSchedule getInterviewSchedule() {
		return this.interviewSchedule;
	}

	public void setInterviewSchedule(InterviewSchedule interviewSchedule) {
		this.interviewSchedule = interviewSchedule;
	}

	public String getModifiedBy()  {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}


	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModifiedDate() {
		return this.lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

}