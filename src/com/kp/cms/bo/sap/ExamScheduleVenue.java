package com.kp.cms.bo.sap;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ExamScheduleVenue {
	private int id;
	private SapVenue venue;
	private String workLocation;
	private ExamScheduleDate examScheduleDate;
	private int priority;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Set<ExamScheduleUsers> examScheduleUsers=new HashSet<ExamScheduleUsers>();
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
	public Set<ExamScheduleUsers> getExamScheduleUsers() {
		return examScheduleUsers;
	}
	public void setExamScheduleUsers(Set<ExamScheduleUsers> examScheduleUsers) {
		this.examScheduleUsers = examScheduleUsers;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public SapVenue getVenue() {
		return venue;
	}
	public void setVenue(SapVenue venue) {
		this.venue = venue;
	}
	public ExamScheduleDate getExamScheduleDate() {
		return examScheduleDate;
	}
	public void setExamScheduleDate(ExamScheduleDate examScheduleDate) {
		this.examScheduleDate = examScheduleDate;
	}
	public String getWorkLocation() {
		return workLocation;
	}
	public void setWorkLocation(String workLocation) {
		this.workLocation = workLocation;
	}
	
}
