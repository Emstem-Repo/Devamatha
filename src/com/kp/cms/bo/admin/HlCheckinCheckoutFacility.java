package com.kp.cms.bo.admin;
//Generated Dec 18, 2009 3:48:30 PM by Hibernate Tools 3.2.0.b9

import java.util.Date;

/**
* HlCheckinCheckoutFacility generated by hbm2java
*/
public class HlCheckinCheckoutFacility implements java.io.Serializable {

	private int id;
	private HlRoomTransaction hlRoomTransaction;
	private HlFacility hlFacility;
	private HlApplicationForm hlApplicationForm;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;

	public HlCheckinCheckoutFacility() {
	}

	public HlCheckinCheckoutFacility(int id) {
		this.id = id;
	}

	public HlCheckinCheckoutFacility(int id,
			HlRoomTransaction hlRoomTransaction, HlFacility hlFacility,
			HlApplicationForm hlApplicationForm, String createdBy,
			Date createdDate, String modifiedBy, Date lastModifiedDate,
			Boolean isActive) {
		this.id = id;
		this.hlRoomTransaction = hlRoomTransaction;
		this.hlFacility = hlFacility;
		this.hlApplicationForm = hlApplicationForm;
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

	public HlRoomTransaction getHlRoomTransaction() {
		return this.hlRoomTransaction;
	}

	public void setHlRoomTransaction(HlRoomTransaction hlRoomTransaction) {
		this.hlRoomTransaction = hlRoomTransaction;
	}

	public HlFacility getHlFacility() {
		return this.hlFacility;
	}

	public void setHlFacility(HlFacility hlFacility) {
		this.hlFacility = hlFacility;
	}

	public HlApplicationForm getHlApplicationForm() {
		return this.hlApplicationForm;
	}

	public void setHlApplicationForm(HlApplicationForm hlApplicationForm) {
		this.hlApplicationForm = hlApplicationForm;
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
