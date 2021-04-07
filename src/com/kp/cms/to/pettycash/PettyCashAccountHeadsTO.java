package com.kp.cms.to.pettycash;


import java.util.Date;

import java.io.Serializable;


public class PettyCashAccountHeadsTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private Integer pcBankAccNumberId;
	private Integer pcAccHeadGroupCodeId;
	private String pcBankAccNumber;
	private String groupName;
	private String accCode;
	private String accName;
	private String bankAccNo;
	private Boolean isFixedAmount;
	private String amount;
	
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private String isActives;
	private String programName;
	
	
	
	public String getPcBankAccNumber() {
		return pcBankAccNumber;
	}
	public void setPcBankAccNumber(String pcBankAccNumber) {
		this.pcBankAccNumber = pcBankAccNumber;
	}
	public Integer getPcBankAccNumberId() {
		return pcBankAccNumberId;
	}
	public void setPcBankAccNumberId(Integer pcBankAccNumberId) {
		this.pcBankAccNumberId = pcBankAccNumberId;
	}
	public Integer getPcAccHeadGroupCodeId() {
		return pcAccHeadGroupCodeId;
	}
	public void setPcAccHeadGroupCodeId(Integer pcAccHeadGroupCodeId) {
		this.pcAccHeadGroupCodeId = pcAccHeadGroupCodeId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getAccCode() {
		return accCode;
	}
	public void setAccCode(String accCode) {
		this.accCode = accCode;
	}
	public String getAccName() {
		return accName;
	}
	public void setAccName(String accName) {
		this.accName = accName;
	}
	public String getBankAccNo() {
		return bankAccNo;
	}
	public void setBankAccNo(String bankAccNo) {
		this.bankAccNo = bankAccNo;
	}
	public Boolean getIsFixedAmount() {
		return isFixedAmount;
	}
	public void setIsFixedAmount(Boolean isFixedAmount) {
		this.isFixedAmount = isFixedAmount;
	}
	
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
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
	public String getIsActives() {
		return isActives;
	}
	public void setIsActives(String isActives) {
		this.isActives = isActives;
	}
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
		
}
