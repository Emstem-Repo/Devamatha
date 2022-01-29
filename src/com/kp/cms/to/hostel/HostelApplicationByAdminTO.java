package com.kp.cms.to.hostel;

public class HostelApplicationByAdminTO {
	
	private int id;
	private String hostelName;
	private String studentName;
	private String staffName;
	private String studentOrStaffName;
	private String studentOrStaffPermanentAddressLine;
	private String studentOrStaffCurrentAddressLine;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getHostelName() {
		return hostelName;
	}
	public void setHostelName(String hostelName) {
		this.hostelName = hostelName;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	
	public String getStudentOrStaffName() {
		return studentOrStaffName;
	}
	public void setStudentOrStaffName(String studentOrStaffName) {
		this.studentOrStaffName = studentOrStaffName;
	}
	public String getStudentOrStaffPermanentAddressLine() {
		return studentOrStaffPermanentAddressLine;
	}
	public void setStudentOrStaffPermanentAddressLine(
			String studentOrStaffPermanentAddressLine) {
		this.studentOrStaffPermanentAddressLine = studentOrStaffPermanentAddressLine;
	}
	public String getStudentOrStaffCurrentAddressLine() {
		return studentOrStaffCurrentAddressLine;
	}
	public void setStudentOrStaffCurrentAddressLine(
			String studentOrStaffCurrentAddressLine) {
		this.studentOrStaffCurrentAddressLine = studentOrStaffCurrentAddressLine;
	}
	
	
}
