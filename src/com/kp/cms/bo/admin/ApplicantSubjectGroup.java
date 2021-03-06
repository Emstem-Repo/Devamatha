package com.kp.cms.bo.admin;

import java.util.Date;

// Generated Feb 26, 2009 9:49:32 AM by Hibernate Tools 3.2.0.b9

/**
 * ApplicantSubjectGroup generated by hbm2java
 */
public class ApplicantSubjectGroup implements java.io.Serializable {

	private int id;
	private SubjectGroup subjectGroup;
	private AdmAppln admAppln;
	private String createdBy;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;

	public ApplicantSubjectGroup() {
	}

	public ApplicantSubjectGroup(int id) {
		this.id = id;
	}

	public ApplicantSubjectGroup(int id, SubjectGroup subjectGroup,
			AdmAppln admAppln, String createdBy, String modifiedBy, Date createdDate, Date lastModifiedDate) {
		this.id = id;
		this.subjectGroup = subjectGroup;
		this.admAppln = admAppln;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public SubjectGroup getSubjectGroup() {
		return this.subjectGroup;
	}

	public void setSubjectGroup(SubjectGroup subjectGroup) {
		this.subjectGroup = subjectGroup;
	}

	public AdmAppln getAdmAppln() {
		return this.admAppln;
	}

	public void setAdmAppln(AdmAppln admAppln) {
		this.admAppln = admAppln;
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

}
