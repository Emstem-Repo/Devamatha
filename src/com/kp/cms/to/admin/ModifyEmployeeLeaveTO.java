package com.kp.cms.to.admin;

import java.util.List;

import com.kp.cms.to.hostel.LeaveTypeTo;


public class ModifyEmployeeLeaveTO {
	
	private int id;
	private String academicYear;
	private String isExemption;
	private String empCode;
	private String fingerPrintId;
	private String leaveTypeId;
	private String startDate;
	private String endDate;
	private String isHalfday;
	private String isAm;
	private String employeeName;
	private String designationName;
	private String departmentName;
	private String employeeId;
	private Boolean halfDayDisplay;
	private Boolean amDisplay;
	private List<LeaveTypeTo> leaveTypes;
	private String reason;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public String getIsExemption() {
		return isExemption;
	}
	public void setIsExemption(String isExemption) {
		this.isExemption = isExemption;
	}
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public String getFingerPrintId() {
		return fingerPrintId;
	}
	public void setFingerPrintId(String fingerPrintId) {
		this.fingerPrintId = fingerPrintId;
	}
	public String getLeaveTypeId() {
		return leaveTypeId;
	}
	public void setLeaveTypeId(String leaveTypeId) {
		this.leaveTypeId = leaveTypeId;
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
	public String getIsHalfday() {
		return isHalfday;
	}
	public void setIsHalfday(String isHalfday) {
		this.isHalfday = isHalfday;
	}
	public String getIsAm() {
		return isAm;
	}
	public void setIsAm(String isAm) {
		this.isAm = isAm;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getDesignationName() {
		return designationName;
	}
	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public Boolean getHalfDayDisplay() {
		return halfDayDisplay;
	}
	public void setHalfDayDisplay(Boolean halfDayDisplay) {
		this.halfDayDisplay = halfDayDisplay;
	}
	public Boolean getAmDisplay() {
		return amDisplay;
	}
	public void setAmDisplay(Boolean amDisplay) {
		this.amDisplay = amDisplay;
	}
	public List<LeaveTypeTo> getLeaveTypes() {
		return leaveTypes;
	}
	public void setLeaveTypes(List<LeaveTypeTo> leaveTypes) {
		this.leaveTypes = leaveTypes;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	
	
	
}
