package com.kp.cms.bo.admin;

// Generated Aug 27, 2009 2:58:28 PM by Hibernate Tools 3.2.0.b9

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * InvUom generated by hbm2java
 */
public class InvUom implements java.io.Serializable {

	private int id;
	private String name;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Set<InvItem> invItemsForInvIssueUomId = new HashSet<InvItem>(0);
	private Set<InvItem> invItemsForInvPurchaseUomId = new HashSet<InvItem>(0);
	private Set<InvSalvageItem> invSalvageItems = new HashSet<InvSalvageItem>(0);

	public InvUom() {
	}

	public InvUom(int id) {
		this.id = id;
	}

	public InvUom(int id, String name, String createdBy, Date createdDate,
			String modifiedBy, Date lastModifiedDate, Boolean isActive,
			Set<InvItem> invItemsForInvIssueUomId,
			Set<InvItem> invItemsForInvPurchaseUomId,
			Set<InvSalvageItem> invSalvageItems) {
		this.id = id;
		this.name = name;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.invItemsForInvIssueUomId = invItemsForInvIssueUomId;
		this.invItemsForInvPurchaseUomId = invItemsForInvPurchaseUomId;
		this.invSalvageItems = invSalvageItems;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Set<InvItem> getInvItemsForInvIssueUomId() {
		return this.invItemsForInvIssueUomId;
	}

	public void setInvItemsForInvIssueUomId(
			Set<InvItem> invItemsForInvIssueUomId) {
		this.invItemsForInvIssueUomId = invItemsForInvIssueUomId;
	}

	public Set<InvItem> getInvItemsForInvPurchaseUomId() {
		return this.invItemsForInvPurchaseUomId;
	}

	public void setInvItemsForInvPurchaseUomId(
			Set<InvItem> invItemsForInvPurchaseUomId) {
		this.invItemsForInvPurchaseUomId = invItemsForInvPurchaseUomId;
	}

	public Set<InvSalvageItem> getInvSalvageItems() {
		return this.invSalvageItems;
	}

	public void setInvSalvageItems(Set<InvSalvageItem> invSalvageItems) {
		this.invSalvageItems = invSalvageItems;
	}

}
