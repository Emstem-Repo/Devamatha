package com.kp.cms.bo.admission;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.CourseApplicationNumber;

public class ApplnAcknowledgement implements Serializable{
   private int id;
   private String applnNo;
   private String receivedThrough;
   private Date receivedDate;
   private String name;
   private String remarks;
   private String slipNo;
   private Course course;
   private Date dateOfBirth;
   private String trackingNo;
   private String mobileNo;
   private String createdBy;
   private String modifiedBy;
   private Date createdDate;
   private Date lastModifiedDate; 
   
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getApplnNo() {
	return applnNo;
}
public void setApplnNo(String applnNo) {
	this.applnNo = applnNo;
}
public String getReceivedThrough() {
	return receivedThrough;
}
public void setReceivedThrough(String receivedThrough) {
	this.receivedThrough = receivedThrough;
}
public Date getReceivedDate() {
	return receivedDate;
}
public void setReceivedDate(Date receivedDate) {
	this.receivedDate = receivedDate;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getRemarks() {
	return remarks;
}
public void setRemarks(String remarks) {
	this.remarks = remarks;
}
public String getSlipNo() {
	return slipNo;
}
public void setSlipNo(String slipNo) {
	this.slipNo = slipNo;
}
public Course getCourse() {
	return course;
}
public void setCourse(Course course) {
	this.course = course;
}
public Date getDateOfBirth() {
	return dateOfBirth;
}
public void setDateOfBirth(Date dateOfBirth) {
	this.dateOfBirth = dateOfBirth;
}
public String getTrackingNo() {
	return trackingNo;
}
public void setTrackingNo(String trackingNo) {
	this.trackingNo = trackingNo;
}
public String getMobileNo() {
	return mobileNo;
}
public void setMobileNo(String mobileNo) {
	this.mobileNo = mobileNo;
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
