package com.kp.cms.bo.admin;

// Generated Jul 13, 2009 2:37:04 PM by Hibernate Tools 3.2.0.b9

import java.util.Date;

/**
 * AdmissionCardTemplate generated by hbm2java
 */
public class AdmissionCardTemplate implements java.io.Serializable {

	private int id;
	private String admissionCardDescription;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;

	public AdmissionCardTemplate() {
	}

	public AdmissionCardTemplate(int id) {
		this.id = id;
	}

	public AdmissionCardTemplate(int id, String admissionCardDescription,
			String createdBy, Date createdDate, String modifiedBy,
			Date lastModifiedDate) {
		this.id = id;
		this.admissionCardDescription = admissionCardDescription;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAdmissionCardDescription() {
		return this.admissionCardDescription;
	}

	public void setAdmissionCardDescription(String admissionCardDescription) {
		this.admissionCardDescription = admissionCardDescription;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getLastModifiedDate() {
		return this.lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

}
