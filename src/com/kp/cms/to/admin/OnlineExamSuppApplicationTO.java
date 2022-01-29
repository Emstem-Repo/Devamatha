package com.kp.cms.to.admin;

public class OnlineExamSuppApplicationTO {
	
	private String id;
	private int onlinePaymentId;
	private String studentId;
	private String stdClassId;
	private double totalFees;
	private String appliedDate;
	private String venue;
	private String time;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getOnlinePaymentId() {
		return onlinePaymentId;
	}
	public void setOnlinePaymentId(int onlinePaymentId) {
		this.onlinePaymentId = onlinePaymentId;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	
	public double getTotalFees() {
		return totalFees;
	}
	public void setTotalFees(double totalFees) {
		this.totalFees = totalFees;
	}
	public String getAppliedDate() {
		return appliedDate;
	}
	public void setAppliedDate(String appliedDate) {
		this.appliedDate = appliedDate;
	}
	public String getVenue() {
		return venue;
	}
	public void setVenue(String venue) {
		this.venue = venue;
	}
	public String getStdClassId() {
		return stdClassId;
	}
	public void setStdClassId(String stdClassId) {
		this.stdClassId = stdClassId;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

}
