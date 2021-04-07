package com.kp.cms.bo.admission;

import java.util.Date;

import com.kp.cms.bo.admin.AdmAppln;

public class AdmInterviewSelectionSchedule  implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private AdmAppln applnId;
	private InterviewSelectionSchedule intSelectionId;
	private InterviewVenueSelection intVenueId;
	private InterviewTimeSelection intTimeId;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	
	public AdmInterviewSelectionSchedule(int id, AdmAppln applnId,
			InterviewSelectionSchedule intSelectionId,
			InterviewVenueSelection intVenueId,
			InterviewTimeSelection intTimeId, String createdBy,
			Date createdDate, String modifiedBy, Date lastModifiedDate,
			Boolean isActive) {
		super();
		this.id = id;
		this.applnId = applnId;
		this.intSelectionId = intSelectionId;
		this.intVenueId = intVenueId;
		this.intTimeId = intTimeId;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
	}
	
	public AdmInterviewSelectionSchedule() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public AdmAppln getApplnId() {
		return applnId;
	}
	public void setApplnId(AdmAppln applnId) {
		this.applnId = applnId;
	}
	public InterviewSelectionSchedule getIntSelectionId() {
		return intSelectionId;
	}
	public void setIntSelectionId(InterviewSelectionSchedule intSelectionId) {
		this.intSelectionId = intSelectionId;
	}
	public InterviewVenueSelection getIntVenueId() {
		return intVenueId;
	}
	public void setIntVenueId(InterviewVenueSelection intVenueId) {
		this.intVenueId = intVenueId;
	}
	public InterviewTimeSelection getIntTimeId() {
		return intTimeId;
	}
	public void setIntTimeId(InterviewTimeSelection intTimeId) {
		this.intTimeId = intTimeId;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	

}
