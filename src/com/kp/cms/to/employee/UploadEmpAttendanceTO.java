package com.kp.cms.to.employee;

import java.io.Serializable;

public class UploadEmpAttendanceTO implements Serializable {
	
	private int empId;
	private int fingerTypeId;
	private int empTypeId;
	private String terminalId;
	private String date;
	private String time;
	private String status;
	
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public int getFingerTypeId() {
		return fingerTypeId;
	}
	public void setFingerTypeId(int fingerTypeId) {
		this.fingerTypeId = fingerTypeId;
	}
	public int getEmpTypeId() {
		return empTypeId;
	}
	public void setEmpTypeId(int empTypeId) {
		this.empTypeId = empTypeId;
	}
	public String getTerminalId() {
		return terminalId;
	}
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
