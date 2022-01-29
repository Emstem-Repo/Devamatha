package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.util.Date;

public class PayScaleBO implements Serializable{
private int id;
private String payScale;
private String scale;
private String createdBy;
private String modifiedBy;
private Date createdDate;
private Date lastModifiedDate;
private Boolean isActive;
private Boolean teachingStaff;
private Employee employee;
public PayScaleBO(){
	
}
public PayScaleBO(int id, String payScale, String scale, String createdBy,
		String modifiedBy, Date createdDate, Date lastModifiedDate,
		Boolean isActive, Boolean teachingStaff) {
	super();
	this.id = id;
	this.payScale = payScale;
	this.scale = scale;
	this.createdBy = createdBy;
	this.modifiedBy = modifiedBy;
	this.createdDate = createdDate;
	this.lastModifiedDate = lastModifiedDate;
	this.isActive = isActive;
	this.teachingStaff = teachingStaff;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getPayScale() {
	return payScale;
}
public void setPayScale(String payScale) {
	this.payScale = payScale;
}
public String getScale() {
	return scale;
}
public void setScale(String scale) {
	this.scale = scale;
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
public Employee getEmployee() {
	return employee;
}
public void setEmployee(Employee employee) {
	this.employee = employee;
}
public Boolean getTeachingStaff() {
	return teachingStaff;
}
public void setTeachingStaff(Boolean teachingStaff) {
	this.teachingStaff = teachingStaff;
}

}
