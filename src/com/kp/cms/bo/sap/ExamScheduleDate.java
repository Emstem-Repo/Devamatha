package com.kp.cms.bo.sap;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ExamScheduleDate {
	private int id;
	private Date examDate;
	private String session;
	private int sessionOrder;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Set<ExamScheduleVenue> examScheduleVenue=new HashSet<ExamScheduleVenue>();
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getExamDate() {
		return examDate;
	}
	public void setExamDate(Date examDate) {
		this.examDate = examDate;
	}
	public String getSession() {
		return session;
	}
	public void setSession(String session) {
		this.session = session;
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
	public Set<ExamScheduleVenue> getExamScheduleVenue() {
		return examScheduleVenue;
	}
	public void setExamScheduleVenue(Set<ExamScheduleVenue> examScheduleVenue) {
		this.examScheduleVenue = examScheduleVenue;
	}
	public int getSessionOrder() {
		return sessionOrder;
	}
	public void setSessionOrder(int sessionOrder) {
		this.sessionOrder = sessionOrder;
	}
	

}
