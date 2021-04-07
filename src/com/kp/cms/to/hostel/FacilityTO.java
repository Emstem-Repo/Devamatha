package com.kp.cms.to.hostel;

import java.io.Serializable;

public class FacilityTO implements Serializable{
	private int id;
	private String name;
	private String quantity;
	private String description;
	private boolean dummySelected;
	private boolean selected;
	private int facilityCount;
	private int hlStudAllotFacId;
	private int hlAdmissionId;
	private int hlRoomTypeId;
	private String checked;
	
	
	
	public int getHlRoomTypeId() {
		return hlRoomTypeId;
	}
	public void setHlRoomTypeId(int hlRoomTypeId) {
		this.hlRoomTypeId = hlRoomTypeId;
	}
	public int getId() {
		return id;
	}
	public int getHlStudAllotFacId() {
		return hlStudAllotFacId;
	}
	public void setHlStudAllotFacId(int hlStudAllotFacId) {
		this.hlStudAllotFacId = hlStudAllotFacId;
	}
	public int getHlAdmissionId() {
		return hlAdmissionId;
	}
	public void setHlAdmissionId(int hlAdmissionId) {
		this.hlAdmissionId = hlAdmissionId;
	}
	public String getName() {
		return name;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isDummySelected() {
		return dummySelected;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setDummySelected(boolean dummySelected) {
		this.dummySelected = dummySelected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public int getFacilityCount() {
		return facilityCount;
	}
	public void setFacilityCount(int facilityCount) {
		this.facilityCount = facilityCount;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	
}
