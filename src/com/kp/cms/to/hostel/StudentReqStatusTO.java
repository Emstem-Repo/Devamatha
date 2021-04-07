package com.kp.cms.to.hostel;

public class StudentReqStatusTO {
	private int id;
	private String applicationNo;
	private String appliedDate;
	private String hostelReqNo;
	private String hostelName;
	private String reqRoomType;
	private String approvedRoomType;
	private String status;
	private String fees;
	private String studentName;
	private String approvedHostelName;
	private String name;
	
	public String getApplicationNo() {
		return applicationNo;
	}
	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}
	public String getAppliedDate() {
		return appliedDate;
	}
	public void setAppliedDate(String appliedDate) {
		this.appliedDate = appliedDate;
	}
	public String getHostelReqNo() {
		return hostelReqNo;
	}
	public void setHostelReqNo(String hostelReqNo) {
		this.hostelReqNo = hostelReqNo;
	}
	public String getHostelName() {
		return hostelName;
	}
	public void setHostelName(String hostelName) {
		this.hostelName = hostelName;
	}
	public String getReqRoomType() {
		return reqRoomType;
	}
	public void setReqRoomType(String reqRoomType) {
		this.reqRoomType = reqRoomType;
	}
	public String getApprovedRoomType() {
		return approvedRoomType;
	}
	public void setApprovedRoomType(String approvedRoomType) {
		this.approvedRoomType = approvedRoomType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFees() {
		return fees;
	}
	public void setFees(String fees) {
		this.fees = fees;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getApprovedHostelName() {
		return approvedHostelName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setApprovedHostelName(String approvedHostelName) {
		this.approvedHostelName = approvedHostelName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}