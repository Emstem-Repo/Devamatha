package com.kp.cms.bo.admin;

import java.util.Date;

public class StudentUploadPhoto {

	private int id;
	private String applnNo;
	private String createdBy;;
	private String modifiedBy;
	private byte[] doc;
	private String fileName;
	private String contentType;
	private Boolean isActive;
	private Date createdDate;
	private Date lastModifiedDate;

	public StudentUploadPhoto() {
	}

	public StudentUploadPhoto(int id) {
		this.id = id;
	}

	public StudentUploadPhoto(int id, String applnNo, String createdBy,
			String modifiedBy, byte[] doc,
			String fileName, String contentType,
			Boolean isActive, Date createdDate, Date lastModifiedDate) {
		this.id = id;
		this.applnNo = applnNo;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.doc = doc;
		this.fileName = fileName;
		this.contentType = contentType;
		this.isActive = isActive;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
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

	public byte[] getDoc() {
		return this.doc;
	}

	public void setDoc(byte[] doc) {
		this.doc = doc;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getContentType() {
		return this.contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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

	public String getApplnNo() {
		return applnNo;
	}

	public void setApplnNo(String applnNo) {
		this.applnNo = applnNo;
	}

	
	
	
	
}
