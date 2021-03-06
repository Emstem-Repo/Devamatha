package com.kp.cms.bo.admin;

// Generated May 18, 2009 3:06:44 PM by Hibernate Tools 3.2.0.b9

import java.util.Date;

/**
 * LeaveDetails generated by hbm2java
 */
public class LeaveDetails implements java.io.Serializable {

	private int id;
	private LeaveType leaveType;
	private Student student;
	private Date fromDate;
	private Date toDate;
	private Integer fromPeriod;
	private Integer toPeriod;

	public LeaveDetails() {
	}

	public LeaveDetails(int id) {
		this.id = id;
	}

	public LeaveDetails(int id, LeaveType leaveType, Student student,
			Date fromDate, Date toDate, Integer fromPeriod, Integer toPeriod) {
		this.id = id;
		this.leaveType = leaveType;
		this.student = student;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.fromPeriod = fromPeriod;
		this.toPeriod = toPeriod;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LeaveType getLeaveType() {
		return this.leaveType;
	}

	public void setLeaveType(LeaveType leaveType) {
		this.leaveType = leaveType;
	}

	public Student getStudent() {
		return this.student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Date getFromDate() {
		return this.fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return this.toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Integer getFromPeriod() {
		return this.fromPeriod;
	}

	public void setFromPeriod(Integer fromPeriod) {
		this.fromPeriod = fromPeriod;
	}

	public Integer getToPeriod() {
		return this.toPeriod;
	}

	public void setToPeriod(Integer toPeriod) {
		this.toPeriod = toPeriod;
	}

}
