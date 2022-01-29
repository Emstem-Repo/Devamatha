package com.kp.cms.to.hostel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlFacility;
import com.kp.cms.bo.admin.HlRoomTransaction;

public class HlCheckinCheckoutFacilityTo implements Serializable {
	
	private int id;
	private HlRoomTransaction hlRoomTransaction;
	private HlFacility hlFacility;
	private HlApplicationForm hlApplicationForm;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private String name;
	private int hlFacilityId;
	private boolean dummySelected;
	private boolean selected;
	List<HlCheckinCheckoutFacilityTo> checkinCheckoutFacilityList;
	
	public List<HlCheckinCheckoutFacilityTo> getCheckinCheckoutFacilityList() {
		return checkinCheckoutFacilityList;
	}
	public void setCheckinCheckoutFacilityList(
			List<HlCheckinCheckoutFacilityTo> checkinCheckoutFacilityList) {
		this.checkinCheckoutFacilityList = checkinCheckoutFacilityList;
	}
	
	public String getName() {
		return name;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public boolean isDummySelected() {
		return dummySelected;
	}
	public void setDummySelected(boolean dummySelected) {
		this.dummySelected = dummySelected;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getHlFacilityId() {
		return hlFacilityId;
	}
	public void setHlFacilityId(int hlFacilityId) {
		this.hlFacilityId = hlFacilityId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public HlRoomTransaction getHlRoomTransaction() {
		return hlRoomTransaction;
	}
	public void setHlRoomTransaction(HlRoomTransaction hlRoomTransaction) {
		this.hlRoomTransaction = hlRoomTransaction;
	}
	public HlFacility getHlFacility() {
		return hlFacility;
	}
	public void setHlFacility(HlFacility hlFacility) {
		this.hlFacility = hlFacility;
	}
	public HlApplicationForm getHlApplicationForm() {
		return hlApplicationForm;
	}
	public void setHlApplicationForm(HlApplicationForm hlApplicationForm) {
		this.hlApplicationForm = hlApplicationForm;
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
