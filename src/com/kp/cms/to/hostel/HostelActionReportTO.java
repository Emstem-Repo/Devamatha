package com.kp.cms.to.hostel;

import java.io.Serializable;

public class HostelActionReportTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String hostelName;
	private String hostelId;
	private String startDate;
	private String endDate;
	private String disciplineType;
	private String leaveType;
	private String typeId;
	private String regNo;
	private String staffId;
	private String name;
	private String daysOfAbsent;
	private String daysOfApproved;
	private String ifPrint;
	private int srlNo;
	private int sizeOfActionReports;
	
	
	public int getSizeOfActionReports() {
		return sizeOfActionReports;
	}
	public void setSizeOfActionReports(int sizeOfActionReports) {
		this.sizeOfActionReports = sizeOfActionReports;
	}

	public int getSrlNo() {
		return srlNo;
	}

	public void setSrlNo(int srlNo) {
		this.srlNo = srlNo;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHostelName() {
		return hostelName;
	}

	public void setHostelName(String hostelName) {
		this.hostelName = hostelName;
	}

	public String getHostelId() {
		return hostelId;
	}

	public void setHostelId(String hostelId) {
		this.hostelId = hostelId;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getDaysOfAbsent() {
		return daysOfAbsent;
	}

	public void setDaysOfAbsent(String daysOfAbsent) {
		this.daysOfAbsent = daysOfAbsent;
	}

	public String getDaysOfApproved() {
		return daysOfApproved;
	}

	public void setDaysOfApproved(String daysOfApproved) {
		this.daysOfApproved = daysOfApproved;
	}

	public String getIfPrint() {
		return ifPrint;
	}

	public void setIfPrint(String ifPrint) {
		this.ifPrint = ifPrint;
	}

	public String getDisciplineType() {
		return disciplineType;
	}

	public void setDisciplineType(String disciplineType) {
		this.disciplineType = disciplineType;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}
}
