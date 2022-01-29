package com.kp.cms.bo.employee;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.kp.cms.bo.admin.EventLocation;


public class EventScheduleForAttendanceBo {
	private int id;
	private String eventDescription;
	private EventLocation eventLocation;
	private Date eventDate;
	private String eventTimeFrom;
	private String eventTimeTo;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Boolean isStudent;
	private List<Integer> classList;
	private List<Integer> deptList;
	private Set<EventScheduleStaffAttendanceBo> eventScheduleStaffAttendanceBo=new HashSet<EventScheduleStaffAttendanceBo>();
	private Set<EventScheduleStudentAttendanceBo> eventScheduleStudentAttendanceBo=new HashSet<EventScheduleStudentAttendanceBo>();
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
	public Set<EventScheduleStaffAttendanceBo> getEventScheduleStaffAttendanceBo() {
		return eventScheduleStaffAttendanceBo;
	}
	public void setEventScheduleStaffAttendanceBo(
			Set<EventScheduleStaffAttendanceBo> eventScheduleStaffAttendanceBo) {
		this.eventScheduleStaffAttendanceBo = eventScheduleStaffAttendanceBo;
	}
	public Set<EventScheduleStudentAttendanceBo> getEventScheduleStudentAttendanceBo() {
		return eventScheduleStudentAttendanceBo;
	}
	public void setEventScheduleStudentAttendanceBo(
			Set<EventScheduleStudentAttendanceBo> eventScheduleStudentAttendanceBo) {
		this.eventScheduleStudentAttendanceBo = eventScheduleStudentAttendanceBo;
	}
	public EventLocation getEventLocation() {
		return eventLocation;
	}
	public void setEventLocation(EventLocation eventLocation) {
		this.eventLocation = eventLocation;
	}
	public Date getEventDate() {
		return eventDate;
	}
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}
	public Boolean getIsStudent() {
		return isStudent;
	}
	public void setIsStudent(Boolean isStudent) {
		this.isStudent = isStudent;
	}
	public List<Integer> getClassList() {
		return classList;
	}
	public void setClassList(List<Integer> classList) {
		this.classList = classList;
	}
	public List<Integer> getDeptList() {
		return deptList;
	}
	public void setDeptList(List<Integer> deptList) {
		this.deptList = deptList;
	}
	
	

}
