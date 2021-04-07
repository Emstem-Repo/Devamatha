package com.kp.cms.to.admission;

import java.util.Date;
import java.util.List;


public class StudentRemarksTO {
	private int studentRemarkId;
	private int remarkTypeId;
	private int studentId;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private List<String> comments;
	private String remarkType;
	private int occurance;
	private String colourCode;
	private boolean colourPresent;
	
	public int getStudentRemarkId() {
		return studentRemarkId;
	}
	public void setStudentRemarkId(int studentRemarkId) {
		this.studentRemarkId = studentRemarkId;
	}
	public int getRemarkTypeId() {
		return remarkTypeId;
	}
	public void setRemarkTypeId(int remarkTypeId) {
		this.remarkTypeId = remarkTypeId;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
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
	public List<String> getComments() {
		return comments;
	}
	public void setComments(List<String> comments) {
		this.comments = comments;
	}
	public String getRemarkType() {
		return remarkType;
	}
	public void setRemarkType(String remarkType) {
		this.remarkType = remarkType;
	}
	public int getOccurance() {
		return occurance;
	}
	public void setOccurance(int occurance) {
		this.occurance = occurance;
	}
	public String getColourCode() {
		return colourCode;
	}
	public void setColourCode(String colourCode) {
		this.colourCode = colourCode;
	}
	public boolean isColourPresent() {
		return colourPresent;
	}
	public void setColourPresent(boolean colourPresent) {
		this.colourPresent = colourPresent;
	}
	
}
