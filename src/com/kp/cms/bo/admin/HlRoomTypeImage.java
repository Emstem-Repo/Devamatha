package com.kp.cms.bo.admin;

// Generated Dec 18, 2009 3:48:30 PM by Hibernate Tools 3.2.0.b9

import java.util.Date;

/**
 * HlRoomTypeImage generated by hbm2java
 */
public class HlRoomTypeImage implements java.io.Serializable {

	private int id;
	private HlRoomType hlRoomType;
	private byte[] image;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;

	public HlRoomTypeImage() {
	}

	public HlRoomTypeImage(int id) {
		this.id = id;
	}

	public HlRoomTypeImage(int id, HlRoomType hlRoomType, byte[] image,
			String createdBy, Date createdDate, String modifiedBy,
			Date lastModifiedDate, Boolean isActive) {
		this.id = id;
		this.hlRoomType = hlRoomType;
		this.image = image;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public HlRoomType getHlRoomType() {
		return this.hlRoomType;
	}

	public void setHlRoomType(HlRoomType hlRoomType) {
		this.hlRoomType = hlRoomType;
	}

	public byte[] getImage() {
		return this.image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getLastModifiedDate() {
		return this.lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

}