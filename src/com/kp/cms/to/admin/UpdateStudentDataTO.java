package com.kp.cms.to.admin;

import java.io.Serializable;

public class UpdateStudentDataTO  implements Serializable{
	private int id;
	private String studentName;
	private int studentId;
	private String regNo;
	private String parentMobNo;
	private String className;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	public String getParentMobNo() {
		return parentMobNo;
	}
	public void setParentMobNo(String parentMobNo) {
		this.parentMobNo = parentMobNo;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
	
}
