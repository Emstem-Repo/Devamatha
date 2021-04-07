package com.kp.cms.to.hostel;

import java.io.Serializable;

public class HostelStatusInfoTO implements Serializable{		
	private static final long serialVersionUID = 1L;
	
	private String hostelId;
	private String roomTypeName[];
	private String roomType;
	private String floorNo;
	private String noOfFloors;
	private String noOfRooms;
	private String roomTxnDate;
	private String roomName;
	private String statusType;
	private String returnRoomTypeName;
	
	public String getReturnRoomTypeName() {
		return returnRoomTypeName;
	}

	public void setReturnRoomTypeName(String returnRoomTypeName) {
		this.returnRoomTypeName = returnRoomTypeName;
	}

	public String getRoomTxnDate() {
		return roomTxnDate;
	}

	public void setRoomTxnDate(String roomTxnDate) {
		this.roomTxnDate = roomTxnDate;
	}	
	
	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getStatusType() {
		return statusType;
	}

	public void setStatusType(String statusType) {
		this.statusType = statusType;
	}
	
	public String getNoOfRooms() {
		return noOfRooms;
	}

	public void setNoOfRooms(String noOfRooms) {
		this.noOfRooms = noOfRooms;
	}
	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public String getNoOfFloors() {
		return noOfFloors;
	}

	public void setNoOfFloors(String noOfFloors) {
		this.noOfFloors = noOfFloors;
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
	
	public String[] getRoomTypeName() {
		return roomTypeName;
	}

	public void setRoomTypeName(String[] roomTypeName) {
		this.roomTypeName = roomTypeName;
	}
}
