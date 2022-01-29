package com.kp.cms.to.admin;

public class AttendanceGrpahData {
	private String monthName;
	private String totalPresent;
	private String totalAbsent;
	private String percentage;
	private String totalConduct;
	private Integer monthNo;
	
	
	public Integer getMonthNo() {
		return monthNo;
	}
	public void setMonthNo(Integer monthNo) {
		this.monthNo = monthNo;
	}
	public String getTotalPresent() {
		return totalPresent;
	}
	public void setTotalPresent(String totalPresent) {
		this.totalPresent = totalPresent;
	}
	public String getTotalAbsent() {
		return totalAbsent;
	}
	public void setTotalAbsent(String totalAbsent) {
		this.totalAbsent = totalAbsent;
	}
	
	public String getTotalConduct() {
		return totalConduct;
	}
	public void setTotalConduct(String totalConduct) {
		this.totalConduct = totalConduct;
	}
	public String getMonthName() {
		return monthName;
	}
	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}
	public String getPercentage() {
		return percentage;
	}
	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
	
	

}
