package com.kp.cms.bo.phd;

// Generated Nov 3, 2009 6:48:19 PM by Hibernate Tools 3.2.0.b9

import java.util.Date;

/**
 * PcFinancialYear generated by hbm2java
 */
public class PhdVoucherNumber implements java.io.Serializable {

	private int id;
	private String financialYear;
	private String startNo;
	private String currentNo;
	private Boolean currentYear;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;

	public PhdVoucherNumber() {
	}

	public PhdVoucherNumber(int id,String financialYear,String startNo,String currentNo,Boolean currentYear,
			String createdBy, Date createdDate, String modifiedBy,Date lastModifiedDate, Boolean isActive) {
		this.id = id;
		this.financialYear = financialYear;
		this.startNo=startNo;
		this.currentNo=currentNo;
		this.currentYear = currentYear;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}

	public String getStartNo() {
		return startNo;
	}

	public void setStartNo(String startNo) {
		this.startNo = startNo;
	}

	public String getCurrentNo() {
		return currentNo;
	}

	public void setCurrentNo(String currentNo) {
		this.currentNo = currentNo;
	}

	public Boolean getCurrentYear() {
		return currentYear;
	}

	public void setCurrentYear(Boolean currentYear) {
		this.currentYear = currentYear;
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

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

}
