package com.kp.cms.bo.admission;

import java.util.Date;
import com.kp.cms.bo.admin.AdmAppln;

public class AdmLoanLetterDetails implements java.io.Serializable {
	
	private int id;
	private AdmAppln admApplnId;
	private Boolean isLoanLetterIssued;
	private Date loanLetterIssuedOn;
	private String createdBy;;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private Boolean isActive;
	
	public AdmLoanLetterDetails(int id) {
		super();
		this.id = id;
	}
	
	public AdmLoanLetterDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public AdmLoanLetterDetails(int id, AdmAppln admApplnId, Boolean isLoanLetterIssued, Date loanLetterIssuedOn, 
			String createdBy, String modifiedBy, Date createdDate, Date lastModifiedDate, Boolean isActive) {
		super();
		this.id = id;
		this.admApplnId = admApplnId;
		this.isLoanLetterIssued = isLoanLetterIssued;
		this.loanLetterIssuedOn =loanLetterIssuedOn; 
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public AdmAppln getAdmApplnId() {
		return admApplnId;
	}

	public void setAdmApplnId(AdmAppln admApplnId) {
		this.admApplnId = admApplnId;
	}

	public Boolean getIsLoanLetterIssued() {
		return isLoanLetterIssued;
	}

	public void setIsLoanLetterIssued(Boolean isLoanLetterIssued) {
		this.isLoanLetterIssued = isLoanLetterIssued;
	}

	public Date getLoanLetterIssuedOn() {
		return loanLetterIssuedOn;
	}

	public void setLoanLetterIssuedOn(Date loanLetterIssuedOn) {
		this.loanLetterIssuedOn = loanLetterIssuedOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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