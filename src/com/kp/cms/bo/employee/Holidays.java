package com.kp.cms.bo.employee;

import java.io.Serializable;
import java.util.Date;

public class Holidays implements Serializable{
private int id;
private Date startDate;
private Date endDate;
private String description;
private String createdBy;
private String modifiedBy;
private Date createdDate;
private Date lastModifiedDate;
private Boolean isActive;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public Date getStartDate() {
	return startDate;
}
public void setStartDate(Date startDate) {
	this.startDate = startDate;
}
public Date getEndDate() {
	return endDate;
}
public void setEndDate(Date endDate) {
	this.endDate = endDate;
}
public String getCreatedBy() {
	return createdBy;
}
public void setCreatedBy(String createdBy) {
	this.createdBy = createdBy;
}
public String getModifiedBy() {
	return modifiedBy;
}
public void setModifiedBy(String modifiedBy) {
	this.modifiedBy = modifiedBy;
}
public Date getCreatedDate() {
	return createdDate;
}
public void setCreatedDate(Date createdDate) {
	this.createdDate = createdDate;
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
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
}
