package com.kp.cms.to.hostel;

import java.util.Date;
import com.kp.cms.bo.admin.HlFacility;
import com.kp.cms.bo.admin.HlRoomType;


public class HlRoomTypeFacilityTo {
	
	private String name;
	private HlFacility hlFacility;
	private HlRoomType hlRoomType;
	private Integer quantity;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private int id;
	private int hlFacilityId;
	private boolean checked;
	private boolean tempChecked;
	
	public int getHlFacilityId() {
		return hlFacilityId;
	}
	public void setHlFacilityId(int hlFacilityId) {
		this.hlFacilityId = hlFacilityId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public HlFacility getHlFacility() {
		return hlFacility;
	}
	public void setHlFacility(HlFacility hlFacility) {
		this.hlFacility = hlFacility;
	}
	public HlRoomType getHlRoomType() {
		return hlRoomType;
	}
	public void setHlRoomType(HlRoomType hlRoomType) {
		this.hlRoomType = hlRoomType;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
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
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public boolean isTempChecked() {
		return tempChecked;
	}
	public void setTempChecked(boolean tempChecked) {
		this.tempChecked = tempChecked;
	}
}
