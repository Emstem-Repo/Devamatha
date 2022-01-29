package com.kp.cms.to.employee;

import java.util.Date;

import com.kp.cms.bo.admin.EventLocation;

public class EventScheduleForAttendanceTo {
	private int id;
	private String eventDescription;
	private String eventLocation;
	private Date eventDate;
	private String eventTimeFrom;
	private String eventTimeTo;
	private Boolean isActive;
	private Boolean isStudent;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEventDescription() {
		return eventDescription;
	}
	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}
	public Date getEventDate() {
		return eventDate;
	}
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}
	public String getEventTimeFrom() {
		return eventTimeFrom;
	}
	public void setEventTimeFrom(String eventTimeFrom) {
		this.eventTimeFrom = eventTimeFrom;
	}
	public String getEventTimeTo() {
		return eventTimeTo;
	}
	public void setEventTimeTo(String eventTimeTo) {
		this.eventTimeTo = eventTimeTo;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public Boolean getIsStudent() {
		return isStudent;
	}
	public void setIsStudent(Boolean isStudent) {
		this.isStudent = isStudent;
	}
	public String getEventLocation() {
		return eventLocation;
	}
	public void setEventLocation(String eventLocation) {
		this.eventLocation = eventLocation;
	}

}
