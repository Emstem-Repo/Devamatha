package com.kp.cms.to.hostel;

public class HostelFeesTypeTo {
	private int id;
	private String feesname;
	private String amount;	
	private String hostelFeesType;
	private String createdBy;
	private String modifiedBy;
	private String createdDate;
	private String lastModifiedDate;
	private int feeId;
	private int currencyId;
	private String academicYear;
	private boolean isActive;
	
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
	public String getHostelFeesType() {
		return hostelFeesType;
	}
	public void setHostelFeesType(String hostelFeesType) {
		this.hostelFeesType = hostelFeesType;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFeesname() {
		return feesname;
	}
	public void setFeesname(String feesname) {
		this.feesname = feesname;
	}
	/**
	 * @return the feeId
	 */
	public int getFeeId() {
		return feeId;
	}
	/**
	 * @param feeId the feeId to set
	 */
	public void setFeeId(int feeId) {
		this.feeId = feeId;
	}
	/**
	 * @return the currencyId
	 */
	public int getCurrencyId() {
		return currencyId;
	}
	/**
	 * @param currencyId the currencyId to set
	 */
	public void setCurrencyId(int currencyId) {
		this.currencyId = currencyId;
	}
	/**
	 * @return the academicYear
	 */
	public String getAcademicYear() {
		return academicYear;
	}
	/**
	 * @param academicYear the academicYear to set
	 */
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	/**
	 * @return the isActive
	 */
	public boolean isActive() {
		return isActive;
	}
	/**
	 * @param isActive the isActive to set
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}	
}
