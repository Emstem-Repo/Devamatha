package com.kp.cms.to.hostel;

public class HostelLeaveApprovalTo {
	private int id;
	private int hlAdmissionId;
	private String registerNo;
	private String name;
	private String className;
	private String dateAndTimeFrom;
	private String dateAndTimeTo;
	private int noOfLeaveApplications;
	private int noOfLeaveApproval; 
	private int noOfLeaveRejected;
	private int noOfLeaveCancelled;
	private String checked;
	//private int count;
	private String mobileNo;
	private String emailId;
	private String status;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRegisterNo() {
		return registerNo;
	}
	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getDateAndTimeFrom() {
		return dateAndTimeFrom;
	}
	public void setDateAndTimeFrom(String dateAndTimeFrom) {
		this.dateAndTimeFrom = dateAndTimeFrom;
	}
	public String getDateAndTimeTo() {
		return dateAndTimeTo;
	}
	public void setDateAndTimeTo(String dateAndTimeTo) {
		this.dateAndTimeTo = dateAndTimeTo;
	}
	public int getNoOfLeaveApplications() {
		return noOfLeaveApplications;
	}
	public void setNoOfLeaveApplications(int noOfLeaveApplications) {
		this.noOfLeaveApplications = noOfLeaveApplications;
	}
	public int getNoOfLeaveApproval() {
		return noOfLeaveApproval;
	}
	public void setNoOfLeaveApproval(int noOfLeaveApproval) {
		this.noOfLeaveApproval = noOfLeaveApproval;
	}
	public int getNoOfLeaveRejected() {
		return noOfLeaveRejected;
	}
	public void setNoOfLeaveRejected(int noOfLeaveRejected) {
		this.noOfLeaveRejected = noOfLeaveRejected;
	}
	public int getNoOfLeaveCancelled() {
		return noOfLeaveCancelled;
	}
	public void setNoOfLeaveCancelled(int noOfLeaveCancelled) {
		this.noOfLeaveCancelled = noOfLeaveCancelled;
	}
	public int getHlAdmissionId() {
		return hlAdmissionId;
	}
	public void setHlAdmissionId(int hlAdmissionId) {
		this.hlAdmissionId = hlAdmissionId;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	/*public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}*/
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
