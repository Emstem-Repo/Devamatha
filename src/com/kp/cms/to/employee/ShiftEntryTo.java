package com.kp.cms.to.employee;

public class ShiftEntryTo {

	private int id;
	private String day;
	private String timeIn;
	private String timeInMin;
	private String timeOutMin;
	private String timeOut;
	private String employeeId;
	private String empName;
	private String fingerFrintId;
	
	public int getId() {
		return id;
	}
	public void setId(int i) {
		this.id = i;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getTimeIn() {
		return timeIn;
	}
	public void setTimeIn(String timeIn) {
		this.timeIn = timeIn;
	}
	public String getTimeOut() {
		return timeOut;
	}
	public void setTimeOut(String timeOut) {
		this.timeOut = timeOut;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getTimeInMin() {
		return timeInMin;
	}
	public void setTimeInMin(String timeInMin) {
		this.timeInMin = timeInMin;
	}
	public String getTimeOutMin() {
		return timeOutMin;
	}
	public void setTimeOutMin(String timeOutMin) {
		this.timeOutMin = timeOutMin;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getFingerFrintId() {
		return fingerFrintId;
	}
	public void setFingerFrintId(String fingerFrintId) {
		this.fingerFrintId = fingerFrintId;
	}
	
}
