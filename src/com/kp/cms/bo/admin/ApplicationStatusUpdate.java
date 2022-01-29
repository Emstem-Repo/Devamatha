package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.util.Date;

public class ApplicationStatusUpdate implements Serializable{
private int id;
private AdmAppln admApplnNO;
private ApplicationStatus applicationStatus;
private String createdBy;
private String modifiedBy;
private Date createdDate;
private Date lastModifiedDate;
public ApplicationStatusUpdate(){
	
}
public ApplicationStatusUpdate(int id,AdmAppln admApplnNO,
		ApplicationStatus applicationStatus, 
		String createdBy, String modifiedBy, Date createdDate,
		Date lastModifiedDate) {
	super();
	this.id = id;
	this.admApplnNO = admApplnNO;
	this.applicationStatus = applicationStatus;
	this.createdBy = createdBy;
	this.modifiedBy = modifiedBy;
	this.createdDate = createdDate;
	this.lastModifiedDate = lastModifiedDate;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public AdmAppln getAdmApplnNO() {
	return admApplnNO;
}
public void setAdmApplnNO(AdmAppln admApplnNO) {
	this.admApplnNO = admApplnNO;
}
public ApplicationStatus getApplicationStatus() {
	return applicationStatus;
}
public void setApplicationStatus(ApplicationStatus applicationStatus) {
	this.applicationStatus = applicationStatus;
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

}
