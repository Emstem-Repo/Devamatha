package com.kp.cms.bo.examallotment;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.admin.EmployeeWorkLocationBO;

public class ExamInvigilationDutySettings implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String endMid;
	private int noOfSessionsOnSameDay;
	private int maxNoOfStudentsPerTeacher;
	private int noOfRoomsPerReliever;
	private EmployeeWorkLocationBO workLocationId; 
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	
	public ExamInvigilationDutySettings(int id, String endMid,
			int noOfSessionsOnSameDay, int maxNoOfStudentsPerTeacher,
			int noOfRoomsPerReliever, EmployeeWorkLocationBO workLocationId,
			String createdBy, Date createdDate, String modifiedBy,
			Date lastModifiedDate, Boolean isActive) {
		super();
		this.id = id;
		this.endMid = endMid;
		this.noOfSessionsOnSameDay = noOfSessionsOnSameDay;
		this.maxNoOfStudentsPerTeacher = maxNoOfStudentsPerTeacher;
		this.noOfRoomsPerReliever = noOfRoomsPerReliever;
		this.workLocationId = workLocationId;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
	}
	
	public ExamInvigilationDutySettings() {
		super();
		// TODO Auto-generated constructor stub
	}

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
	public int getNoOfSessionsOnSameDay() {
		return noOfSessionsOnSameDay;
	}
	public void setNoOfSessionsOnSameDay(int noOfSessionsOnSameDay) {
		this.noOfSessionsOnSameDay = noOfSessionsOnSameDay;
	}
	public int getMaxNoOfStudentsPerTeacher() {
		return maxNoOfStudentsPerTeacher;
	}
	public void setMaxNoOfStudentsPerTeacher(int maxNoOfStudentsPerTeacher) {
		this.maxNoOfStudentsPerTeacher = maxNoOfStudentsPerTeacher;
	}
	public int getNoOfRoomsPerReliever() {
		return noOfRoomsPerReliever;
	}
	public void setNoOfRoomsPerReliever(int noOfRoomsPerReliever) {
		this.noOfRoomsPerReliever = noOfRoomsPerReliever;
	}
	public EmployeeWorkLocationBO getWorkLocationId() {
		return workLocationId;
	}
	public void setWorkLocationId(EmployeeWorkLocationBO workLocationId) {
		this.workLocationId = workLocationId;
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
}
