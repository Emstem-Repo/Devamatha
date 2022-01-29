package com.kp.cms.to.examallotment;

import java.io.Serializable;
import java.util.Date;

public class ExamRoomAllotmentTO implements Serializable {
	
	private int id;
	private String courseName;
	private int courseId;
	private String semNo;
	private String checked;
	private int poolId;
	private String poolName;
	private String specializationId;
	
	public int getId() {
		return id;
	}
	public String getCourseName() {
		return courseName;
	}
	public int getCourseId() {
		return courseId;
	}
	public String getSemNo() {
		return semNo;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public void setSemNo(String semNo) {
		this.semNo = semNo;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public int getPoolId() {
		return poolId;
	}
	public void setPoolId(int poolId) {
		this.poolId = poolId;
	}
	public String getPoolName() {
		return poolName;
	}
	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}
	public String getSpecializationId() {
		return specializationId;
	}
	public void setSpecializationId(String specializationId) {
		this.specializationId = specializationId;
	}
	
	
	
	
	
}