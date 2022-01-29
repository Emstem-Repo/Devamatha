package com.kp.cms.bo.auditorium;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class AuditoriumBooking implements Serializable{
   private int id;
   private BlockDetails block;
   private VenueDetails venue;
   private String bookedBy;
   private String startTime;
   private String endTime;
   private String remarks;
   //private Set<AuditoriumBookingRequirements> bookingRequirements = new HashSet<AuditoriumBookingRequirements>(0);
   private String createdBy;
   private String modifiedBy;
   private Date createdDate;
   private Date lastModifiedDate;
   private String authorizedBy;
   private Boolean rejected;
   private String rejectedBy;
   private Boolean isApproved;
   private Date date;
   private Set<BookingRequirements> bookingRequirements=new HashSet<BookingRequirements>();
   private String adminRemarks;
   private Boolean eventCanceled;
   
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public BlockDetails getBlock() {
	return block;
}
public void setBlock(BlockDetails block) {
	this.block = block;
}
public VenueDetails getVenue() {
	return venue;
}
public void setVenue(VenueDetails venue) {
	this.venue = venue;
}
public String getBookedBy() {
	return bookedBy;
}
public void setBookedBy(String bookedBy) {
	this.bookedBy = bookedBy;
}

public String getRemarks() {
	return remarks;
}
public void setRemarks(String remarks) {
	this.remarks = remarks;
}
public String getStartTime() {
	return startTime;
}
public void setStartTime(String startTime) {
	this.startTime = startTime;
}
public String getEndTime() {
	return endTime;
}
public void setEndTime(String endTime) {
	this.endTime = endTime;
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
public String getAuthorizedBy() {
	return authorizedBy;
}
public void setAuthorizedBy(String authorizedBy) {
	this.authorizedBy = authorizedBy;
}
public Boolean getIsApproved() {
	return isApproved;
}
public void setIsApproved(Boolean isApproved) {
	this.isApproved = isApproved;
}
public Date getDate() {
	return date;
}
public void setDate(Date date) {
	this.date = date;
}
public Set<BookingRequirements> getBookingRequirements() {
	return bookingRequirements;
}
public void setBookingRequirements(Set<BookingRequirements> bookingRequirements) {
	this.bookingRequirements = bookingRequirements;
}
public String getAdminRemarks() {
	return adminRemarks;
}
public void setAdminRemarks(String adminRemarks) {
	this.adminRemarks = adminRemarks;
}
public Boolean getRejected() {
	return rejected;
}
public void setRejected(Boolean rejected) {
	this.rejected = rejected;
}
public String getRejectedBy() {
	return rejectedBy;
}
public void setRejectedBy(String rejectedBy) {
	this.rejectedBy = rejectedBy;
}
public Boolean getEventCanceled() {
	return eventCanceled;
}
public void setEventCanceled(Boolean eventCanceled) {
	this.eventCanceled = eventCanceled;
}
   
}
