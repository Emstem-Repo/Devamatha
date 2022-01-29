package com.kp.cms.to.attendance;

import java.io.Serializable;



public class ActivityTO implements Serializable,Comparable<ActivityTO> {
	private int id;
	private String name;
	private String createdBy;
	private String createdDate;
	private String modifiedBy;
	private String lastModifiedDate;
	private boolean isActive;
	private AttendanceTypeTO attendanceTypeTO;
	private String activityIsActive;
	private String checked;
	
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
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public AttendanceTypeTO getAttendanceTypeTO() {
		return attendanceTypeTO;
	}
	public void setAttendanceTypeTO(AttendanceTypeTO attendanceTypeTO) {
		this.attendanceTypeTO = attendanceTypeTO;
	}
	public String getActivityIsActive() {
		return activityIsActive;
	}
	public void setActivityIsActive(String activityIsActive) {
		this.activityIsActive = activityIsActive;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	@Override
	public int compareTo(ActivityTO arg0) {
		if(arg0!=null && this!=null){
			if(this.getId() > arg0.getId())
				return 1;
			else if(this.getId() < arg0.getId())
				return -1;
			else
				return 0;
		}
		return 0;
	}
	
}
