package com.kp.cms.to.hostel;

import java.io.Serializable;

public class ListOfOccupancyRegisterTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String hostelId;
	private String floorNo;
	private String roomNo;
	private String studentName;
	private String noOfOccupants;
	
	public String getNoOfOccupants() {
		return noOfOccupants;
	}
	public void setNoOfOccupants(String noOfOccupants) {
		this.noOfOccupants = noOfOccupants;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getHostelId() {
		return hostelId;
	}
	public void setHostelId(String hostelId) {
		this.hostelId = hostelId;
	}
	public String getFloorNo() {
		return floorNo;
	}
	public void setFloorNo(String floorNo) {
		this.floorNo = floorNo;
	}
	public String getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}
	
}
