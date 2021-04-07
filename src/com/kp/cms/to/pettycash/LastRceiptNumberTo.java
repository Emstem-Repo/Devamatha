package com.kp.cms.to.pettycash;

import java.io.Serializable;

public class LastRceiptNumberTo implements Serializable {
	
	
	private String receiptNo;
	private String academicYear;
	private int id; 
	private String createdBy;
	private String modifiedBy;
	private String createdDate;
	private String lastModifiedDate;
	private String isActive;
	private int finacialYearID;
	
	public String getReceiptNo() {
		return receiptNo;
	}
	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public int getFinacialYearID() {
		return finacialYearID;
	}
	public void setFinacialYearID(int finacialYearID) {
		this.finacialYearID = finacialYearID;
	}
	
	

}
