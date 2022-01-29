package com.kp.cms.bo.employee;

import java.util.Date;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Department;

public class EventScheduleStudentAttendanceBo {
	private int id;
	private Classes classes;
	private EventScheduleForAttendanceBo eventSheduleAttendance;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private String fromPeriodId;
	private String toPeriodId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Classes getClasses() {
		return classes;
	}
	public void setClasses(Classes classes) {
		this.classes = classes;
	}
	public EventScheduleForAttendanceBo getEventSheduleAttendance() {
		return eventSheduleAttendance;
	}
	public void setEventSheduleAttendance(
			EventScheduleForAttendanceBo eventSheduleAttendance) {
		this.eventSheduleAttendance = eventSheduleAttendance;
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
	public String getFromPeriodId() {
		return fromPeriodId;
	}
	public void setFromPeriodId(String fromPeriodId) {
		this.fromPeriodId = fromPeriodId;
	}
	public String getToPeriodId() {
		return toPeriodId;
	}
	public void setToPeriodId(String toPeriodId) {
		this.toPeriodId = toPeriodId;
	}
}
