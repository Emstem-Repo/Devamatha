package com.kp.cms.bo.admin;

// Generated Jan 22, 2009 3:35:47 PM by Hibernate Tools 3.2.0.b9

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

/**
 * ApplnDoc generated by hbm2java
 */
public class ApplnDoc implements java.io.Serializable, Comparable<ApplnDoc> {

	private int id;
	private DocType docType;
	private String createdBy;;
	private String modifiedBy;
	private AdmAppln admAppln;
	private String name;
	private byte[] document;
	private Date createdDate;
	private Date lastModifiedDate;
	private Boolean isVerified;
	private Boolean hardcopySubmitted;
	private Boolean notApplicable;
	private Boolean isPhoto;
	private String contentType;
	private Date submissionDate;
	private Set<ApplnDocDetails> applnDocDetails;
	private String semNo;
	private String semType;
	private boolean isMngQuotaForm;
	public ApplnDoc() {
	}

	public ApplnDoc(int id) {
		this.id = id;
	}
	
	public int compareTo(ApplnDoc otherData) {
		if(new ArrayList<Student>(this.admAppln.getStudents()).get(0).getRegisterNo() != null && 
		   !new ArrayList<Student>(this.admAppln.getStudents()).get(0).getRegisterNo().isEmpty() &&
		   new ArrayList<Student>(otherData.admAppln.getStudents()).get(0).getRegisterNo() != null && 
		   !new ArrayList<Student>(otherData.admAppln.getStudents()).get(0).getRegisterNo().isEmpty()) {
			
			return new ArrayList<Student>(this.admAppln.getStudents()).get(0).getRegisterNo()
				   .compareTo
				   (new ArrayList<Student>(otherData.admAppln.getStudents()).get(0).getRegisterNo());
		} else {
			return new ArrayList<Student>(this.admAppln.getStudents()).get(0).getId() -
			       new ArrayList<Student>(otherData.admAppln.getStudents()).get(0).getId();
		}
	}

	public ApplnDoc(int id, DocType docType, String createdBy,
			String modifiedBy, AdmAppln admAppln, String name,
			byte[] document, Date createdDate, Date lastModifiedDate,
			Boolean isVerified,Boolean isPhoto,Boolean hardcopySubmitted,
			Boolean notApplicable,String contentType,Date submissionDate,
			Set<ApplnDocDetails> applnDocDetails,String semNo,String semType, boolean isMngQuotaForm) {
		this.id = id;
		this.docType = docType;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.admAppln = admAppln;
		this.name = name;
		this.document = document;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.isVerified = isVerified;
		this.contentType=contentType;
		this.hardcopySubmitted=hardcopySubmitted;
		this.notApplicable=notApplicable;
		this.isPhoto=isPhoto;
		this.submissionDate = submissionDate;
		this.applnDocDetails=applnDocDetails;
		this.semNo=semNo;
		this.semType=semType;
		this.isMngQuotaForm = isMngQuotaForm;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public DocType getDocType() {
		return this.docType;
	}

	public void setDocType(DocType docType) {
		this.docType = docType;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy)  {
		this.createdBy = createdBy;
	}

	public String getModifiedBy()  {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public AdmAppln getAdmAppln() {
		return this.admAppln;
	}

	public void setAdmAppln(AdmAppln admAppln) {
		this.admAppln = admAppln;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getDocument() {
		return this.document;
	}

	public void setDocument(byte[] document) {
		this.document = document;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModifiedDate() {
		return this.lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Boolean getIsVerified() {
		return isVerified;
	}

	public void setIsVerified(Boolean isVerified) {
		this.isVerified = isVerified;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Boolean getHardcopySubmitted() {
		return hardcopySubmitted;
	}

	public void setHardcopySubmitted(Boolean hardcopySubmitted) {
		this.hardcopySubmitted = hardcopySubmitted;
	}

	public Boolean getIsPhoto() {
		return isPhoto;
	}

	public void setIsPhoto(Boolean isPhoto) {
		this.isPhoto = isPhoto;
	}
	public Date getSubmissionDate() {
		return this.submissionDate;
	}

	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}

	public Boolean getNotApplicable() {
		return notApplicable;
	}

	public void setNotApplicable(Boolean notApplicable) {
		this.notApplicable = notApplicable;
	}

	public Set<ApplnDocDetails> getApplnDocDetails() {
		return applnDocDetails;
	}

	public void setApplnDocDetails(Set<ApplnDocDetails> applnDocDetails) {
		this.applnDocDetails = applnDocDetails;
	}

	public String getSemNo() {
		return semNo;
	}

	public void setSemNo(String semNo) {
		this.semNo = semNo;
	}

	public String getSemType() {
		return semType;
	}

	public void setSemType(String semType) {
		this.semType = semType;
	}

	public boolean getIsMngQuotaForm() {
		return isMngQuotaForm;
	}

	public void setIsMngQuotaForm(boolean isMngQuotaForm) {
		this.isMngQuotaForm = isMngQuotaForm;
	}
	
}