package com.kp.cms.to.examallotment;

import java.io.Serializable;
import java.util.Date;
public class ExamInvigilationDutySettingsTo implements Serializable{
	
	private int id;
	private String endMid;
	private String noOfSessionsOnSameDay;
	private String maxNoOfStudentsPerTeacher;
	private String noOfRoomsPerReliever;
	private String workLocationId; 
	private String createdBy;
	private String createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private String locationName;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEndMid() {
		return endMid;
	}
	public void setEndMid(String endMid) {
		this.endMid = endMid;
	}
	public String getNoOfSessionsOnSameDay() {
		return noOfSessionsOnSameDay;
	}
	public void setNoOfSessionsOnSameDay(String noOfSessionsOnSameDay) {
		this.noOfSessionsOnSameDay = noOfSessionsOnSameDay;
	}
	public String getMaxNoOfStudentsPerTeacher() {
		return maxNoOfStudentsPerTeacher;
	}
	public void setMaxNoOfStudentsPerTeacher(String maxNoOfStudentsPerTeacher) {
		this.maxNoOfStudentsPerTeacher = maxNoOfStudentsPerTeacher;
	}
	public String getNoOfRoomsPerReliever() {
		return noOfRoomsPerReliever;
	}
	public void setNoOfRoomsPerReliever(String noOfRoomsPerReliever) {
		this.noOfRoomsPerReliever = noOfRoomsPerReliever;
	}
	public String getWorkLocationId() {
		return workLocationId;
	}
	public void setWorkLocationId(String workLocationId) {
		this.workLocationId = workLocationId;
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
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	
}
