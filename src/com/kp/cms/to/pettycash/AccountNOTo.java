package com.kp.cms.to.pettycash;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AccountNOTo implements Serializable,Comparable<AccountNOTo>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String accCode;
	private String accName;
	private String bankAccNo;
	private Boolean isFixedAmount;
	private BigDecimal amount;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private int sequence;
	
	public String getAccCode() {
		return accCode;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
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
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	@Override
	public int compareTo(AccountNOTo arg0) {
		if(arg0!=null && this!=null){
			if(this.getSequence() > arg0.getSequence()){
				return 1;
			}else if(this.getSequence() < arg0.getSequence()){
				return -1;
			}else{
				return 0;
			}
		}
		return 0;
	}
	
}