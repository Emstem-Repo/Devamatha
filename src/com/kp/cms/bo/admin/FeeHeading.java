package com.kp.cms.bo.admin;

// Generated Feb 10, 2009 3:07:45 PM by Hibernate Tools 3.2.0.b9

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * FeeHeading generated by hbm2java
 */
public class FeeHeading implements java.io.Serializable {

	private int id;
	private String createdBy;;
	private String modifiedBy;
	private FeeGroup feeGroup;
	private String name;
	private Date createdDate;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Set<FeeAccountAssignment> feeAccountAssignments = new HashSet<FeeAccountAssignment>(
			0);
	private Set<FeePaymentDetail > feePaymentDetailses = new HashSet<FeePaymentDetail>(
			0);


	public FeeHeading() {
	}

	public FeeHeading(int id) {
		this.id = id;
	}

	public FeeHeading(int id, String createdBy,
			String modifiedBy, FeeGroup feeGroup, String name,
			Date createdDate, Date lastModifiedDate, Boolean isActive,
			Set<FeeAccountAssignment> feeAccountAssignments) {
		this.id = id;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.feeGroup = feeGroup;
		this.name = name;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.feeAccountAssignments = feeAccountAssignments;
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

	public FeeGroup getFeeGroup() {
		return this.feeGroup;
	}

	public void setFeeGroup(FeeGroup feeGroup) {
		this.feeGroup = feeGroup;
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

	public Set<FeeAccountAssignment> getFeeAccountAssignments() {
		return this.feeAccountAssignments;
	}

	public void setFeeAccountAssignments(
			Set<FeeAccountAssignment> feeAccountAssignments) {
		this.feeAccountAssignments = feeAccountAssignments;
	}

	public Set<FeePaymentDetail> getFeePaymentDetailses() {
		return feePaymentDetailses;
	}

	public void setFeePaymentDetailses(Set<FeePaymentDetail> feePaymentDetailses) {
		this.feePaymentDetailses = feePaymentDetailses;
	}
	
}
