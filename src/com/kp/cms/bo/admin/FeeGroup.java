package com.kp.cms.bo.admin;

// Generated Feb 10, 2009 3:07:45 PM by Hibernate Tools 3.2.0.b9

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * FeeGroup generated by hbm2java
 */
public class FeeGroup implements java.io.Serializable {

	private int id;
	private String createdBy;;
	private String modifiedBy;
	private String name;
	private Date createdDate;
	private Date lastModifiedDate;
	private Boolean isOptional;
	private Boolean isActive;
	private Set<FeeHeading> feeHeadings = new HashSet<FeeHeading>(0);
	private Set<Fee> fees = new HashSet<Fee>(0);
	private Set<FeePaymentApplicantDetails> feePaymentApplicantDetailses = new HashSet<FeePaymentApplicantDetails>(
			0);
	
	public FeeGroup() {
	}

	public FeeGroup(int id) {
		this.id = id;
	}

	public FeeGroup(int id, String createdBy,
			String modifiedBy, String name, Date createdDate,
			Date lastModifiedDate, Boolean isActive, Boolean isOptional,
			Set<FeeHeading> feeHeadings, Set<Fee> fees,
			Set<FeePaymentApplicantDetails> feePaymentApplicantDetailses) {
		this.id = id;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.name = name;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.isOptional = isOptional;
		this.isActive = isActive;
		this.feeHeadings = feeHeadings;
		this.fees = fees;
		this.feePaymentApplicantDetailses = feePaymentApplicantDetailses;
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

	public String getModifiedBy()  {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Boolean getIsOptional() {
		return isOptional;
	}

	public void setIsOptional(Boolean isOptional) {
		this.isOptional = isOptional;
	}

	public Set<FeeHeading> getFeeHeadings() {
		return this.feeHeadings;
	}

	public void setFeeHeadings(Set<FeeHeading> feeHeadings) {
		this.feeHeadings = feeHeadings;
	}

	public Set<Fee> getFees() {
		return this.fees;
	}

	public void setFees(Set<Fee> fees) {
		this.fees = fees;
	}
	/**
	 * @return the feePaymentApplicantDetailses
	 */
	public Set<FeePaymentApplicantDetails> getFeePaymentApplicantDetailses() {
		return feePaymentApplicantDetailses;
	}

	/**
	 * @param feePaymentApplicantDetailses the feePaymentApplicantDetailses to set
	 */
	public void setFeePaymentApplicantDetailses(
			Set<FeePaymentApplicantDetails> feePaymentApplicantDetailses) {
		this.feePaymentApplicantDetailses = feePaymentApplicantDetailses;
	}
	
	
}
