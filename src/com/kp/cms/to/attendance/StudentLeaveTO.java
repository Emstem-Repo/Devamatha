package com.kp.cms.to.attendance;

import java.util.Date;

public class StudentLeaveTO {
		
	
	private int id;
	private String leaveType;
	private Date startDate;
	private Date endDate;
	private String startPeriod;
	private String endPeriod;
	private String reason;
	private String rollOrRegNos;
	private int classSchemewiseId;
	private String className;
	// for leave report
	private String stDate;
	private String edDate;
	
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}
	/**
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}
	/**
	 * @return the leaveType
	 */
	public String getLeaveType() {
		return leaveType;
	}
	/**
	 * @param leaveType the leaveType to set
	 */
	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}
	/**
	 * @return the startPeriod
	 */
	public String getStartPeriod() {
		return startPeriod;
	}
	/**
	 * @param startPeriod the startPeriod to set
	 */
	public void setStartPeriod(String startPeriod) {
		this.startPeriod = startPeriod;
	}
	/**
	 * @return the endPeriod
	 */
	public String getEndPeriod() {
		return endPeriod;
	}
	/**
	 * @param endPeriod the endPeriod to set
	 */
	public void setEndPeriod(String endPeriod) {
		this.endPeriod = endPeriod;
	}
	/**
	 * @return the classSchemewiseId
	 */
	public int getClassSchemewiseId() {
		return classSchemewiseId;
	}
	/**
	 * @param classSchemewiseId the classSchemewiseId to set
	 */
	public void setClassSchemewiseId(int classSchemewiseId) {
		this.classSchemewiseId = classSchemewiseId;
	}
	/**
	 * @return the rollOrRegNos
	 */
	public String getRollOrRegNos() {
		return rollOrRegNos;
	}
	/**
	 * @param rollOrRegNos the rollOrRegNos to set
	 */
	public void setRollOrRegNos(String rollOrRegNos) {
		this.rollOrRegNos = rollOrRegNos;
	}
	public String getStDate() {
		return stDate;
	}
	public void setStDate(String stDate) {
		this.stDate = stDate;
	}
	public String getEdDate() {
		return edDate;
	}
	public void setEdDate(String edDate) {
		this.edDate = edDate;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
}
