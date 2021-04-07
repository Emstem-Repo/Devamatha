package com.kp.cms.to.admission;

import java.io.Serializable;

public class InterviewTimeChangeTO implements Serializable{
	
	private int interviewTypeId;
	private String interviewType;
	private String applicationId;
	private String applicationNo;
	private String applicantName;
	private String appliedYear;
	private String courseId;
	private String courseName;
	private String interviewDate;
	private String timeID;
	private String startTimeHours;
	private String startTimeMins;
	private String endTimeHours;
	private String endTimeMins;
	private String interviewTime;
	private String email;
	private InterviewProgramCourseTO interviewProgramCrouCourseTO;
	private int interviewers;
	private String timeInterval;
	private String venue;
	private int interviewId;
	private String oldInterviewTime;
	private String oldInterviewDate;
	private int appNoForm;
	private int appNoTo;
	private String totalRounds;
	private String reportingTime;
	/* code added by sudhir */
	private String eAdmitCardGenerateOn;
	private String selectionProcessId;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public InterviewProgramCourseTO getInterviewProgramCrouCourseTO() {
		return interviewProgramCrouCourseTO;
	}
	public void setInterviewProgramCrouCourseTO(
			InterviewProgramCourseTO interviewProgramCrouCourseTO) {
		this.interviewProgramCrouCourseTO = interviewProgramCrouCourseTO;
	}
	public String getInterviewTime() {
		return interviewTime;
	}
	public void setInterviewTime(String interviewTime) {
		this.interviewTime = interviewTime;
	}
	public int getInterviewTypeId() {
		return interviewTypeId;
	}
	public void setInterviewTypeId(int interviewTypeId) {
		this.interviewTypeId = interviewTypeId;
	}
	public String getInterviewType() {
		return interviewType;
	}
	public void setInterviewType(String interviewType) {
		this.interviewType = interviewType;
	}
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	public String getApplicationNo() {
		return applicationNo;
	}
	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}
	public String getApplicantName() {
		return applicantName;
	}
	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}
	public String getAppliedYear() {
		return appliedYear;
	}
	public void setAppliedYear(String appliedYear) {
		this.appliedYear = appliedYear;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getInterviewDate() {
		return interviewDate;
	}
	public void setInterviewDate(String interviewDate) {
		this.interviewDate = interviewDate;
	}

	public String getTimeID() {
		return timeID;
	}
	public void setTimeID(String timeID) {
		this.timeID = timeID;
	}
	public String getStartTimeHours() {
		return startTimeHours;
	}
	public void setStartTimeHours(String startTimeHours) {
		this.startTimeHours = startTimeHours;
	}
	public String getStartTimeMins() {
		return startTimeMins;
	}
	public void setStartTimeMins(String startTimeMins) {
		this.startTimeMins = startTimeMins;
	}
	public String getEndTimeHours() {
		return endTimeHours;
	}
	public void setEndTimeHours(String endTimeHours) {
		this.endTimeHours = endTimeHours;
	}
	public String getEndTimeMins() {
		return endTimeMins;
	}
	public void setEndTimeMins(String endTimeMins) {
		this.endTimeMins = endTimeMins;
	}
	public int getInterviewers() {
		return interviewers;
	}
	public void setInterviewers(int interviewers) {
		this.interviewers = interviewers;
	}
	public String getTimeInterval() {
		return timeInterval;
	}
	public void setTimeInterval(String timeInterval) {
		this.timeInterval = timeInterval;
	}
	public String getVenue() {
		return venue;
	}
	public void setVenue(String venue) {
		this.venue = venue;
	}
	public int getInterviewId() {
		return interviewId;
	}
	public void setInterviewId(int interviewId) {
		this.interviewId = interviewId;
	}
	public String getOldInterviewTime() {
		return oldInterviewTime;
	}
	public void setOldInterviewTime(String oldInterviewTime) {
		this.oldInterviewTime = oldInterviewTime;
	}
	public String getOldInterviewDate() {
		return oldInterviewDate;
	}
	public void setOldInterviewDate(String oldInterviewDate) {
		this.oldInterviewDate = oldInterviewDate;
	}
	public int getAppNoForm() {
		return appNoForm;
	}
	public void setAppNoForm(int appNoForm) {
		this.appNoForm = appNoForm;
	}
	public int getAppNoTo() {
		return appNoTo;
	}
	public void setAppNoTo(int appNoTo) {
		this.appNoTo = appNoTo;
	}
	public String getTotalRounds() {
		return totalRounds;
	}
	public void setTotalRounds(String totalRounds) {
		this.totalRounds = totalRounds;
	}
	public String getReportingTime() {
		return reportingTime;
	}
	public void setReportingTime(String reportingTime) {
		this.reportingTime = reportingTime;
	}
	public String geteAdmitCardGenerateOn() {
		return eAdmitCardGenerateOn;
	}
	public void seteAdmitCardGenerateOn(String eAdmitCardGenerateOn) {
		this.eAdmitCardGenerateOn = eAdmitCardGenerateOn;
	}
	public String getSelectionProcessId() {
		return selectionProcessId;
	}
	public void setSelectionProcessId(String selectionProcessId) {
		this.selectionProcessId = selectionProcessId;
	}
	
	
	

}
