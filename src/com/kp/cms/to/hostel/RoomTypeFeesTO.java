package com.kp.cms.to.hostel;

import java.io.Serializable;

public class RoomTypeFeesTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String roomTypeName;
	private String roomTypeId;
	private String groupName;
	private String hostelTypeName;
	private String hostelTypeId;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getRoomTypeName() {
		return roomTypeName;
	}
	public void setRoomTypeName(String roomTypeName) {
		this.roomTypeName = roomTypeName;
	}
	public String getRoomTypeId() {
		return roomTypeId;
	}
	public void setRoomTypeId(String roomTypeId) {
		this.roomTypeId = roomTypeId;
	}
	public String getHostelTypeName() {
		return hostelTypeName;
	}
	public void setHostelTypeName(String hostelTypeName) {
		this.hostelTypeName = hostelTypeName;
	}
	public String getHostelTypeId() {
		return hostelTypeId;
	}
	public void setHostelTypeId(String hostelTypeId) {
		this.hostelTypeId = hostelTypeId;
	}
		
	
}
