package com.kp.cms.bo.admin;

// Generated Jan 16, 2013 5:48:30 PM by Hibernate Tools 3.2.0.b9

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class HlBeds implements java.io.Serializable{
	
	private int id;
	private String bedNo;
	private HlRoom hlRoom;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	
	public HlBeds(){
	}
	
	
	public HlBeds(int id, String bedNo, HlRoom hlRoom,
			String createdBy, Date createdDate, String modifiedBy, Date lastModifiedDate, Boolean isActive) {
		super();
		this.id = id;
		this.bedNo = bedNo;
		this.hlRoom = hlRoom;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBedNo() {
		return bedNo;
	}

	public void setBedNo(String bedNo) {
		this.bedNo = bedNo;
	}

	public HlRoom getHlRoom() {
		return hlRoom;
	}

	public void setHlRoom(HlRoom hlRoom) {
		this.hlRoom = hlRoom;
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
	
}