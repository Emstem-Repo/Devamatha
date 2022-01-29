package com.kp.cms.to.auditorium;


public class AuditoriumBookingTo {
	private String blockName;
	   private String venueName;
	   private String bookedBy;
	   private String startTime;
	   private String endTime;
	   private String remarks;
	   //private Set<AuditoriumBookingRequirements> bookingRequirements = new HashSet<AuditoriumBookingRequirements>(0);
	   private String authorizedBy;
	   private String eventId;
	   private String adminRemarks;
	   private String eventCanceled;
	   private String bookingRequirements;
	   private String department;
	   private String date;
 
	
	   
	public String getBookedBy() {
		return bookedBy;
	}
	public void setBookedBy(String bookedBy) {
		this.bookedBy = bookedBy;
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
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getAuthorizedBy() {
		return authorizedBy;
	}
	public void setAuthorizedBy(String authorizedBy) {
		this.authorizedBy = authorizedBy;
	}
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public String getAdminRemarks() {
		return adminRemarks;
	}
	public void setAdminRemarks(String adminRemarks) {
		this.adminRemarks = adminRemarks;
	}
	public String getEventCanceled() {
		return eventCanceled;
	}
	public void setEventCanceled(String eventCanceled) {
		this.eventCanceled = eventCanceled;
	}
 	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getBlockName() {
		return blockName;
	}
	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}
	public String getVenueName() {
		return venueName;
	}
	public void setVenueName(String venueName) {
		this.venueName = venueName;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getBookingRequirements() {
		return bookingRequirements;
	}
	public void setBookingRequirements(String bookingRequirements) {
		this.bookingRequirements = bookingRequirements;
	}
	   
}
