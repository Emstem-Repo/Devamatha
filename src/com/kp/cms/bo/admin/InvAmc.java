package com.kp.cms.bo.admin;

// Generated Aug 27, 2009 2:58:28 PM by Hibernate Tools 3.2.0.b9

import java.math.BigDecimal;
import java.util.Date;

/**
 * InvAmc generated by hbm2java
 */
public class InvAmc implements java.io.Serializable {

	private int id;
	private InvItemCategory invItemCategory;
	private InvStockReceiptItem invStockReceiptItem;
	private InvItem invItem;
	private String itemNo;
	private Date warrantyStartDate;
	private Date warrantyEndDate;
	private String comments;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Character warrantyAmcFlag;
	private InvVendor invVendor;
	private BigDecimal amount;
	
	public InvAmc() {
	}

	public InvAmc(int id) {
		this.id = id;
	}

	public InvAmc(int id, InvItemCategory invItemCategory,
			InvStockReceiptItem invStockReceiptItem, InvItem invItem, InvVendor invVendor,
			String itemNo, Date warrantyStartDate, Date warrantyEndDate,
			String comments, String createdBy, Date createdDate,
			String modifiedBy, Date lastModifiedDate, Boolean isActive,Character warrantyAmcFlag) {
		this.id = id;
		this.invItemCategory = invItemCategory;
		this.invStockReceiptItem = invStockReceiptItem;
		this.invItem = invItem;
		this.itemNo = itemNo;
		this.warrantyStartDate = warrantyStartDate;
		this.warrantyEndDate = warrantyEndDate;
		this.comments = comments;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.warrantyAmcFlag = warrantyAmcFlag;
		this.invVendor = invVendor;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public InvItemCategory getInvItemCategory() {
		return this.invItemCategory;
	}

	public void setInvItemCategory(InvItemCategory invItemCategory) {
		this.invItemCategory = invItemCategory;
	}

	public InvStockReceiptItem getInvStockReceiptItem() {
		return this.invStockReceiptItem;
	}

	public void setInvStockReceiptItem(InvStockReceiptItem invStockReceiptItem) {
		this.invStockReceiptItem = invStockReceiptItem;
	}

	public InvItem getInvItem() {
		return this.invItem;
	}

	public void setInvItem(InvItem invItem) {
		this.invItem = invItem;
	}

	public String getItemNo() {
		return this.itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public Date getWarrantyStartDate() {
		return this.warrantyStartDate;
	}

	public void setWarrantyStartDate(Date warrantyStartDate) {
		this.warrantyStartDate = warrantyStartDate;
	}

	public Date getWarrantyEndDate() {
		return this.warrantyEndDate;
	}

	public void setWarrantyEndDate(Date warrantyEndDate) {
		this.warrantyEndDate = warrantyEndDate;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
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

	public Character getWarrantyAmcFlag() {
		return warrantyAmcFlag;
	}

	public void setWarrantyAmcFlag(Character warrantyAmcFlag) {
		this.warrantyAmcFlag = warrantyAmcFlag;
	}

	public InvVendor getInvVendor() {
		return invVendor;
	}

	public void setInvVendor(InvVendor invVendor) {
		this.invVendor = invVendor;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
}
