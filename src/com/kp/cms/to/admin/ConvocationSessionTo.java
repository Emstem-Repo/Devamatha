package com.kp.cms.to.admin;


public class ConvocationSessionTo {
	private int id;
	private String date;
	private String amOrpm;
	private String maxGuest;
	private String passAmount;
	private String[] courseIds;
	private String courseNames;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getAmOrpm() {
		return amOrpm;
	}
	public void setAmOrpm(String amOrpm) {
		this.amOrpm = amOrpm;
	}
	public String getMaxGuest() {
		return maxGuest;
	}
	public void setMaxGuest(String maxGuest) {
		this.maxGuest = maxGuest;
	}
	public String getPassAmount() {
		return passAmount;
	}
	public void setPassAmount(String passAmount) {
		this.passAmount = passAmount;
	}
	public String[] getCourseIds() {
		return courseIds;
	}
	public void setCourseIds(String[] courseIds) {
		this.courseIds = courseIds;
	}
	public String getCourseNames() {
		return courseNames;
	}
	public void setCourseNames(String courseNames) {
		this.courseNames = courseNames;
	}
	
}
