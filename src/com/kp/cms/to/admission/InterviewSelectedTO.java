package com.kp.cms.to.admission;

import java.util.Date;

import com.kp.cms.to.admin.EmployeeTO;

public class InterviewSelectedTO {
	private int id;
	//private EmployeeTO employeeByCreatedBy;
	//private EmployeeTO employeeByModifiedBy;
	private InterviewProgramCourseTO interviewProgramCourse;
	private AdmApplnTO admAppln;
	private Boolean isCardGenerated;
	private Date createdDate;
	private Date lastModifiedDate;
	private Boolean isActive;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	public InterviewProgramCourseTO getInterviewProgramCourse() {
		return interviewProgramCourse;
	}
	public void setInterviewProgramCourse(
			InterviewProgramCourseTO interviewProgramCourse) {
		this.interviewProgramCourse = interviewProgramCourse;
	}
	public AdmApplnTO getAdmAppln() {
		return admAppln;
	}
	public void setAdmAppln(AdmApplnTO admAppln) {
		this.admAppln = admAppln;
	}
	public Boolean getIsCardGenerated() {
		return isCardGenerated;
	}
	public void setIsCardGenerated(Boolean isCardGenerated) {
		this.isCardGenerated = isCardGenerated;
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
	
	
}
