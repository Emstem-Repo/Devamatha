package com.kp.cms.bo.admin;

import java.util.Date;

public class AttendanceSlipNumber implements java.io.Serializable {

	private int id;
	private PcFinancialYear pcFinancialYear;
	private String startingNumber;
	private String endingNumber;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private int currentNo;

	public AttendanceSlipNumber() {
	}

	public AttendanceSlipNumber(int id) {
		this.id = id;
	}

	public AttendanceSlipNumber(int id, PcFinancialYear pcFinancialYear,
			String startingNumber, String endingNumber, String createdBy,
			Date createdDate, String modifiedBy, Date lastModifiedDate,
			Boolean isActive,int currentNo) {
		this.id = id;
		this.pcFinancialYear = pcFinancialYear;
		this.startingNumber = startingNumber;
		this.endingNumber = endingNumber;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.currentNo=currentNo;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PcFinancialYear getPcFinancialYear() {
		return this.pcFinancialYear;
	}

	public void setPcFinancialYear(PcFinancialYear pcFinancialYear) {
		this.pcFinancialYear = pcFinancialYear;
	}

	public String getStartingNumber() {
		return this.startingNumber;
	}

	public void setStartingNumber(String startingNumber) {
		this.startingNumber = startingNumber;
	}

	public String getEndingNumber() {
		return this.endingNumber;
	}

	public void setEndingNumber(String endingNumber) {
		this.endingNumber = endingNumber;
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

	public Boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public int getCurrentNo() {
		return currentNo;
	}

	public void setCurrentNo(int currentNo) {
		this.currentNo = currentNo;
	}

}
