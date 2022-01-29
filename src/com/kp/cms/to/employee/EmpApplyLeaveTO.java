package com.kp.cms.to.employee;

import java.io.Serializable;

import com.kp.cms.bo.admin.EmpOnlineLeave;
import com.kp.cms.bo.admin.Employee;

public class EmpApplyLeaveTO implements Serializable{
	private int id;
	private String empLeaveType;
	private String fromDate;
	private String toDate;
	private String reason;
	private String noOfDays;
	private String isHalfDay;
	private String isAm;
	private String status;
	private String employeeName;
	private String employeeId;
	private String checked;
	private Employee employee;
	private int empLeaveTypeId;
	private int year;
	private EmpOnlineLeave onlineLeave;
	private int empId;
	private int empTypeId;
	private String approvedBy;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmpLeaveType() {
		return empLeaveType;
	}
	public void setEmpLeaveType(String empLeaveType) {
		this.empLeaveType = empLeaveType;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getNoOfDays() {
		return noOfDays;
	}
	public void setNoOfDays(String noOfDays) {
		this.noOfDays = noOfDays;
	}
	public String getIsHalfDay() {
		return isHalfDay;
	}
	public void setIsHalfDay(String isHalfDay) {
		this.isHalfDay = isHalfDay;
	}
	public String getIsAm() {
		return isAm;
	}
	public void setIsAm(String isAm) {
		this.isAm = isAm;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public int getEmpLeaveTypeId() {
		return empLeaveTypeId;
	}
	public void setEmpLeaveTypeId(int empLeaveTypeId) {
		this.empLeaveTypeId = empLeaveTypeId;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public EmpOnlineLeave getOnlineLeave() {
		return onlineLeave;
	}
	public void setOnlineLeave(EmpOnlineLeave onlineLeave) {
		this.onlineLeave = onlineLeave;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public int getEmpTypeId() {
		return empTypeId;
	}
	public void setEmpTypeId(int empTypeId) {
		this.empTypeId = empTypeId;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	public String getApprovedBy() {
		return approvedBy;
	}
}
