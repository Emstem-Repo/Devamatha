package com.kp.cms.to.hostel;

public class VReqStatusTo {

	private String hostelName;
	private String appliedHostel;
	private String appliedRoom;
	private String approvedRoom; 
	private String applino;
	private String reqno;
	private String name;
	private double  fees;
	private String appliedDate; 
	private String staff;
	
	
	public String getStaff() {
		return staff;
	}
	public void setStaff(String staff) {
		this.staff = staff;
	}
	public String getApplino() {
		return applino;
	}
	public void setApplino(String applino) {
		this.applino = applino;
	}
	public String getReqno() {
		return reqno;
	}
	public void setReqno(String reqno) {
		this.reqno = reqno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setFees(double fees) {
		this.fees = fees;
	}
	public String getHostelName() {
		return hostelName;
	}
	public void setHostelName(String hostelName) {
		this.hostelName = hostelName;
	}
	public String getAppliedHostel() {
		return appliedHostel;
	}
	public void setAppliedHostel(String appliedHostel) {
		this.appliedHostel = appliedHostel;
	}
	public String getAppliedRoom() {
		return appliedRoom;
	}
	public void setAppliedRoom(String appliedRoom) {
		this.appliedRoom = appliedRoom;
	}
	public String getApprovedRoom() {
		return approvedRoom;
	}
	public void setApprovedRoom(String approvedRoom) {
		this.approvedRoom = approvedRoom;
	}
	public String getAppliedDate() {
		return appliedDate;
	}
	public void setAppliedDate(String appliedDate) {
		this.appliedDate = appliedDate;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getFees() {
		return Fees;
	}
	public void setFees(String fees) {
		Fees = fees;
	}
	public String getRequisitionNo() {
		return requisitionNo;
	}
	public void setRequisitionNo(String requisitionNo) {
		this.requisitionNo = requisitionNo;
	}
	private String Status;
	private String  studentName;
	private String  Fees;
	private String requisitionNo;
}
