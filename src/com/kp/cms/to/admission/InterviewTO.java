package com.kp.cms.to.admission;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.kp.cms.to.admin.EmployeeTO;

public class InterviewTO {

	private int id;
	//private EmployeeTO employeeByCreatedBy;
	//private EmployeeTO employeeByModifiedBy;
	private InterviewProgramCourseTO interviewProgramCourse;
	private Date createdDate;
	private Date lastModifiedDate;
	private int year;
	private Integer noOfCandidates;
	private Integer noOfInterviewers;
	private Set<InterviewScheduleTO> interviewSchedules = new HashSet<InterviewScheduleTO>(
			0);
	private Set<InterviewCardTO> interviewCards = new HashSet<InterviewCardTO>(0);
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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

	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public Integer getNoOfCandidates() {
		return noOfCandidates;
	}
	public void setNoOfCandidates(Integer noOfCandidates) {
		this.noOfCandidates = noOfCandidates;
	}
	public Integer getNoOfInterviewers() {
		return noOfInterviewers;
	}
	public void setNoOfInterviewers(Integer noOfInterviewers) {
		this.noOfInterviewers = noOfInterviewers;
	}
	public InterviewProgramCourseTO getInterviewProgramCourse() {
		return interviewProgramCourse;
	}
	public void setInterviewProgramCourse(
			InterviewProgramCourseTO interviewProgramCourse) {
		this.interviewProgramCourse = interviewProgramCourse;
	}
	public Set<InterviewScheduleTO> getInterviewSchedules() {
		return interviewSchedules;
	}
	public void setInterviewSchedules(Set<InterviewScheduleTO> interviewSchedules) {
		this.interviewSchedules = interviewSchedules;
	}
	public Set<InterviewCardTO> getInterviewCards() {
		return interviewCards;
	}
	public void setInterviewCards(Set<InterviewCardTO> interviewCards) {
		this.interviewCards = interviewCards;
	}

	
	
	
}
