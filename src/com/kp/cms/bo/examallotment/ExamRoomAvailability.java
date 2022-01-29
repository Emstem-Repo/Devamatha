package com.kp.cms.bo.examallotment;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.studentfeedback.RoomMaster;

public class ExamRoomAvailability implements Serializable{
	private int id;
	private RoomMaster roomMaster;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedBy;
	private Boolean isActive;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public RoomMaster getRoomMaster() {
		return roomMaster;
	}
	public void setRoomMaster(RoomMaster roomMaster) {
		this.roomMaster = roomMaster;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(Date lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
}
