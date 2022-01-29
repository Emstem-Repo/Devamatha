package com.kp.cms.to.admin;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class StudentUploadPhotoTO implements Serializable {
	private int id;
	private int applnNo;
	private String createdBy;
	private String modifiedBy;
	private byte[] doc;
	private String fileName;
	private String contentType;
	private Boolean isActive;
	private Date createdDate;
	private Date lastModifiedDate;
	private String regNo;
	private String checked;
	private String studentId;
	private String studentName;
	private String studentClass;
	private String oldFileName;
	private String rejectedReason;
	
	


	public String getRejectedReason() {
		return rejectedReason;
	}


	public void setRejectedReason(String rejectedReason) {
		this.rejectedReason = rejectedReason;
	}


	public String getOldFileName() {
		return oldFileName;
	}


	public void setOldFileName(String oldFileName) {
		this.oldFileName = oldFileName;
	}


	public String getStudentName() {
		return studentName;
	}


	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}


	public String getStudentClass() {
		return studentClass;
	}


	public void setStudentClass(String studentClass) {
		this.studentClass = studentClass;
	}


	public String getStudentId() {
		return studentId;
	}


	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}


	public String getChecked() {
		return checked;
	}


	public void setChecked(String checked) {
		this.checked = checked;
	}

	public StudentUploadPhotoTO(){
		
	}
	
	
	public String getRegNo() {
		return regNo;
	}


	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	public int getApplnNo() {
		return applnNo;
	}


	public void setApplnNo(int applnNo) {
		this.applnNo = applnNo;
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
	public byte[] getDoc() {
		return doc;
	}
	public void setDoc(byte[] doc) {
		this.doc = doc;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public Date getCreatedDate() {
		return (Date)createdDate.clone();
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = (Date)createdDate.clone();
	}
	public Date getLastModifiedDate() {
		return (Date)lastModifiedDate.clone();
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = (Date)lastModifiedDate.clone();
	}
	

}
