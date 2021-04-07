package com.kp.cms.bo.admin;

// Generated Dec 18, 2009 3:48:30 PM by Hibernate Tools 3.2.0.b9

import java.util.Date;

/**
 * HlRoomTypeFacility generated by hbm2java
 */
public class HlRoomTypeFacility implements java.io.Serializable {

	private int id;
	private HlFacility hlFacility;
	private HlRoomType hlRoomType;
	private Integer quantity;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;

	public HlRoomTypeFacility() {
	}

	public HlRoomTypeFacility(int id) {
		this.id = id;
	}

	public HlRoomTypeFacility(int id, HlFacility hlFacility,
			HlRoomType hlRoomType, Integer quantity, String createdBy,
			Date createdDate, String modifiedBy, Date lastModifiedDate,
			Boolean isActive) {
		this.id = id;
		this.hlFacility = hlFacility;
		this.hlRoomType = hlRoomType;
		this.quantity = quantity;
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

	public HlFacility getHlFacility() {
		return this.hlFacility;
	}

	public void setHlFacility(HlFacility hlFacility) {
		this.hlFacility = hlFacility;
	}

	public HlRoomType getHlRoomType() {
		return this.hlRoomType;
	}

	public void setHlRoomType(HlRoomType hlRoomType) {
		this.hlRoomType = hlRoomType;
	}

	public Integer getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
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