package com.kp.cms.bo.admin;

// Generated Jan 22, 2009 3:35:47 PM by Hibernate Tools 3.2.0.b9

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Account generated by hbm2java
 */
public class Account implements java.io.Serializable {

	private int id;
	private BankBranch bankBranch;
	private String createdBy;;
	private String modifiedBy;
	private String accHolderName;
	private String accNo;
	private String accType;
	private Date createdDate;
	private Date lastModifiedDate;
	private String code;
	private Set<FeeDetails> feeDetailses = new HashSet<FeeDetails>(0);

	public Account() {
	}

	public Account(int id) {
		this.id = id;
	}

	public Account(int id, BankBranch bankBranch, String createdBy,
			String modifiedBy, String accHolderName, String accNo,
			String accType, Date createdDate, Date lastModifiedDate,
			String code, Set<FeeDetails> feeDetailses) {
		this.id = id;
		this.bankBranch = bankBranch;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.accHolderName = accHolderName;
		this.accNo = accNo;
		this.accType = accType;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.code = code;
		this.feeDetailses = feeDetailses;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BankBranch getBankBranch() {
		return this.bankBranch;
	}

	public void setBankBranch(BankBranch bankBranch) {
		this.bankBranch = bankBranch;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy)  {
		this.createdBy = createdBy;
	}

	public String getModifiedBy()  {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getAccHolderName() {
		return this.accHolderName;
	}

	public void setAccHolderName(String accHolderName) {
		this.accHolderName = accHolderName;
	}

	public String getAccNo() {
		return this.accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public String getAccType() {
		return this.accType;
	}

	public void setAccType(String accType) {
		this.accType = accType;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModifiedDate() {
		return this.lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Set<FeeDetails> getFeeDetailses() {
		return this.feeDetailses;
	}

	public void setFeeDetailses(Set<FeeDetails> feeDetailses) {
		this.feeDetailses = feeDetailses;
	}

}