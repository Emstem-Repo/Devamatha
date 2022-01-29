package com.kp.cms.to.hostel;

import java.io.Serializable;

public class HostelerDatabaseTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String hostelName;
	private int hostelId;
	private int studentId;
	private String regNo;
	private String studentName;
	private String roomNo;
	private int roomTypeId;
	private String roomTypeName;
	private boolean isStaff;
	private int hlformId;
	private String floorNo;
	
	public int getHlformId() {
		return hlformId;
	}
	public void setHlformId(int hlformId) {
		this.hlformId = hlformId;
	}
	public void setIsStaff(boolean isStaff) {
		this.isStaff = isStaff;
	}
	public boolean getIsStaff() {
		return isStaff;
	}
	public String getHostelName() {
		return hostelName;
	}
	public int getHostelId() {
		return hostelId;
	}
	public int getStudentId() {
		return studentId;
	}
	public String getRegNo() {
		return regNo;
	}
	public String getStudentName() {
		return studentName;
	}
	public String getRoomNo() {
		return roomNo;
	}
	public int getRoomTypeId() {
		return roomTypeId;
	}
	public String getRoomTypeName() {
		return roomTypeName;
	}
	public void setHostelName(String hostelName) {
		this.hostelName = hostelName;
	}
	public void setHostelId(int hostelId) {
		this.hostelId = hostelId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}
	public void setRoomTypeId(int roomTypeId) {
		this.roomTypeId = roomTypeId;
	}
	public void setRoomTypeName(String roomTypeName) {
		this.roomTypeName = roomTypeName;
	}
	public String getFloorNo() {
		return floorNo;
	}
	public void setFloorNo(String floorNo) {
		this.floorNo = floorNo;
	}
	public void setStaff(boolean isStaff) {
		this.isStaff = isStaff;
	}

}
