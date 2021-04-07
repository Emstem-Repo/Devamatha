package com.kp.cms.to.reports;

import java.io.Serializable;

public class StudentsAttendanceReportTO implements Serializable,Comparable<StudentsAttendanceReportTO> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String classId;
	private String className;
	private String registerNumber;
	private String classesHeld;
	private String classesAttended;
	private String studentName;
	private String percentage;
	private boolean addLeaves;
	private int studentId;
	private String checked;
	private Boolean tempChecked;
	private String remarks;
	private String status;
	private int id;
	
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getRegisterNumber() {
		return registerNumber;
	}
	public void setRegisterNumber(String registerNumber) {
		this.registerNumber = registerNumber;
	}
	public String getClassesHeld() {
		return classesHeld;
	}
	public void setClassesHeld(String classesHeld) {
		this.classesHeld = classesHeld;
	}
	public String getClassesAttended() {
		return classesAttended;
	}
	public void setClassesAttended(String classesAttended) {
		this.classesAttended = classesAttended;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getPercentage() {
		return percentage;
	}
	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
	public boolean isAddLeaves() {
		return addLeaves;
	}
	public void setAddLeaves(boolean addLeaves) {
		this.addLeaves = addLeaves;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Boolean getTempChecked() {
		return tempChecked;
	}
	public void setTempChecked(Boolean tempChecked) {
		this.tempChecked = tempChecked;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public int compareTo(StudentsAttendanceReportTO arg0) {
		if(arg0!=null && this!=null && arg0.getRegisterNumber()!=null && this.getRegisterNumber()!=null){
			return this.getRegisterNumber().compareTo(arg0.getRegisterNumber());
		}else
			return 0;
	} 
}