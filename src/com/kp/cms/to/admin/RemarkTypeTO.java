package com.kp.cms.to.admin;

public class RemarkTypeTO {
	private int id;
	private String remarkType;
	private String color;
	private String createdBy;
	private String createdDate;
	private String modifiedBy;
	private String lastModifiedDate;
	private String maxOccurrences;
	private String isActive;
	
	public int getId() {
		return id;
	}
	public String getRemarkType() {
		return remarkType;
	}
	public String getColor() {
		return color;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setRemarkType(String remarkType) {
		this.remarkType = remarkType;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public String getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public String getMaxOccurrences() {
		return maxOccurrences;
	}
	public void setMaxOccurrences(String maxOccurrences) {
		this.maxOccurrences = maxOccurrences;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

}
