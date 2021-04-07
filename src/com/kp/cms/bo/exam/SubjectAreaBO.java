package com.kp.cms.bo.exam;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.admin.Employee;

public class SubjectAreaBO implements Serializable{
private int id;
private String name;
private String createdBy;
private Date createdDate;
private String modifiedBy;
private Date lastModifiedDate;
private Boolean isActive;

/**
 * 
 */
public SubjectAreaBO() {
	super();
}
/**
 * @param id
 */
public SubjectAreaBO(int id) {
	super();
	this.id = id;
}
/**
 * @param id
 * @param name
 * @param createdBy
 * @param createdDate
 * @param modifiedBy
 * @param lastModifiedDate
 * @param isActive
 */
public SubjectAreaBO(int id, String name, String createdBy, Date createdDate,
		String modifiedBy, Date lastModifiedDate, Boolean isActive) {
	super();
	this.id = id;
	this.name = name;
	this.createdBy = createdBy;
	this.createdDate = createdDate;
	this.modifiedBy = modifiedBy;
	this.lastModifiedDate = lastModifiedDate;
	this.isActive = isActive;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
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
