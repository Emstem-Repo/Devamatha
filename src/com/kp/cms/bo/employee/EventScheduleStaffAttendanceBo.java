package com.kp.cms.bo.employee;

import java.util.Date;

import com.kp.cms.bo.admin.Department;

public class EventScheduleStaffAttendanceBo {
	private int id;
	private Department department;
	private EventScheduleForAttendanceBo eventSheduleAttendance;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public EventScheduleForAttendanceBo getEventSheduleAttendance() {
		return eventSheduleAttendance;
	}
	public void setEventSheduleAttendance(
			EventScheduleForAttendanceBo eventSheduleAttendance) {
		this.eventSheduleAttendance = eventSheduleAttendance;
	}

}
