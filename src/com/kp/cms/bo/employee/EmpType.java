package com.kp.cms.bo.employee;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.kp.cms.bo.admin.EmployeeTypeBO;

public class EmpType implements Serializable{
	
	private int id;
	private String name;
	private String timeIn;
	private String timeInEnds;
	private String timeOut;
	private String saturdayTimeOut;
	private String halfDayStartTime;
	private String halfDatyEndTime;
	private String leaveInitializeMonth;
	private Boolean isActive;
	private String createdBy;
	private String lastModifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private Set<EmpLeaveAllotment> empLeaveAllot=new HashSet<EmpLeaveAllotment>(0);
	/**
	 * 
	 */
	public EmpType(){
		
	}
	/**
	 * @param id
	 * @param employeeType
	 * @param timeIn
	 * @param timeInEnds
	 * @param timeOut
	 * @param saturdayTimeOut
	 * @param halfDayStartTime
	 * @param halfDatyEndTime
	 * @param leaveInitializeMonth
	 * @param isActive
	 * @param createdBy
	 * @param lastModifiedBy
	 * @param createdDate
	 * @param lastModifiedDate
	 */
	public EmpType(int id, String name, String timeIn,
			String timeInEnds, String timeOut, String saturdayTimeOut,
			String halfDayStartTime, String halfDatyEndTime,
			String leaveInitializeMonth, Boolean isActive, String createdBy,
			String lastModifiedBy, Date createdDate, Date lastModifiedDate) {
		super();
		this.id = id;
		this.name=name;
		this.timeIn = timeIn;
		this.timeInEnds = timeInEnds;
		this.timeOut = timeOut;
		this.saturdayTimeOut = saturdayTimeOut;
		this.halfDayStartTime = halfDayStartTime;
		this.halfDatyEndTime = halfDatyEndTime;
		this.leaveInitializeMonth = leaveInitializeMonth;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.lastModifiedBy = lastModifiedBy;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
	}
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getTimeIn() {
		return timeIn;
	}
	public String getTimeInEnds() {
		return timeInEnds;
	}
	public String getTimeOut() {
		return timeOut;
	}
	public String getSaturdayTimeOut() {
		return saturdayTimeOut;
	}
	public String getHalfDayStartTime() {
		return halfDayStartTime;
	}
	public String getHalfDatyEndTime() {
		return halfDatyEndTime;
	}
	public String getLeaveInitializeMonth() {
		return leaveInitializeMonth;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public void setTimeIn(String timeIn) {
		this.timeIn = timeIn;
	}
	public void setTimeInEnds(String timeInEnds) {
		this.timeInEnds = timeInEnds;
	}
	public void setTimeOut(String timeOut) {
		this.timeOut = timeOut;
	}
	public void setSaturdayTimeOut(String saturdayTimeOut) {
		this.saturdayTimeOut = saturdayTimeOut;
	}
	public void setHalfDayStartTime(String halfDayStartTime) {
		this.halfDayStartTime = halfDayStartTime;
	}
	public void setHalfDatyEndTime(String halfDatyEndTime) {
		this.halfDatyEndTime = halfDatyEndTime;
	}
	public void setLeaveInitializeMonth(String leaveInitializeMonth) {
		this.leaveInitializeMonth = leaveInitializeMonth;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public Set<EmpLeaveAllotment> getEmpLeaveAllot() {
		return empLeaveAllot;
	}
	public void setEmpLeaveAllot(Set<EmpLeaveAllotment> empLeaveAllot) {
		this.empLeaveAllot = empLeaveAllot;
	}

}
