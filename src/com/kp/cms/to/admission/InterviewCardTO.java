package com.kp.cms.to.admission;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.to.admin.EmployeeTO;

public class InterviewCardTO {

	private int id;
	//private EmployeeTO employeeByCreatedBy;
	private InterviewScheduleTO interview;
	private AdmApplnTO admApplnTO;
	//private EmployeeTO employeeByModifiedBy;
	private String time;
	private String endtime;
	private Date startTime;
	private Date endTime;
	private Date date;
	private Date createdDate;
	private Date lastModifiedDate;
	private String interviewType;
	private String issueDate;
	private Integer interviewer;
	private String collegeName;
	private String collegeAddress;
	private String collegeAddress1;
	private String collegeAddress2;
	private String intCardContent;
	// added for upload interviewStatus - if date,time is specified needs to be done same as selection Process workflow-Smitha
	private int admApplnId;
	private String venue;
	private String interviewDate;
	private int interviewPrgCrsId;
	private int appliedYear;
	private AdmAppln admAppln;
	
	
	public String getIntCardContent() {
		return intCardContent;
	}
	public void setIntCardContent(String intCardContent) {
		this.intCardContent = intCardContent;
	}
	public String getCollegeName() {
		return collegeName;
	}
	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}
	public String getCollegeAddress() {
		return collegeAddress;
	}
	public void setCollegeAddress(String collegeAddress) {
		this.collegeAddress = collegeAddress;
	}
	public Integer getInterviewer() {
		return interviewer;
	}
	public void setInterviewer(Integer interviewer) {
		this.interviewer = interviewer;
	}
	private Set<InterviewProcessTO> interviewProcesses = new HashSet<InterviewProcessTO>(
			0);
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public InterviewScheduleTO getInterview() {
		return interview;
	}
	public void setInterview(InterviewScheduleTO interview) {
		this.interview = interview;
	}
	

	public AdmApplnTO getAdmApplnTO() {
		return admApplnTO;
	}
	public void setAdmApplnTO(AdmApplnTO admApplnTO) {
		this.admApplnTO = admApplnTO;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
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
	public Set<InterviewProcessTO> getInterviewProcesses() {
		return interviewProcesses;
	}
	public void setInterviewProcesses(Set<InterviewProcessTO> interviewProcesses) {
		this.interviewProcesses = interviewProcesses;
	}
	public String getInterviewType() {
		return interviewType;
	}
	public void setInterviewType(String interviewType) {
		this.interviewType = interviewType;
	}
	public String getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}
	public String getCollegeAddress1() {
		return collegeAddress1;
	}
	public void setCollegeAddress1(String collegeAddress1) {
		this.collegeAddress1 = collegeAddress1;
	}
	public String getCollegeAddress2() {
		return collegeAddress2;
	}
	public void setCollegeAddress2(String collegeAddress2) {
		this.collegeAddress2 = collegeAddress2;
	}
	public int getAdmApplnId() {
		return admApplnId;
	}
	public void setAdmApplnId(int admApplnId) {
		this.admApplnId = admApplnId;
	}
	public String getVenue() {
		return venue;
	}
	public void setVenue(String venue) {
		this.venue = venue;
	}
	public String getInterviewDate() {
		return interviewDate;
	}
	public void setInterviewDate(String interviewDate) {
		this.interviewDate = interviewDate;
	}
	public int getInterviewPrgCrsId() {
		return interviewPrgCrsId;
	}
	public void setInterviewPrgCrsId(int interviewPrgCrsId) {
		this.interviewPrgCrsId = interviewPrgCrsId;
	}
	public int getAppliedYear() {
		return appliedYear;
	}
	public void setAppliedYear(int appliedYear) {
		this.appliedYear = appliedYear;
	}
	public AdmAppln getAdmAppln() {
		return admAppln;
	}
	public void setAdmAppln(AdmAppln admAppln) {
		this.admAppln = admAppln;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	
	
}
