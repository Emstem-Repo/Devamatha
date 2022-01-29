package com.kp.cms.bo.hostel;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.bo.admin.HlRoomType;

public class AvailableSeatsBo implements Serializable{
	private int id;
	private String academicYear;
	private HlHostel hostelId;
	private HlRoomType roomTypeId;
	private String numOfAvailableSeats;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public HlHostel getHostelId() {
		return hostelId;
	}
	public void setHostelId(HlHostel hostelId) {
		this.hostelId = hostelId;
	}
	public HlRoomType getRoomTypeId() {
		return roomTypeId;
	}
	public void setRoomTypeId(HlRoomType roomTypeId) {
		this.roomTypeId = roomTypeId;
	}
	public String getNumOfAvailableSeats() {
		return numOfAvailableSeats;
	}
	public void setNumOfAvailableSeats(String numOfAvailableSeats) {
		this.numOfAvailableSeats = numOfAvailableSeats;
	}
	

}
