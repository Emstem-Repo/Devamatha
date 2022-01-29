package com.kp.cms.bo.phd;

import java.io.Serializable;
import java.util.Date;

public class PhdConference implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private PhdDocumentSubmissionBO documetSubmission;
	private String participated;
	private String organizedBy;
	private String nameProgram;
	private Date dateFrom;
	private Date dateTo;
	private String level;
	private Boolean isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	
	
	public PhdConference(){
		
	}
	
	/**
	 * @param id
	 * @param researchPublication
	 * @param documetSubmission
	 * @param description
	 * @param researchDate
	 * @param isActive
	 * @param createdBy
	 * @param createdDate
	 * @param modifiedBy
	 * @param modifiedDate
	 */
	public PhdConference(int id,PhdDocumentSubmissionBO documetSubmission,
		String participated,String organizedBy,String nameProgram,Date dateFrom,Date dateTo,String level
	    ,Boolean isActive,String createdBy,Date createdDate,String modifiedBy,Date modifiedDate) {
		super();
		this.id = id;
		this.documetSubmission = documetSubmission;
		this.participated=participated;
		this.organizedBy=organizedBy;
		this.nameProgram=nameProgram;
		this.dateFrom=dateFrom;
		this.dateTo=dateTo;
		this.level=level;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PhdDocumentSubmissionBO getDocumetSubmission() {
		return documetSubmission;
	}

	public void setDocumetSubmission(PhdDocumentSubmissionBO documetSubmission) {
		this.documetSubmission = documetSubmission;
	}

	public String getParticipated() {
		return participated;
	}

	public void setParticipated(String participated) {
		this.participated = participated;
	}

	public String getOrganizedBy() {
		return organizedBy;
	}

	public void setOrganizedBy(String organizedBy) {
		this.organizedBy = organizedBy;
	}

	public String getNameProgram() {
		return nameProgram;
	}

	public void setNameProgram(String nameProgram) {
		this.nameProgram = nameProgram;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	
	
}
