package com.kp.cms.to.admission;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.kp.cms.to.admin.EmployeeTO;

public class InterviewScheduleTO {

	private int id;
	private EmployeeTO employee;
	private InterviewTO interview;
	private String date;
	private String venue;
	private String startTime;
	private String endTime;
	private Integer createdBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private String timeInterval;
	private Set<InterviewBreakTimeTO> interviewBreakTimes = new HashSet<InterviewBreakTimeTO>(
			0);
	private Set<InterviewersTO> interviewerses = new HashSet<InterviewersTO>(0);
	private Set<InterviewCardTO> interviewCards = new HashSet<InterviewCardTO>(0);
	
	public Set<InterviewCardTO> getInterviewCards() {
		return interviewCards;
	}
	public void setInterviewCards(Set<InterviewCardTO> interviewCards) {
		this.interviewCards = interviewCards;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getVenue() {
		return venue;
	}
	public void setVenue(String venue) {
		this.venue = venue;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
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
	public String getTimeInterval() {
		return timeInterval;
	}
	public void setTimeInterval(String timeInterval) {
		this.timeInterval = timeInterval;
	}
	public EmployeeTO getEmployee() {
		return employee;
	}
	public void setEmployee(EmployeeTO employee) {
		this.employee = employee;
	}
	public InterviewTO getInterview() {
		return interview;
	}
	public void setInterview(InterviewTO interview) {
		this.interview = interview;
	}
	public Set<InterviewBreakTimeTO> getInterviewBreakTimes() {
		return interviewBreakTimes;
	}
	public void setInterviewBreakTimes(Set<InterviewBreakTimeTO> interviewBreakTimes) {
		this.interviewBreakTimes = interviewBreakTimes;
	}
	public Set<InterviewersTO> getInterviewerses() {
		return interviewerses;
	}
	public void setInterviewerses(Set<InterviewersTO> interviewerses) {
		this.interviewerses = interviewerses;
	}

	
	
}
