package com.kp.cms.to.admission;

import java.util.Date;

import com.kp.cms.to.admin.RoomTO;

public class InterviewersTO {

	private int id;
	private InterviewScheduleTO interviewSchedule;
	private RoomTO room;
	private String description;
	private Date createdDate;
	private Date lastModifiedDate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public InterviewScheduleTO getInterviewSchedule() {
		return interviewSchedule;
	}
	public void setInterviewSchedule(InterviewScheduleTO interviewSchedule) {
		this.interviewSchedule = interviewSchedule;
	}
	public RoomTO getRoom() {
		return room;
	}
	public void setRoom(RoomTO room) {
		this.room = room;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
