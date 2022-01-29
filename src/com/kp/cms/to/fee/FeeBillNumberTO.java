package com.kp.cms.to.fee;

import java.io.Serializable;
/**
 * 
 * @author kshirod.k
 * A TO class for FeeBillNumber
 *
 */
public class FeeBillNumberTO implements Serializable{
	
	private String billNo;
	private String academicYear;
	private String currentBillNo;
	private int id;
	private String createdBy;
	private String modifiedBy;
	private String createdDate;
	private String lastModifiedDate;
	private String isActive;
	private String year;
	private int finacialYearID;
	public int getFinacialYearID() {
		return finacialYearID;
	}
	public void setFinacialYearID(int finacialYearID) {
		this.finacialYearID = finacialYearID;
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
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public String getCurrentBillNo() {
		return currentBillNo;
	}
	public void setCurrentBillNo(String currentBillNo) {
		this.currentBillNo = currentBillNo;
	}
		
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public String getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
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
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	

}
