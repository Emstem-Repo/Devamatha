package com.kp.cms.bo.admin;

// Generated Dec 18, 2009 3:48:30 PM by Hibernate Tools 3.2.0.b9

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * HlFacility generated by hbm2java
 */
public class HlFacility implements java.io.Serializable {

	private int id;
	private String name;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Set<HlCheckinCheckoutFacility> hlCheckinCheckoutFacilities = new HashSet<HlCheckinCheckoutFacility>(
			0);
	private Set<HlRoomTypeFacility> hlRoomTypeFacilities = new HashSet<HlRoomTypeFacility>(
			0);

	public HlFacility() {
	}

	public HlFacility(int id) {
		this.id = id;
	}

	public HlFacility(int id, String name, String createdBy, Date createdDate,
			String modifiedBy, Date lastModifiedDate, Boolean isActive,
			Set<HlCheckinCheckoutFacility> hlCheckinCheckoutFacilities,
			Set<HlRoomTypeFacility> hlRoomTypeFacilities) {
		this.id = id;
		this.name = name;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.hlCheckinCheckoutFacilities = hlCheckinCheckoutFacilities;
		this.hlRoomTypeFacilities = hlRoomTypeFacilities;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Set<HlCheckinCheckoutFacility> getHlCheckinCheckoutFacilities() {
		return this.hlCheckinCheckoutFacilities;
	}

	public void setHlCheckinCheckoutFacilities(
			Set<HlCheckinCheckoutFacility> hlCheckinCheckoutFacilities) {
		this.hlCheckinCheckoutFacilities = hlCheckinCheckoutFacilities;
	}

	public Set<HlRoomTypeFacility> getHlRoomTypeFacilities() {
		return this.hlRoomTypeFacilities;
	}

	public void setHlRoomTypeFacilities(
			Set<HlRoomTypeFacility> hlRoomTypeFacilities) {
		this.hlRoomTypeFacilities = hlRoomTypeFacilities;
	}

}
