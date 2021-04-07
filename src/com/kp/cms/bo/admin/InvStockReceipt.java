package com.kp.cms.bo.admin;

// Generated Aug 27, 2009 2:58:28 PM by Hibernate Tools 3.2.0.b9

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * InvStockReceipt generated by hbm2java
 */
public class InvStockReceipt implements java.io.Serializable {

	private int id;
	private InvLocation invLocation;
	private InvPurchaseOrder invPurchaseOrder;
	private Date receiptDate;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Set<InvStockReceiptItem> invStockReceiptItems = new HashSet<InvStockReceiptItem>(
			0);

	public InvStockReceipt() {
	}

	public InvStockReceipt(int id) {
		this.id = id;
	}

	public InvStockReceipt(int id, InvLocation invLocation,
			InvPurchaseOrder invPurchaseOrder, Date receiptDate,
			String createdBy, Date createdDate, String modifiedBy,
			Date lastModifiedDate, Boolean isActive,
			Set<InvStockReceiptItem> invStockReceiptItems) {
		this.id = id;
		this.invLocation = invLocation;
		this.invPurchaseOrder = invPurchaseOrder;
		this.receiptDate = receiptDate;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.invStockReceiptItems = invStockReceiptItems;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public InvLocation getInvLocation() {
		return this.invLocation;
	}

	public void setInvLocation(InvLocation invLocation) {
		this.invLocation = invLocation;
	}

	public InvPurchaseOrder getInvPurchaseOrder() {
		return this.invPurchaseOrder;
	}

	public void setInvPurchaseOrder(InvPurchaseOrder invPurchaseOrder) {
		this.invPurchaseOrder = invPurchaseOrder;
	}

	public Date getReceiptDate() {
		return this.receiptDate;
	}

	public void setReceiptDate(Date receiptDate) {
		this.receiptDate = receiptDate;
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

	public Set<InvStockReceiptItem> getInvStockReceiptItems() {
		return this.invStockReceiptItems;
	}

	public void setInvStockReceiptItems(
			Set<InvStockReceiptItem> invStockReceiptItems) {
		this.invStockReceiptItems = invStockReceiptItems;
	}

}
