package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.util.Date;

public class CertificateRequestPurposeDetails implements Serializable{

	private static final long serialVersionUID = 1L;
	private int id;
	private CertificateOnlineStudentRequest certificateRequestId;
	private CertificateRequestPurpose purposeId;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	
	
	public CertificateRequestPurposeDetails(
			CertificateOnlineStudentRequest certificateRequestId,
			CertificateRequestPurpose purposeId, String createdBy,
			Date createdDate, String modifiedBy, Date lastModifiedDate) {
		super();
		this.certificateRequestId = certificateRequestId;
		this.purposeId = purposeId;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
	}


	public CertificateRequestPurposeDetails() {
		super();
		
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public CertificateOnlineStudentRequest getCertificateRequestId() {
		return certificateRequestId;
	}


	public void setCertificateRequestId(
			CertificateOnlineStudentRequest certificateRequestId) {
		this.certificateRequestId = certificateRequestId;
	}


	public CertificateRequestPurpose getPurposeId() {
		return purposeId;
	}


	public void setPurposeId(CertificateRequestPurpose purposeId) {
		this.purposeId = purposeId;
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
}
