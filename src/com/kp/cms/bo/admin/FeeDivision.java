package com.kp.cms.bo.admin;

// Generated Feb 10, 2009 3:07:45 PM by Hibernate Tools 3.2.0.b9

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * FeeDivision generated by hbm2java
 */
public class FeeDivision implements java.io.Serializable {

	private int id;
	private String createdBy;;
	private String modifiedBy;
	private String name;
	private Date createdDate;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Set<FeeAccount> feeAccounts = new HashSet<FeeAccount>(0);
	
	public FeeDivision() {
	}

	public FeeDivision(int id) {
		this.id = id;
	}

	public FeeDivision(int id, String createdBy,
			String modifiedBy, String name, Date createdDate,
			Date lastModifiedDate, Boolean isActive,
			Set<FeeAccount> feeAccounts) {
		this.id = id;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.name = name;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.feeAccounts = feeAccounts;
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

	public Set<FeeAccount> getFeeAccounts() {
		return this.feeAccounts;
	}

	public void setFeeAccounts(Set<FeeAccount> feeAccounts) {
		this.feeAccounts = feeAccounts;
	}

}
