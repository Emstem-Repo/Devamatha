package com.kp.cms.to.attendance;

import java.util.Date;

public class ApproveLeaveTO {

	private String attendenceId;

	private String leaveTypeId;

	private Date startDate;

	private Date endDate;

	private Date startTime;

	private Date endTime;
	
	private String registerNo;
	
	private String rollNo;
	
	private int startPeriod;
	private int endPeriod;
	
	private String activityId;
	
	private String attMainId;
	private String AttStudentId;
	private Boolean isCocurricularAttendance;

	public String getAttendenceId() {
		return attendenceId;
	}

	public void setAttendenceId(String attendenceId) {
		this.attendenceId = attendenceId;
	}

	public String getLeaveTypeId() {
		return leaveTypeId;
	}

	public void setLeaveTypeId(String leaveTypeId) {
		this.leaveTypeId = leaveTypeId;
	}

	


	public String getRegisterNo() {
		return registerNo;
	}

	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}

	public String getRollNo() {
		return rollNo;
	}

	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the startPeriod
	 */
	public int getStartPeriod() {
		return startPeriod;
	}

	/**
	 * @param startPeriod the startPeriod to set
	 */
	public void setStartPeriod(int startPeriod) {
		this.startPeriod = startPeriod;
	}

	/**
	 * @return the endPeriod
	 */
	public int getEndPeriod() {
		return endPeriod;
	}

	/**
	 * @param endPeriod the endPeriod to set
	 */
	public void setEndPeriod(int endPeriod) {
		this.endPeriod = endPeriod;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getAttMainId() {
		return attMainId;
	}

	public void setAttMainId(String attMainId) {
		this.attMainId = attMainId;
	}

	public String getAttStudentId() {
		return AttStudentId;
	}

	public void setAttStudentId(String attStudentId) {
		AttStudentId = attStudentId;
	}

	public Boolean getIsCocurricularAttendance() {
		return isCocurricularAttendance;
	}

	public void setIsCocurricularAttendance(Boolean isCocurricularAttendance) {
		this.isCocurricularAttendance = isCocurricularAttendance;
	}
	
	
}
