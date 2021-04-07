package com.kp.cms.to.admission;

import java.util.Date;

import com.kp.cms.to.admin.EmployeeTO;

public class InterviewBreakTimeTO {

	
	private int id;
	//private EmployeeTO employeeByCreatedBy;
	private InterviewScheduleTO interviewSchedule;
	//private EmployeeTO employeeByModifiedBy;
	private String startTime;
	private String endTime;
	private Date createdDate;
	private Date lastModifiedDate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	
	public InterviewScheduleTO getInterviewSchedule() {
		return interviewSchedule;
	}
	public void setInterviewSchedule(InterviewScheduleTO interviewSchedule) {
		this.interviewSchedule = interviewSchedule;
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

	
	
}
