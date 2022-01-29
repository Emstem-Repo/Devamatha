package com.kp.cms.to.pettycash;

import java.util.Date;

import com.kp.cms.bo.admin.PcFinancialYear;

public class ReceiptNumberTo {
	
	private int receiptNumberId;
	private PcFinancialYear pcFinancialYear;
	//private int finYearId;
	private String startingNumber;
	private String endingNumber;
	private String createdBy;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private String financilalYear;
	
	
	public int getReceiptNumberId() {
		return receiptNumberId;
	}
	public void setReceiptNumberId(int receiptNumberId) {
		this.receiptNumberId = receiptNumberId;
	}
	/*public int getFinYearId() {
		return finYearId;
	}
	public void setFinYearId(int finYearId) {
		this.finYearId = finYearId;
	}*/
	
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
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public PcFinancialYear getPcFinancialYear() {
		return pcFinancialYear;
	}
	public void setPcFinancialYear(PcFinancialYear pcFinancialYear) {
		this.pcFinancialYear = pcFinancialYear;
	}
	public String getFinancilalYear() {
		return financilalYear;
	}
	public void setFinancilalYear(String financilalYear) {
		this.financilalYear = financilalYear;
	}
	
	
	

}
