package com.kp.cms.to.hostel;

public class HostelStudentExtractTo {
	
	private String id;
	private String regNo;
	private String firstName;
	private String middleName;
	private String lastName;
	private String roomNo;
	private String applicantName;
	private String className;
	private String leaveType;
	
	private int diffDates;
	private int hlid;
	
	public String getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}
	
	public String getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}
	public int getHlid() {
		return hlid;
	}
	public void setHlid(int hlid) {
		this.hlid = hlid;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getApplicantName() {
		return applicantName;
	}
	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}
	public int getDiffDates() {
		return diffDates;
	}
	public void setDiffDates(int diffDates) {
		this.diffDates = diffDates;
	}
	
}
