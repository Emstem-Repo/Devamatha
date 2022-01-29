package com.kp.cms.to.attendance;

public class ConsolidatedAttendanceSmsTO {
	
	private int id;
	private String courseName;
	private String className;
	private String smsTime;
	private String smdTrigerDate;
	private String classSchemwiseID;
	private Boolean isSmsSent;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getSmsTime() {
		return smsTime;
	}
	public void setSmsTime(String smsTime) {
		this.smsTime = smsTime;
	}
	public String getSmdTrigerDate() {
		return smdTrigerDate;
	}
	public void setSmdTrigerDate(String smdTrigerDate) {
		this.smdTrigerDate = smdTrigerDate;
	}
	public String getClassSchemwiseID() {
		return classSchemwiseID;
	}
	public void setClassSchemwiseID(String classSchemwiseID) {
		this.classSchemwiseID = classSchemwiseID;
	}
	public Boolean getIsSmsSent() {
		return isSmsSent;
	}
	public void setIsSmsSent(Boolean isSmsSent) {
		this.isSmsSent = isSmsSent;
	}
	
	
	

}
