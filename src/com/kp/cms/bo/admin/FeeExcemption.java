package com.kp.cms.bo.admin;

// Generated Jan 22, 2009 3:35:47 PM by Hibernate Tools 3.2.0.b9

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * FeeExcemption generated by hbm2java
 */
public class FeeExcemption implements java.io.Serializable {

	private int id;
	private String createdBy;;
	private FeeDetails feeDetails;
	private String modifiedBy;
	private Category category;
	private FeeExcemptionType feeExcemptionType;
	private String name;
	private BigDecimal amount;
	private Integer year;
	private Date createdDate;
	private Date lastModifiedDate;
	private Set<FeeTransaction> feeTransactions = new HashSet<FeeTransaction>(0);

	public FeeExcemption() {
	}

	public FeeExcemption(int id) {
		this.id = id;
	}

	public FeeExcemption(int id, String createdBy,
			FeeDetails feeDetails, String modifiedBy,
			Category category, FeeExcemptionType feeExcemptionType,
			String name, BigDecimal amount, Integer year, Date createdDate,
			Date lastModifiedDate, Set<FeeTransaction> feeTransactions) {
		this.id = id;
		this.createdBy = createdBy;
		this.feeDetails = feeDetails;
		this.modifiedBy = modifiedBy;
		this.category = category;
		this.feeExcemptionType = feeExcemptionType;
		this.name = name;
		this.amount = amount;
		this.year = year;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.feeTransactions = feeTransactions;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy)  {
		this.createdBy = createdBy;
	}

	public FeeDetails getFeeDetails() {
		return this.feeDetails;
	}

	public void setFeeDetails(FeeDetails feeDetails) {
		this.feeDetails = feeDetails;
	}

	public String getModifiedBy()  {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public FeeExcemptionType getFeeExcemptionType() {
		return this.feeExcemptionType;
	}

	public void setFeeExcemptionType(FeeExcemptionType feeExcemptionType) {
		this.feeExcemptionType = feeExcemptionType;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Integer getYear() {
		return this.year;
	}

	public void setYear(Integer year) {
		this.year = year;
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

	public Set<FeeTransaction> getFeeTransactions() {
		return this.feeTransactions;
	}

	public void setFeeTransactions(Set<FeeTransaction> feeTransactions) {
		this.feeTransactions = feeTransactions;
	}

}
