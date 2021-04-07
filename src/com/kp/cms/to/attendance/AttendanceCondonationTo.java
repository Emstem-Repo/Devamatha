package com.kp.cms.to.attendance;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;

public class AttendanceCondonationTo implements Comparable<AttendanceCondonationTo> {
	
	private Integer id;
	private String studentName;
	private String registerNo;
	private String rollNo;
	private String previousPercentage;
	private String addedPercentage;
	private String totalPercentage;
	private Integer studentId;
	private Integer classeId;
	private String className;
	private Integer semester;
	private Boolean isOver;
	private String displayMsg;
	
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getRegisterNo() {
		return registerNo;
	}
	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}
	public String getRollNo() {
		return rollNo;
	}
	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}
	
	public String getAddedPercentage() {
		return addedPercentage;
	}
	public void setAddedPercentage(String addedPercentage) {
		this.addedPercentage = addedPercentage;
	}
	public String getTotalPercentage() {
		return totalPercentage;
	}
	public void setTotalPercentage(String totalPercentage) {
		this.totalPercentage = totalPercentage;
	}
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}
	public Integer getStudentId() {
		return studentId;
	}
	public void setClasseId(Integer classeId) {
		this.classeId = classeId;
	}
	public Integer getClasseId() {
		return classeId;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getId() {
		return id;
	}
	@Override
	public int compareTo(AttendanceCondonationTo to) {
		
		if(to.rollNo!=null && rollNo!=null ){
			return rollNo.compareTo(to.rollNo);
		}else{
			return 1;
		}
		
		
	}
	public void setPreviousPercentage(String previousPercentage) {
		this.previousPercentage = previousPercentage;
	}
	public String getPreviousPercentage() {
		return previousPercentage;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public Integer getSemester() {
		return semester;
	}
	public void setSemester(Integer semester) {
		this.semester = semester;
	}
	public void setIsOver(Boolean isOver) {
		this.isOver = isOver;
	}
	public Boolean getIsOver() {
		return isOver;
	}
	public void setDisplayMsg(String displayMsg) {
		this.displayMsg = displayMsg;
	}
	public String getDisplayMsg() {
		return displayMsg;
	}
	
	
	

}
