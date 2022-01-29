package com.kp.cms.bo.exam;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.admin.PcFinancialYear;

public class OnlineBillNumber implements Serializable {
	
	private int id;
	private String startingNumber;
	private String endingNumber;
	private String currentBillNo;
	private PcFinancialYear pcFinancialYear;
	private Boolean isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	
	public OnlineBillNumber() {
		super();
	}
	
	public OnlineBillNumber(int id, String startingNumber, String endingNumber,
			String currentBillNo, PcFinancialYear pcFinancialYear,
			Boolean isActive, String createdBy, Date createdDate,
			String modifiedBy, Date lastModifiedDate) {
		super();
		this.id = id;
		this.startingNumber = startingNumber;
		this.endingNumber = endingNumber;
		this.currentBillNo = currentBillNo;
		this.pcFinancialYear = pcFinancialYear;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStartingNumber() {
		return startingNumber;
	}

	public void setStartingNumber(String startingNumber) {
		this.startingNumber = startingNumber;
	}

	public String getEndingNumber() {
		return endingNumber;
	}

	public void setEndingNumber(String endingNumber) {
		this.endingNumber = endingNumber;
	}

	public String getCurrentBillNo() {
		return currentBillNo;
	}

	public void setCurrentBillNo(String currentBillNo) {
		this.currentBillNo = currentBillNo;
	}

	public PcFinancialYear getPcFinancialYear() {
		return pcFinancialYear;
	}

	public void setPcFinancialYear(PcFinancialYear pcFinancialYear) {
		this.pcFinancialYear = pcFinancialYear;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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
	
	
}