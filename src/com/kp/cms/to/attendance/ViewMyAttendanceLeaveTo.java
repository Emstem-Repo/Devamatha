package com.kp.cms.to.attendance;

import java.util.Map;

public class ViewMyAttendanceLeaveTo {
	private int id;
	private String attnLeaveDate;
	private String className;
	private String subjectName;
	private String periodName;
	private String studentRegNo;
	private String checked;
	private String tempChecked;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAttnLeaveDate() {
		return attnLeaveDate;
	}
	public void setAttnLeaveDate(String attnLeaveDate) {
		this.attnLeaveDate = attnLeaveDate;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getPeriodName() {
		return periodName;
	}
	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}
	public String getStudentRegNo() {
		return studentRegNo;
	}
	public void setStudentRegNo(String studentRegNo) {
		this.studentRegNo = studentRegNo;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getTempChecked() {
		return tempChecked;
	}
	public void setTempChecked(String tempChecked) {
		this.tempChecked = tempChecked;
	}
	
}
